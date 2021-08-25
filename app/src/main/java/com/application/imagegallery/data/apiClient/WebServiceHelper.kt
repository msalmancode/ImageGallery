package com.application.imagegallery.data.apiClient

import com.application.imagegallery.data.webServices.Response.HitsResponse
import com.application.imagegallery.uitls.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class WebServiceHelper : Constants() {

    private fun getClient(): OkHttpClient? {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    private fun getRetrofit(): Retrofit? {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }




    /*fun getImagesJsonCall(imageType: String, searchText: String): Call<HitsResponse?>? {
        val params: MutableMap<String, String> = HashMap()
        params["image_type"] = imageType
        params["q"] = searchText
        params["key"] = API_KEY
        return getRetrofit()?.create(APIService::class.java)?.imagesCall(params)
    }*/

    private var retrofit: Retrofit? = null

    fun getClientXML(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpClient())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build()
        }
        return retrofit
    }


    fun getImagesJsonCall(imageType: String, searchText: String): Call<HitsResponse?>? {
//        val params: MutableMap<String, String> = HashMap()
//        params["image_type"] = imageType
//        params["q"] = searchText
//        params["key"] = API_KEY
        return getRetrofit()?.create(APIService::class.java)?.imagesCall(API_KEY,searchText,imageType)
    }

}