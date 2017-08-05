package com.jiajieshen.android.library.global;

import android.app.Activity;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * Created by sum on 3/5/16.
 */
public class CurrentActivityHolder {

    private static CurrentActivityHolder instance = new CurrentActivityHolder();
    private WeakReference<Activity> activityWeakRef;

    private CurrentActivityHolder() {
    }

    public static CurrentActivityHolder getInstance() {
        return instance;
    }

    @Nullable
    public Activity getActivity() {
        if (activityWeakRef != null) {
            return activityWeakRef.get();
        } else {
            return null;
        }
    }

    public void holdActivity(Activity activity) {
        activityWeakRef = new WeakReference<>(activity);
    }
}

