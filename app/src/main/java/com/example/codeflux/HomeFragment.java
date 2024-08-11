package com.example.codeflux;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {
    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private LinearLayout availLang;
    private LinearLayout upSoon;
    private int dotsCount;
    private ImageView[] dots;
    private int currentPage = 0;
    private Timer timer;
    private final long DELAY_MS = 0; // Delay in milliseconds before task is to be executed
    private final long PERIOD_MS = 5000; // Time in milliseconds between successive task executions.

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set the title of the activity hosting the fragment
        requireActivity().setTitle("Home");
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        viewPager = view.findViewById(R.id.viewPager);
        dotsLayout = view.findViewById(R.id.dotsLayout);

        availLang = view.findViewById(R.id.availLang);
        upSoon = view.findViewById(R.id.upSooon);

        List<ImageModel> images = new ArrayList<>();
        images.add(new ImageModel(R.drawable.sliderimgone, "Read, Learn, Code"));
        images.add(new ImageModel(R.drawable.sliderimgtwo, "Elevate Your Coding Skills with Every Page"));
        images.add(new ImageModel(R.drawable.sliderimgthree, "Crafting your skill with Code Flux"));

        ImgSliderAdapter adapter = new ImgSliderAdapter(getActivity(), images);
        viewPager.setAdapter(adapter);

        // Add dots indicator
        dotsCount = adapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getActivity());
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
                Intent intent  = new Intent(getActivity(), MainActivity3.class);
                startActivity(intent);
            }
        });

        upSoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Not Available", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        startSlider();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopSlider();
    }

    private void startSlider() {
        if (timer == null) {
            final android.os.Handler handler = new android.os.Handler();
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