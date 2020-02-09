package com.iqbalproject.pj_pos.network

import com.iqbalproject.pj_pos.model.Customers
import com.iqbalproject.pj_pos.model.LoginResponse
import com.iqbalproject.pj_pos.model.SalesResponses
import com.iqbalproject.pj_pos.model.Stocks
import com.iqbalproject.pj_pos.utils.Constants
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("stock")
    fun getStocks(
        @Header("pjpos_key") api_key: String = Constants.API_KEY
    ): Call<Stocks>

    @GET("login_user")
    fun login(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("pjpos_key") api_key: String = Constants.API_KEY
    ): Call<LoginResponse>

    @POST("trx_sales")
    @FormUrlEncoded  // FormUrlEncoded akan menampilkan inputan pada URL
    fun trx_sales(
        @Header("pjpos_key") api_key: String = Constants.API_KEY,
        @Field("cust_id") cust_id: String,
        @Field("item_id[]") item_id: List<String>,
        @Field("discount") discount: Int,
        @Field("total_pay") total_pay: Int,
        @Field("sale_qty[]") sale_qty: List<Int>
        ): Call<SalesResponses>

    @GET("customers")
    fun getCustomers(
        @Header("pjpos_key") api_key: String = Constants.API_KEY
    ): Call<Customers>
}