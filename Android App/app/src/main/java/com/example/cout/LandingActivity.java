package com.example.cout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.text.Layout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import pl.droidsonroids.gif.GifImageView;

public class LandingActivity extends AppCompatActivity {

    private ImageButton coutButton;
    private GifImageView image;
    private View landLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        landLayout = (View) findViewById(R.id.layoutLand);

        coutButton = (ImageButton)findViewById(R.id.coutButton);
        image = (GifImageView)findViewById(R.id.gifImageView);
        coutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LandingActivity.this, MainActivity.class));
                animateSlideUp(LandingActivity.this);
//                anim.setDuration(2000);
//                landLayout.startAnimation(anim);
//                coutButton.startAnimation(anim);
//
//                new Handler().postDelayed(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        Intent intent = new Intent(LandingActivity.this,MainActivity.class);
//                        startActivity(intent);
//                    }
//
//                }, 2000);



            }

        });
    }
    public void animateSlideUp(Context context) {

        ((Activity) context).overridePendingTransition(R.anim.animate_slide_up_enter, R.anim.animate_slide_up_exit);
    }




}