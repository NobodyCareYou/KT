package com.personal.eternity;


import com.personal.net.resp.BaseResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ReqApi {

    @GET("api/xjj")
    Call<BaseResponse<String>> findRandomVideo(@Query("type") String type);

}
