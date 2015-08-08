package com.example.nick.themechanger;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface API {
    /// CUSTOMS ///

    @GET("/customs/{id}")
    void getCustoms(@Path("id") String id, Callback<RestTheme> callback);

}
