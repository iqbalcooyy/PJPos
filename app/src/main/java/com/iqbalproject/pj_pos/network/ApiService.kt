package com.iqbalproject.pj_pos.network

import com.iqbalproject.pj_pos.model.LoginResponse
import com.iqbalproject.pj_pos.model.Stocks
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("read_stock.php")
    fun getStocks(): Call<Stocks>

    @FormUrlEncoded
    @POST("login.php")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginResponse>
}