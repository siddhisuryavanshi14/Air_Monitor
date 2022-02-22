package com.example.airmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class AnalysisActivity extends AppCompatActivity {

    ImageView img1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        img1=findViewById(R.id.img1);

        //web1.loadUrl("https://thingspeak.com/apps/matlab_visualizations/449745");
        Picasso.get().load("https://s3.amazonaws.com/images.thingspeak.com/plugins/449745/z7636j0QDJwVt7loHzQV6w.png?1645535162208")
                .placeholder(R.drawable.air).into(img1);

    }
}