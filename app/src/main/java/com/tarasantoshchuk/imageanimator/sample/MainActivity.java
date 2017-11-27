package com.tarasantoshchuk.imageanimator.sample;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.tarasantoshchuk.image_animator.ImageAnimator;

public class MainActivity extends AppCompatActivity {
    private ImageView mStart;
    private ImageView mEnd;
    private ImageView mMovingImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStart = findViewById(R.id.start_animation);
        ImageView middleImage = findViewById(R.id.middle_animation);
        mEnd = findViewById(R.id.end_animation);
        mMovingImage = findViewById(R.id.moving_image);

        mStart.setOnClickListener(getOnClickListener());
        mEnd.setOnClickListener(getOnClickListener());
        middleImage.setOnClickListener(getOnClickListener());
    }

    @NonNull
    private View.OnClickListener getOnClickListener() {
        return v -> runAnimation((ImageView) v);
    }

    private void runAnimation(ImageView v) {
        mStart.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mStart.getViewTreeObserver().removeOnPreDrawListener(this);

                Animator animator = ImageAnimator.ofImageView(mMovingImage, mMovingImage, v, mStart, mEnd);

                animator.setDuration(3000);
                animator.setInterpolator(new LinearInterpolator());
                animator.start();
                return true;
            }
        });

        mStart.invalidate();
    }
}
