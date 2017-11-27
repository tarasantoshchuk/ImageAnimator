package com.tarasantoshchuk.image_animator;


import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.Property;
import android.widget.ImageView;

@SuppressWarnings("WeakerAccess")
public class MatrixProperty extends Property<ImageView, Matrix> {
    private static MatrixProperty sInstance = new MatrixProperty();

    private MatrixProperty() {
        super(Matrix.class, "imageMatrix");
    }

    @Override
    public Matrix get(ImageView object) {
        return new Matrix(object.getImageMatrix());
    }

    @Override
    public void set(ImageView object, Matrix value) {
        Drawable drawable = object.getDrawable();
        if (drawable == null) {
            return;
        }

        if (value == null) {
            drawable.setBounds(0, 0, object.getWidth(), object.getHeight());
        } else {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            object.setImageMatrix(new Matrix(value));
        }

        object.invalidate();
    }

    public static MatrixProperty instance() {
        return sInstance;
    }
}
