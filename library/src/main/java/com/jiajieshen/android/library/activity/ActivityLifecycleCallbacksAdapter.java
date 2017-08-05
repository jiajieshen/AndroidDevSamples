package com.jiajieshen.android.library.activity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created by sum on 3/14/16.
 */
public class ActivityLifecycleCallbacksAdapter implements Application.ActivityLifecycleCallbacks {

    @Override
    public final void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public final void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public final void onActivityPaused(Activity activity) {
    }

    @Override
    public final void onActivityStopped(Activity activity) {
    }

    @Override
    public final void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public final void onActivityDestroyed(Activity activity) {
    }
}
