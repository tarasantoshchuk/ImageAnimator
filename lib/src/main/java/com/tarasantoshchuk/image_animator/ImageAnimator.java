package com.tarasantoshchuk.image_animator;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.ImageView;

public class ImageAnimator {
    private ImageAnimator() {
        throw new UnsupportedOperationException();
    }

    public static Animator ofImageView(ImageView target, ImageView... values) {
        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.playTogether(
                ObjectAnimator.ofInt(target,"top", intValues(values, View::getTop)),
                ObjectAnimator.ofInt(target, "bottom", intValues(values, View::getBottom)),
                ObjectAnimator.ofInt(target, "left", intValues(values, View::getLeft)),
                ObjectAnimator.ofInt(target, "right", intValues(values, View::getRight)),
                ImageMatrixAnimator.ofImageMatrix(target, values)
        );

        return animatorSet;
    }

    private static int[] intValues(ImageView[] values, Converter<Integer> converter) {
        int[] result = new int[values.length];

        for (int i = 0; i < result.length; i++) {
            result[i] = converter.convert(values[i]);
        }

        return result;
    }

    private interface Converter<T> {
        T convert(ImageView view);
    }
}
