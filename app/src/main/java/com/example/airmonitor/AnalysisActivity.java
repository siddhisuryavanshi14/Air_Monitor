package com.example.airmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class AnalysisActivity extends AppCompatActivity {

    ImageView img1,img2,img3,imgBack;
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        img1=findViewById(R.id.img1);
        img2=findViewById(R.id.img2);
        img3=findViewById(R.id.img3);
        imgBack=findViewById(R.id.imgBack);
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        //web1.loadUrl("https://thingspeak.com/apps/matlab_visualizations/449745");
        Picasso.get().load("https://s3.amazonaws.com/images.thingspeak.com/plugins/450140/Gz3siNUL1vTByJj-ekfosw.png?1645976317264")
                .placeholder(R.drawable.air)
                .resize(530,380)
                .into(img1);
        Picasso.get().load("https://s3.amazonaws.com/images.thingspeak.com/plugins/450154/UN4Z1KPKdfEre8HSxL1uHQ.png?1645976345746")
                .placeholder(R.drawable.air)
                .resize(530,380)
                .into(img2);
        Picasso.get().load("https://s3.amazonaws.com/images.thingspeak.com/plugins/450172/afy-KBsGQxX6pM1V0pdQzA.png?1645976362762")
                .placeholder(R.drawable.air)
                .resize(530,380)
                .into(img3);

        imgBack.setOnClickListener(v -> onBackPressed());

    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        scaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
            img3.setScaleX(mScaleFactor);
            img3.setScaleY(mScaleFactor);
            return true;
        }
    }


}