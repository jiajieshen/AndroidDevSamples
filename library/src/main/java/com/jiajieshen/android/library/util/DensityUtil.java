package com.jiajieshen.android.library.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * 密度工具
 */
public final class DensityUtil {

    private DensityUtil() {
        throw new UnsupportedOperationException();
    }

    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    public static String densityDpiToString(int densityDpi) {
        if (densityDpi <= 120) {
            return "ldpi";
        } else if (densityDpi <= 160) {
            return "mdpi";
        } else if (densityDpi <= 240) {
            return "hdpi";
        } else if (densityDpi <= 320) {
            return "xhdpi";
        } else if (densityDpi <= 480) {
            return "xxhdpi";
        } else {
            return "xxxhdpi";
        }
    }

}