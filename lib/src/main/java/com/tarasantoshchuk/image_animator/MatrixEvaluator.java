package com.tarasantoshchuk.image_animator;


import android.animation.TypeEvaluator;
import android.graphics.Matrix;

public class MatrixEvaluator implements TypeEvaluator<Matrix> {
    private float[] mStartValues = new float[9];
    private float[] mEndValues = new float[9];
    private float[] mResultValues = new float[9];


    @Override
    public Matrix evaluate(float fraction, Matrix startValue, Matrix endValue) {
        if (startValue == null || endValue == null) {
            return null;
        }

        startValue.getValues(mStartValues);
        endValue.getValues(mEndValues);

        for (int i = 0; i < 9; i++) {
            mResultValues[i] = mStartValues[i] + (mEndValues[i] - mStartValues[i]) * fraction;
        }

        Matrix result = new Matrix();
        result.setValues(mResultValues);

        return result;
    }
}
