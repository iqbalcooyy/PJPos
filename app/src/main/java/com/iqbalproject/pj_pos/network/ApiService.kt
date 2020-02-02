package com.iqbalproject.pj_pos.network

import com.iqbalproject.pj_pos.model.LoginResponse
import com.iqbalproject.pj_pos.model.Stocks
import com.iqbalproject.pj_pos.utils.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("stock")
    fun getStocks(
        @Query("pjpos_key") api_key: String = Constants.API_KEY
    ): Call<Stocks>

    @GET("login_user")
    fun login(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("pjpos_key") api_key: String = Constants.API_KEY
    ): Call<LoginResponse>
}