package com.application.imagegallery.data.apiClient

import com.application.imagegallery.data.webService.response.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface APIService {

    /*@POST("api/")
    @FormUrlEncoded
    fun imagesCall(@QueryMap params: Map<String, String>): Call<HitsResponse?>?*/

    @GET("api/")
    fun imagesCall(
        @Query("key") key: String,
        @Query("q") search: String,
        @Query("image_type") imageType: String,
        @Query("page") page: String,
    ): Call<ApiResponse?>?


}




