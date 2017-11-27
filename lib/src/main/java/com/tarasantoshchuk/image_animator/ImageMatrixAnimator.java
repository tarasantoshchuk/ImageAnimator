package com.tarasantoshchuk.image_animator;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.widget.ImageView;

@SuppressWarnings("WeakerAccess")
public class ImageMatrixAnimator {
    static ValueAnimator ofImageMatrix(ImageView target, ImageView... values) {
        return ofImageMatrix(target, matrixValues(values));
    }

    public static ValueAnimator ofImageMatrix(ImageView target, Matrix... values) {
        return ObjectAnimator.ofObject(target, MatrixProperty.instance(), new MatrixEvaluator(), values);
    }

    private static Matrix[] matrixValues(ImageView[] values) {
        Matrix[] result = new Matrix[values.length];

        for (int i = 0; i < result.length; i++) {
            result[i] = getImageMatrix(values[i]);
        }

        return result;
    }

    @NonNull
    private static Matrix getImageMatrix(ImageView image) {
        float dH = image.getDrawable().getIntrinsicHeight();
        float dW = image.getDrawable().getIntrinsicWidth();

        float iH = image.getHeight();
        float iW = image.getWidth();

        switch (image.getScaleType()) {
            case CENTER:
                return centerMatrix(dH, dW, iH, iW);
            case CENTER_CROP:
                return centerCropMatrix(dH, dW, iH, iW);
            case CENTER_INSIDE:
                return centerInsideMatrix(dH, dW, iH, iW);
            case FIT_CENTER:
                return fitCenterMatrix(dH, dW, iH, iW);
            case FIT_END:
                return fitEndMatrix(dH, dW, iH, iW);
            case FIT_START:
                return fitStartMatrix(dH, dW, iH, iW);
            case FIT_XY:
                return fitXYMatrix(dH, dW, iH, iW);
            case MATRIX:
                return new Matrix(image.getImageMatrix());
            default:
                throw new IllegalStateException("unexpected value of ScaleType");
        }
    }

    private static Matrix fitXYMatrix(float dH, float dW, float iH, float iW) {
        Matrix matrix = new Matrix();

        matrix.postScale(iW/dW, iH/dH);

        return matrix;
    }

    private static Matrix fitEndMatrix(float dH, float dW, float iH, float iW) {
        Matrix matrix = new Matrix();

        matrix.postTranslate(iW - dW, iH - iW);

        float scale = scaleInside(dH, dW, iH, iW);

        matrix.postScale(scale, scale, iW, iH);

        return matrix;
    }

    private static Matrix fitStartMatrix(float dH, float dW, float iH, float iW) {
        Matrix matrix = new Matrix();

        float scale = scaleInside(dH, dW, iH, iW);
        matrix.postScale(scale, scale);
        return matrix;
    }

    private static float scaleInside(float dH, float dW, float iH, float iW) {
        return Math.min(iH/dH, iW/dW);
    }

    private static Matrix fitCenterMatrix(float dH, float dW, float iH, float iW) {
        Matrix matrix = centerMatrix(dH, dW, iH, iW);

        float scale = scaleInside(dH, dW, iH, iW);

        matrix.postScale(scale, scale, iW/2f, iH/2f);
        return matrix;
    }

    private static Matrix centerInsideMatrix(float dH, float dW, float iH, float iW) {
        Matrix matrix = centerMatrix(dH, dW, iH, iW);

        float scale = scaleInside(dH, dW, iH, iW);
        scale = Math.min(scale, 1f);

        matrix.postScale(scale, scale, iW/2f, iH/2f);
        return matrix;
    }

    private static Matrix centerCropMatrix(float dH, float dW, float iH, float iW) {
        Matrix matrix = centerMatrix(dH, dW, iH, iW);

        float scale = Math.max(iW/dW, iH/dH);

        matrix.postScale(scale, scale, iW/2f, iH/2f);
        return matrix;
    }

    private static Matrix centerMatrix(float dH, float dW, float iH, float iW) {
        Matrix matrix = new Matrix();
        matrix.postTranslate((iW - dW) / 2f, (iH - dH) / 2f);
        return matrix;
    }
}
