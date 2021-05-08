package com.example.myslotmachine2.ImageViewScrolling;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myslotmachine2.R;

public class ImageViewScrolling extends FrameLayout {

    private ImageView current_image, next_image;

    private int old_value = 0;

    private IEventEnd eventEnd;

    public void setEventEnd(IEventEnd eventEnd) {
        this.eventEnd = eventEnd;
    }

    public ImageViewScrolling(@NonNull Context context) {
        super(context);
        unit(context);
    }

    public ImageViewScrolling(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        unit(context);
    }

    private void unit(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_view_scrolling, this);
        current_image = view.findViewById(R.id.current_image);
        next_image = view.findViewById(R.id.next_image);

    }

    public void setValueRandom(int set_image_number) {
        int animation_dur = 300;
        current_image.animate().translationY(-getHeight()).setDuration(animation_dur).start();
        next_image.setTranslationY(next_image.getHeight());
        next_image.animate().translationY(0)
                .setDuration(animation_dur)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        setImage(current_image, old_value);
                        current_image.setTranslationY(0);
                        if (old_value != set_image_number) {
                            setValueRandom(set_image_number);
                            old_value++;
                        } else {
                            old_value = 0;
                            setImage(next_image, set_image_number);
                            eventEnd.eventEnd();
                        }

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }

    private void setImage(ImageView image_view, int value) {

        switch (value) {
            case (0):
                image_view.setImageResource(R.drawable.bar_done);
                break;

            case (1):
                image_view.setImageResource(R.drawable.lemon_done);
                break;

            case (2):
                image_view.setImageResource(R.drawable.orange_done);
                break;

            case (3):
                image_view.setImageResource(R.drawable.sevent_done);
                break;

            case (4):
                image_view.setImageResource(R.drawable.triple_done);
                break;

            case (5):
                image_view.setImageResource(R.drawable.waternelon_done);
                break;

            case (6):
                image_view.setImageResource(R.drawable.cherry_done);
                break;
        }

        image_view.setTag(value);
    }

    public int getValue() {
        return Integer.parseInt(next_image.getTag().toString());
    }
}
