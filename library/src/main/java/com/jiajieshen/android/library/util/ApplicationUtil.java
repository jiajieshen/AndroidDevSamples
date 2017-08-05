package com.jiajieshen.android.library.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public final class ApplicationUtil {

    private ApplicationUtil() {
        throw new UnsupportedOperationException();
    }

    public static String getAppName(Context context) {
        String appName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            appName = context.getResources().getString(labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return appName;
    }

    public static String getMetaData(Context context, @NonNull String key) {
        String metaDataValue = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(
                        context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null && applicationInfo.metaData != null) {
                    metaDataValue = applicationInfo.metaData.getString(key);
                }
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return metaDataValue;
    }

    @CheckResult
    public static boolean checkApk(Context context, @NonNull String apkPath) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
            if (info != null) {
                return true;
            }
        } catch (Exception e) {
            Toast.makeText(context, "安装包失效", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public static void installApk(Context context, String apkPath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + apkPath), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    public static List<String> getAllAppPackageName(Activity activity) {
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        final PackageManager packageManager = activity.getPackageManager();
        List<ResolveInfo> apps = packageManager.queryIntentActivities(mainIntent, 0);
        List<String> packageNameList = new ArrayList<>(apps.size());
        for (int i = 0, size = apps.size(); i < size; i++) {
            ResolveInfo info = apps.get(i);
            packageNameList.add(info.activityInfo.applicationInfo.packageName);
        }
        return packageNameList;
    }

}