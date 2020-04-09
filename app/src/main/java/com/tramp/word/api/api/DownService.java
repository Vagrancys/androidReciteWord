package com.tramp.word.api.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/29
 * version:1.0
 */
public interface DownService {
    @Streaming
    @GET("down/{path}")
    Call<ResponseBody> getDownLoad(@Path("path") String path);
}
