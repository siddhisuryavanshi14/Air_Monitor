package com.example.airmonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ekn.gruzer.gaugelibrary.HalfGauge;
import com.ekn.gruzer.gaugelibrary.Range;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {

    HalfGauge halfGauge;
    ImageButton data;
    TextView temperature,humidity,pressure,dewPoint,txtMessage;
    ImageView icon;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    boolean connected=false;
    SharedPreferences sharedPreferences;
    String air_quality,pressureTH,humidityTH,dewPointTH,temperatureTH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE);
        setContentView(R.layout.activity_main);

        halfGauge=(HalfGauge) findViewById(R.id.halfGauge);
        temperature=findViewById(R.id.temperature);
        humidity=findViewById(R.id.humidity);
        pressure=findViewById(R.id.pressure);
        dewPoint=findViewById(R.id.dewPoint);
        data=findViewById(R.id.data);
        txtMessage=findViewById(R.id.txtMessage);
        icon=findViewById(R.id.icon);

        Range range = new Range();
        range.setColor(Color.parseColor("#64dd17"));
        range.setFrom(0.0);
        range.setTo(50.0);

        Range range2 = new Range();
        range2.setColor(Color.parseColor("#ffee58"));
        range2.setFrom(50.1);
        range2.setTo(100.0);

        Range range3 = new Range();
        range3.setColor(Color.parseColor("#ffa726"));
        range3.setFrom(100.1);
        range3.setTo(150.0);

        Range range4 =new Range();
        range4.setColor(Color.parseColor("#f44336"));
        range4.setFrom(150.1);
        range4.setTo(200.0);

        Range range5 =new Range();
        range5.setColor(Color.parseColor("#c2185b"));
        range5.setFrom(200.1);
        range5.setTo(300.0);

        Range range6 =new Range();
        range6.setColor(Color.parseColor("#b71c1c"));
        range6.setFrom(300.1);
        range6.setTo(500.0);

        //set min max and current value
        halfGauge.setMinValue(0.0);
        halfGauge.setMaxValue(500.0);

        //add color ranges to gauge
        halfGauge.addRange(range);
        halfGauge.addRange(range2);
        halfGauge.addRange(range3);
        halfGauge.addRange(range4);
        halfGauge.addRange(range5);
        halfGauge.addRange(range6);

        rootNode=FirebaseDatabase.getInstance();
        reference=rootNode.getReference("Data");
        DecimalFormat df=new DecimalFormat("#.00");


        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                isConnected();
                int last= Integer.parseInt(sharedPreferences.getString("last", "0"));
                if (!connected) {
                    air_quality = sharedPreferences.getString("air_quality", "0.0");
                    pressureTH = sharedPreferences.getString("pressure", "0");
                    humidityTH = sharedPreferences.getString("humidity", "0");
                    dewPointTH = sharedPreferences.getString("dew_point", "0");
                    temperatureTH = sharedPreferences.getString("temperature", "0");
                    runOnUiThread(() -> setValues(Double.parseDouble(air_quality)));
                } else {
                    //deleteData(reference);
                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                    String url = "https://api.thingspeak.com/channels/1335871/feeds.json?api_key=P13OEXK1V8BRM8AG&results=2";
                    JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
                        try {
                            JSONObject channelData = response.getJSONObject("channel");
                            String lastEntry = channelData.getString("last_entry_id");
                            JSONArray feedsData = response.getJSONArray("feeds");
                            for (int i = 0; i < feedsData.length(); i++) {
                                JSONObject feed = feedsData.getJSONObject(i);
                                String entry = feed.getString("entry_id");
                                String dateTime = feed.getString("created_at");
                                String entry_id = feed.getString("entry_id");
                                air_quality = feed.getString("field1");
                                pressureTH = feed.getString("field2")+" hPa";
                                if (!feed.getString("field4").equals("nan")) {
                                    humidityTH = feed.getString("field3").trim() + " %";
                                    double d=(Double.parseDouble(feed.getString("field4")) - 32) / 1.8;
                                    dewPointTH = String.valueOf(df.format(d)) + " °C";
                                }else {
                                    humidityTH="NA";
                                    dewPointTH = "NA";
                                }
                                double t=(Double.parseDouble(feed.getString("field5").split("\\r\\n\\r\\n")[0])-32)/1.8;
                                temperatureTH = String.valueOf(df.format(t)) +" °C";
                                if (lastEntry.equals(entry)) {
                                    Double air = Double.parseDouble(air_quality);
                                    runOnUiThread(() -> setValues(air));
                                    sharedPreferences.edit().putString("air_quality", air_quality).apply();
                                    sharedPreferences.edit().putString("temperature", temperatureTH).apply();
                                    sharedPreferences.edit().putString("humidity", humidityTH).apply();
                                    sharedPreferences.edit().putString("pressure", pressureTH).apply();
                                    sharedPreferences.edit().putString("dew_point", dewPointTH).apply();
                                    sharedPreferences.edit().putString("last",lastEntry).apply();
                                }
                                HelperClass helperClass = new HelperClass(
                                        dateTime, entry_id, air_quality, pressureTH, humidityTH, dewPointTH, temperatureTH);
                                addData(reference, helperClass, entry_id);
                            }

                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
                        }

                    }, error -> Toast.makeText(MainActivity.this, "Some Error Occurred Unexpectedly", Toast.LENGTH_SHORT).show()) {
                        @Override
                        public Map<String, String> getHeaders() {
                            Map<String, String> headers = new HashMap<>();
                            headers.put("Content-type", "application/json");
                            return headers;
                        }
                    };
                    jsonRequest.setRetryPolicy(new DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    queue.add(jsonRequest);
                }
            }
        }, 0, 2000);



        data.setOnClickListener(v -> {
            isConnected();
            if (connected) {
                Intent intent = new Intent(MainActivity.this, ShowDataActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this,"Check Internet Connectivity",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addData(DatabaseReference reference,HelperClass helperClass,String id) {
        try {
            reference.child(id).setValue(helperClass);
            //Toast.makeText(this, "Data added", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(this, "Some error occurred", Toast.LENGTH_SHORT).show();
        }

    }

    private void deleteData(DatabaseReference reference) {
        reference.removeValue();
    }

    private void isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) this.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        //we are connected to a network
        connected = Objects.requireNonNull(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)).getState() == NetworkInfo.State.CONNECTED ||
                Objects.requireNonNull(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)).getState() == NetworkInfo.State.CONNECTED;
    }

    private void setValues(Double air) {
        halfGauge.setValue(air);
        if (air >= 0.0 && air <= 50.0) {
            txtMessage.setText("Air Quality is Good");
            icon.setImageResource(R.drawable.ic_baseline_tag_faces_24);
        } else if (air >= 50.1 && air <= 100.0) {
            txtMessage.setText("Air Quality is Moderate");
            icon.setImageResource(R.drawable.ic_moderate_alt_24);
        } else if (air >= 100.1 && air <= 150.0) {
            txtMessage.setText("Air Quality is Unhealthy for Sensitive Groups");
            icon.setImageResource(R.drawable.ic_unhealthy_24);
        } else if (air >= 150.1 && air <= 200.0) {
            txtMessage.setText("Air Quality is Unhealthy");
            icon.setImageResource(R.drawable.ic_unhealthy_24);
        } else if (air >= 200.1 && air <= 300.0) {
            txtMessage.setText("Air Quality is Very Unhealthy");
            icon.setImageResource(R.drawable.ic_very_unhealthy_24);
        } else if (air >= 300.1 && air <= 500.0) {
            txtMessage.setText("Air Quality is Hazardous");
            icon.setImageResource(R.drawable.ic_hazardous_24);
        }
        temperature.setText(temperatureTH);
        humidity.setText(humidityTH);
        pressure.setText(pressureTH);
        dewPoint.setText(dewPointTH);
    }
}