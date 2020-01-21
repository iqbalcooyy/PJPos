package com.iqbalproject.pj_pos.network

import com.iqbalproject.pj_pos.model.Stocks
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("read_stock.php")
    fun getStocks(): Call<Stocks>
}