package com.littleinferno.flowchart.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.littleinferno.flowchart.DataType;
import com.littleinferno.flowchart.R;

public final class ResUtil {

    private ResUtil() {
    }

    public static Drawable getArrayDrawable(Context context, boolean isArray) {
        if (isArray)
            return ContextCompat.getDrawable(context, R.drawable.ic_array_true);
        else
            return ContextCompat.getDrawable(context, R.drawable.ic_array_false);
    }

    public static int getDataTypeColor(Context context, DataType dataType) {
        switch (dataType) {
            case BOOL:
                return ContextCompat.getColor(context, R.color.Bool);
            case INT:
                return ContextCompat.getColor(context, R.color.Int);
            case FLOAT:
                return ContextCompat.getColor(context, R.color.Float);
            case STRING:
                return ContextCompat.getColor(context, R.color.String);
        }
        return 0;
    }
}