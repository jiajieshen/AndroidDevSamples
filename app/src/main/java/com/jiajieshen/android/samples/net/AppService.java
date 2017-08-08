package com.jiajieshen.android.samples.net;

import com.jiajieshen.android.samples.model.FirebaseResult;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AppService {
    @Headers({
            "Authorization: key=AAAAL91tEow:APA91bGX_57lpMtgr83rFFINZOXaJbqpK0s3xxd_UNWgXMpzQXxweHYmtxuQYs8pw3-TXo5tUd69YGbhgXiggHTrwHb34hp-dhqfSlZphWz7nkPuJXu8ikv4mlPuOwMk4h97r5UgLmQY",
            "Content-Type: application/json"
    })
    @POST("fcm/send")
    Observable<FirebaseResult> test(@Body RequestBody body);
}