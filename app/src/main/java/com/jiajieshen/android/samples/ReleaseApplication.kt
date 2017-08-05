package com.jiajieshen.android.samples

import android.app.Application
import android.text.TextUtils
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.iid.FirebaseInstanceId
import com.jiajieshen.android.library.util.ProcessUtil
import com.tencent.bugly.crashreport.CrashReport
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


/**
 * Created by xin on 6/20/17.
 */
class ReleaseApplication : Application(),AnkoLogger {

    override fun onCreate() {
        super.onCreate()

        // get current process name
        val processName: String? = ProcessUtil.getProcessName(android.os.Process.myPid())
        if (TextUtils.equals(processName, packageName)) {

            // init Bugly
            CrashReport.initCrashReport(this, BuildConfig.BUGLY_APP_ID, BuildConfig.DEBUG)

            // init firebase
            FirebaseAnalytics.getInstance(this)

            val refreshedToken = FirebaseInstanceId.getInstance().token
            info("Refreshed token: " + refreshedToken!!)
        }

    }
}