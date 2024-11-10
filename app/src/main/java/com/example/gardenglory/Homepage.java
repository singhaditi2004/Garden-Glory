package com.example.gardenglory;

import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Homepage extends AppCompatActivity {

    private ViewPager2 viewPager;
    private LinearLayout indicatorLayout;
    private List<Integer> images = Arrays.asList(R.drawable.donotno, R.drawable.logo, R.drawable.papaya);
    private Handler slideHandler = new Handler();
    private int currentIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        viewPager = findViewById(R.id.viewPager);
        indicatorLayout = findViewById(R.id.indicatorLayout);

        CarouselAdapter adapter = new CarouselAdapter(images);
        viewPager.setAdapter(adapter);

        setupIndicators(images.size());
        setCurrentIndicator(0);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                setCurrentIndicator(position);
                currentIndex = position;
            }
        });

        // Start auto slide
        slideHandler.postDelayed(slideRunnable, 3000); // Change every 3 seconds
    }

    private Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            currentIndex = (currentIndex + 1) % images.size();
            viewPager.setCurrentItem(currentIndex, true);
            slideHandler.postDelayed(this, 3000);
        }
    };

    private void setupIndicators(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(8, 0, 8, 0);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(this);
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    this, R.drawable.indicator_inactive));
            indicators[i].setLayoutParams(params);
            indicatorLayout.addView(indicators[i]);
        }
    }

    private void setCurrentIndicator(int index) {
        int childCount = indicatorLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) indicatorLayout.getChildAt(i);
            imageView.setImageDrawable(ContextCompat.getDrawable(
                    this, i == index ? R.drawable.indicator_active : R.drawable.indicator_inactive));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        slideHandler.removeCallbacks(slideRunnable); // Stop auto-sliding when activity is paused
    }

    @Override
    protected void onResume() {
        super.onResume();
        slideHandler.postDelayed(slideRunnable, 3000); // Resume auto-sliding
    }
}