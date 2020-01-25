package com.iqbalproject.pj_pos.network

import com.iqbalproject.pj_pos.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkConfig {

    //Configuration for Interceptor
    fun getInterceptor(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level =
            HttpLoggingInterceptor.Level.BODY //Level Body utk menampilkan semua isi response nya(end point s/d json nya)

        var okhttp = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
            .connectTimeout(Constants.REQ_TIMEOUT_DURATION.toLong(), TimeUnit.SECONDS)
            .writeTimeout(Constants.REQ_TIMEOUT_DURATION.toLong(), TimeUnit.SECONDS)
            .readTimeout(Constants.REQ_TIMEOUT_DURATION.toLong(), TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

        return okhttp
    }

    //Configuration for Network Library
    fun getNetwork(): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .client(getInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //Configuration for get service or Request to Server
    fun api(): ApiService {
        return getNetwork().create(ApiService::class.java)
    }
}