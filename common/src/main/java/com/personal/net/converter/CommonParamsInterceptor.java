package com.personal.net.converter;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * okhttp 公共参数拦截器
 * Created by ly on 2023/4/20 18:24
 */
public class CommonParamsInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request.Builder builder = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "application/json");
        return chain.proceed(builder.build());
    }
}
