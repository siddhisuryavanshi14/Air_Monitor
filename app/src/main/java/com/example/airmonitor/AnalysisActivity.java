package com.example.airmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class AnalysisActivity extends AppCompatActivity {

    ImageView imgBack;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        imgBack=findViewById(R.id.imgBack);
        listView=findViewById(R.id.listView);

        String[] visualisations={"Air Quality","Pressure","Humidity","Dew Point","Temperature",
                getString(R.string.temp_and_humidity_correlation),getString(R.string.histograms_for_aqi_temperature_humidity_dew_point_pressure),
                getString(R.string._3d_bar_chart),getString(R.string._3_day_comparison)};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1,visualisations);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    startActivity(new Intent(AnalysisActivity.this,WebActivity.class).putExtra("url",
                            "https://thingspeak.com/channels/1335871/charts/1?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&title=Air+Quality&type=line"));
                    break;
                case 1:
                    startActivity(new Intent(AnalysisActivity.this,WebActivity.class).putExtra("url",
                            "https://thingspeak.com/channels/1335871/charts/2?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&title=Pressure&type=line"));
                    break;
                case 2:
                    startActivity(new Intent(AnalysisActivity.this,WebActivity.class).putExtra("url",
                            "https://thingspeak.com/channels/1335871/charts/3?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&title=Humidity&type=line"));
                    break;
                case 3:
                    startActivity(new Intent(AnalysisActivity.this,WebActivity.class).putExtra("url",
                            "https://thingspeak.com/channels/1335871/charts/4?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&title=Dew+Point&type=line"));
                    break;
                case 4:
                    startActivity(new Intent(AnalysisActivity.this,WebActivity.class).putExtra("url",
                            "https://thingspeak.com/channels/1335871/charts/5?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&title=Temperature&type=line"));
                    break;
                case 5:
                    startActivity(new Intent(AnalysisActivity.this,WebActivity.class).putExtra("url",
                            "https://thingspeak.com/apps/matlab_visualizations/450140"));
                    break;
                case 6:
                    startActivity(new Intent(AnalysisActivity.this,WebActivity.class).putExtra("url",
                            "https://thingspeak.com/apps/matlab_visualizations/450154"));
                    break;
                case 7:
                    startActivity(new Intent(AnalysisActivity.this,WebActivity.class).putExtra("url",
                            "https://thingspeak.com/apps/matlab_visualizations/450172"));
                    break;
                case 8:
                    startActivity(new Intent(AnalysisActivity.this,WebActivity.class).putExtra("url",
                            "https://thingspeak.com/apps/matlab_visualizations/450644"));
                    break;
            }
        });

        imgBack.setOnClickListener(v -> onBackPressed());

    }

    private void goTooUrl(String url) {
        Uri uriUrl= Uri.parse(url);
        Intent launchBrowser=new Intent(Intent.ACTION_VIEW,uriUrl);
        startActivity(launchBrowser);
    }

}