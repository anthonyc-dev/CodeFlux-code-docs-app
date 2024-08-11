package com.example.codeflux;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FakeHome extends AppCompatActivity {
    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private LinearLayout availLang;
    private LinearLayout upSoon;
    private int dotsCount;
    private ImageView[] dots;
    private int currentPage = 0;
    private Timer timer;
    DrawerLayout drawerLayout;
    private final long DELAY_MS = 0; // Delay in milliseconds before task is to be executed
    private final long PERIOD_MS = 5000; // Time in milliseconds between successive task executions.

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_home);

        // Set the title of the activity
        setTitle("Home");



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        viewPager = findViewById(R.id.viewPager);
        dotsLayout = findViewById(R.id.dotsLayout);

        availLang = findViewById(R.id.availLang);
        upSoon = findViewById(R.id.upSooon);

        List<ImageModel> images = new ArrayList<>();
        images.add(new ImageModel(R.drawable.sliderimgone, "Read, Learn, Code"));
        images.add(new ImageModel(R.drawable.sliderimgtwo, "Elevate Your Coding Skills with Every Page"));
        images.add(new ImageModel(R.drawable.sliderimgthree, "Crafting your skill with Code Flux"));

        ImgSliderAdapter adapter = new ImgSliderAdapter(this, images);
        viewPager.setAdapter(adapter);

        // Add dots indicator
        dotsCount = adapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageResource(R.drawable.circle_gray_24);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            dotsLayout.addView(dots[i], params);
        }

        dots[0].setImageResource(R.drawable.circle_24);

        // Automatic sliding
        startSlider();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageResource(R.drawable.circle_gray_24);
                }
                dots[position].setImageResource(R.drawable.circle_24);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        availLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FakeHome.this, MainActivity3.class);
                startActivity(intent);
            }
        });

        upSoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FakeHome.this, "Not Available", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        startSlider();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopSlider();
    }

    private void startSlider() {
        if (timer == null) {
            final Handler handler = new Handler();
            final Runnable Update = () -> {
                if (currentPage == dotsCount) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            };

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, DELAY_MS, PERIOD_MS);
        }
    }

    private void stopSlider() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
