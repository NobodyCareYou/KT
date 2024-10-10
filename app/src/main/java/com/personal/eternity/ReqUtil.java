package com.personal.eternity;

import android.annotation.SuppressLint;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.personal.net.converter.CommonParamsInterceptor;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReqUtil {
    private static final String TAG = "HttpUtil";
    private static final long CONNECTION_TIMEOUT = 15;
    private static final long READ_TIMEOUT = 20;
    private static final long WRITE_TIMEOUT = 20;
    private static final ReqUtil INSTANCE = new ReqUtil();

    private static volatile ReqApi iRequests;


    private ReqUtil() {
    }

    public static ReqUtil getInstance() {
        return INSTANCE;
    }

    public static ReqApi get() {
        if (iRequests == null) {
            synchronized (ReqUtil.class) {
                if (iRequests == null) {
                    iRequests = INSTANCE.getRetrofit();
                }
            }
        }
        return iRequests;
    }




    private ReqApi getRetrofit() {
        return new Retrofit.Builder()
            .baseUrl("https://api.kuleu.com/")
            .client(getOkHttpBuilder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ReqApi.class);
    }




    @SuppressLint("CustomX509TrustManager")
    private OkHttpClient.Builder getOkHttpBuilder(boolean needCommonParams) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //设置超时
        builder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        if (needCommonParams) {
            builder.addInterceptor(new CommonParamsInterceptor());
        }
        if (BuildConfig.DEBUG ) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
            builder.addNetworkInterceptor(new StethoInterceptor());
        }
        return builder;
    }

    private OkHttpClient.Builder getOkHttpBuilder() {
        return getOkHttpBuilder(true);
    }


    public void clearConnPools() {
        try {
            getOkHttpBuilder().build().connectionPool().evictAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //重制request，一般用于更新公共参数
    public void resetReq() {
        iRequests = null;
    }
}
