package com.jiajieshen.android.library.util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.widget.Toast;

/**
 * Created by sum on 1/26/16.
 */
public final class ToastUtil {

    private static Toast toast;

    private ToastUtil() {
        throw new UnsupportedOperationException();
    }

    @Nullable
    private static Toast getToast(Context context) {
        if (context == null) {
            return null;
        }
        if (toast == null) {
            Context applicationContext = context.getApplicationContext();
            toast = Toast.makeText(applicationContext, "", Toast.LENGTH_SHORT);
        }
        return toast;
    }

    @UiThread
    public static void showShort(Context context, String message) {
        Toast toast = getToast(context);
        if (toast != null) {
            toast.setText(message);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @UiThread
    public static void showShort(Context context, @StringRes int message) {
        Toast toast = getToast(context);
        if (toast != null) {
            toast.setText(message);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @UiThread
    public static void showLong(Context context, String message) {
        Toast toast = getToast(context);
        if (toast != null) {
            toast.setText(message);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @UiThread
    public static void showLong(Context context, @StringRes int message) {
        Toast toast = getToast(context);
        if (toast != null) {
            toast.setText(message);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
