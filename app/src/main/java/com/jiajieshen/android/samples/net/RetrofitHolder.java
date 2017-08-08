package com.jiajieshen.android.samples.net;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.jiajieshen.android.samples.BuildConfig;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xin on 6/5/17.
 */

public class RetrofitHolder {

    public static final String HOST = "https://fcm.googleapis.com";

    public static Retrofit retrofit;

    public static void init(Context context) {
        if (retrofit != null) {
            return;
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(new StethoInterceptor());
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(HOST + "/")
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static AppService getAppService() {
        return AppServiceHolder.appService;
    }

    private static class AppServiceHolder {
        private static AppService appService = retrofit.create(AppService.class);
    }
}
