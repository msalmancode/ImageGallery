package com.application.imagegallery.data.apiClient

import com.application.imagegallery.data.model.WebServiceRequest.HitsResponse
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface APIService {

    @POST("api/")
    @FormUrlEncoded
    fun imagesCall(@FieldMap params: Map<String, String>): Call<HitsResponse?>?


}