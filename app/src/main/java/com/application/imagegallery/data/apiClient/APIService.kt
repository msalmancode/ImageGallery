package com.application.imagegallery.data.apiClient

import com.application.imagegallery.data.webServices.Response.HitsResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Path


interface APIService {

    /* @POST("api/")
     @FormUrlEncoded
     fun imagesCall(@FieldMap params: Map<String, String>): Call<HitsResponse?>?*/


    @FormUrlEncoded
    @GET("api/?key={key}&q={q}&image_type={image_type}")
    fun imagesCall(
        @Path("key") empID: String?,
        @Path("q") search: String?,
        @Field("image_type") image_type: String?
    ): Call<HitsResponse?>?


}