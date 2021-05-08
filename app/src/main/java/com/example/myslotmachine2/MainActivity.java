package com.example.myslotmachine2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myslotmachine2.ImageViewScrolling.IEventEnd;
import com.example.myslotmachine2.ImageViewScrolling.ImageViewScrolling;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements IEventEnd {

    private ImageView btn_up, btn_down;
    private ImageViewScrolling image, image2, image3;
    private TextView txt_score;
    private int score = 1000;
    private int count_done = 0;
    private final int min_cash = 50;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        btn_down = findViewById(R.id.btn_down);
        btn_up = findViewById(R.id.btn_up);

        image = findViewById(R.id.image);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);

        txt_score = findViewById(R.id.txt_score);

        image.setEventEnd(MainActivity.this);
        image2.setEventEnd(MainActivity.this);
        image3.setEventEnd(MainActivity.this);

        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (score >= min_cash) {

                    btn_up.setVisibility(View.GONE);
                    btn_down.setVisibility(View.VISIBLE);

                    image.setValueRandom(new Random().nextInt(7));
                    image2.setValueRandom(new Random().nextInt(7));
                    image3.setValueRandom(new Random().nextInt(7));

                    score -= min_cash;
                    txt_score.setText(String.valueOf(score));

                } else {
                    toast("You not enought money").show();
                }
            }
        });

    }

    @Override
    public void eventEnd() {
        if (count_done < 2)
            count_done++;
        else {
            btn_down.setVisibility(View.GONE);
            btn_up.setVisibility(View.VISIBLE);

            count_done = 0;

            if (image.getValue() == image2.getValue() && image2.getValue() == image3.getValue()) {
                int max_win = 300;
                score += max_win;
                toast("+ " + max_win).show();
                txt_score.setText(String.valueOf(score));
            } else if (image.getValue() == image2.getValue() ||
                    image2.getValue() == image3.getValue() ||
                    image.getValue() == image3.getValue()) {
                int min_win = 150;
                score += min_win;
                toast("+ " + min_win).show();
                txt_score.setText(String.valueOf(score));
            } else {
                toast("You lose").show();
            }
        }
    }

    public Toast toast(String msg) {
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);

        View view = toast.getView();
        view.getBackground().setColorFilter(getResources().getColor(R.color.fiolet), PorterDuff.Mode.SRC_IN);

        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(getResources().getColor(R.color.white));

        if (Build.VERSION.SDK_INT

                >= Build.VERSION_CODES.M) {

            text.setTextAppearance(

                    R.style.toastTextStyle);

        }

        return toast;
    }
}
























