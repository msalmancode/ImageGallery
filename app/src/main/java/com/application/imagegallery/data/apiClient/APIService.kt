package com.application.imagegallery.data.apiClient

import com.application.imagegallery.data.model.WebServiceRequest.HitsResponse
import retrofit2.Call
import retrofit2.http.FieldMap


interface APIService {

    fun imagesCall(@FieldMap params: MutableMap<String, String>): Call<HitsResponse?>?


}