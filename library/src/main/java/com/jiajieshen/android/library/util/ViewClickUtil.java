package com.jiajieshen.android.library.util;

/**
 *
 */
public final class ViewClickUtil {

    private ViewClickUtil() {
        throw new UnsupportedOperationException();
    }

    private static long lastClickTime = 0;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        long FAST_DOUBLE_CLICK_TIME = 300;
        if (0 < timeD && timeD < FAST_DOUBLE_CLICK_TIME) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}