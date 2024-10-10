package com.personal.eternity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.personal.net.resp.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * 网络请求回调
 *
 * @author Liu
 */
public abstract class ReqCallback<T> implements Callback<BaseResponse<T>> {


    public ReqCallback() {

    }

    @Override
    public void onResponse(@NonNull Call<BaseResponse<T>> call, @NonNull Response<BaseResponse<T>> response) {
        BaseResponse<T> body = response.body();
        String msg;
        int code = -1;
        if (body != null) {
            msg = body.msg;
            if (response.isSuccessful()) {
                code = body.code;
                switch (code) {
                    case 0:
                    case 200:
                        onSuccess(body.video);
                        return;
                    case 401:
                        break;
                    default:
                        break;
                }
            }
        } else {
            msg = "fail";
        }
        onFailure(code, msg);
    }

    @Override
    public void onFailure(@NonNull Call<BaseResponse<T>> call, @NonNull Throwable t) {
        onFailure(999,t.getMessage());
    }

    /**
     * 成功
     *
     * @param t object
     */
    public void onSuccess(@Nullable T t) {
    }



    public void onFailure(int code, String msg) {
    }
}
