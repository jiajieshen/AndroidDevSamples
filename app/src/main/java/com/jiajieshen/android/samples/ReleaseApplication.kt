package com.jiajieshen.android.samples

import android.app.Application
import android.text.TextUtils
import com.jiajieshen.android.library.util.ProcessUtil
import com.tencent.bugly.crashreport.CrashReport


/**
 * Created by xin on 6/20/17.
 */
class ReleaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // get current process name
        val processName: String? = ProcessUtil.getProcessName(android.os.Process.myPid())
        if (TextUtils.equals(processName, packageName)) {

            // init Bugly
            CrashReport.initCrashReport(this, BuildConfig.BUGLY_APP_ID, BuildConfig.DEBUG)

            // init firebase
//            FirebaseAnalytics.getInstance(this)
        }

    }
}