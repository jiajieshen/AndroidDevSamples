package com.jiajieshen.android.library.global;

import com.google.gson.Gson;

/**
 * Created by xin on 8/8/17.
 */

public class GsonHolder {

    private static Gson GSON = new Gson();

    public static final Gson getGson() {
        return GSON;
    }
}
