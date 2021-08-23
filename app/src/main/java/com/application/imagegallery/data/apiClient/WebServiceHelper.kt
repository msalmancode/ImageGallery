package com.application.imagegallery.data.apiClient

import com.application.imagegallery.data.model.WebServiceRequest.HitsResponse
import com.application.imagegallery.uitls.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class WebServiceHelper : Constants() {

    private fun getClient(): OkHttpClient? {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .build()
    }

    private fun getRetrofit(): Retrofit? {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    fun getImagesJsonCall(imageType: String, searchText: String): Call<HitsResponse?>? {
        val params: MutableMap<String, String> = HashMap()
        params["image_type"] = imageType
        params["q"] = searchText
        params["key"] = API_KEY
        return getRetrofit()?.create(APIService::class.java)?.imagesCall(params)
    }


}