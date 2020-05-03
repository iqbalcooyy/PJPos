package com.iqbalproject.pj_pos.network

import com.iqbalproject.pj_pos.model.*
import com.iqbalproject.pj_pos.utils.Constants
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("stock")
    fun getStocks(
        @Header("pjpos_key") api_key: String = Constants.API_KEY
    ): Call<Stocks>

    @GET("customers")
    fun getCustomers(
        @Header("pjpos_key") api_key: String = Constants.API_KEY
    ): Call<Customers>

    @GET("supplier")
    fun getSuppliers(
        @Header("pjpos_key") api_key: String = Constants.API_KEY
    ): Call<Suppliers>

    @POST("login_user")
    @FormUrlEncoded
    fun login(
        @Header("pjpos_key") api_key: String = Constants.API_KEY,
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @POST("trx_sales")
    @FormUrlEncoded  // FormUrlEncoded akan menampilkan inputan pada URL
    fun trx_sales(
        @Header("pjpos_key") api_key: String = Constants.API_KEY,
        @Field("cust_id") cust_id: String,
        @Field("item_id[]") item_id: List<String>,
        @Field("discount") discount: Int,
        @Field("to_be_paid") to_be_paid: Int,
        @Field("sale_qty[]") sale_qty: List<Int>,
        @Field("paid") paid: Int
        ): Call<TrxResponses>

    @POST("trx_purchase")
    @FormUrlEncoded  // FormUrlEncoded akan menampilkan inputan pada URL
    fun trx_purchase(
        @Header("pjpos_key") api_key: String = Constants.API_KEY,
        @Field("supplier_id") supplier_id: String,
        @Field("purchase_price_total") price_total: Int,
        @Field("item_id[]") item_id: List<String>,
        @Field("purchase_qty[]") purchase_qty: List<Int>,
        @Field("purchase_uom[]") purchase_uom: List<String>,
        @Field("purchase_price[]") purchase_price: List<Int>
    ): Call<TrxResponses>

    @POST("trx_acc_receivable")
    @FormUrlEncoded
    fun trx_acc_receivable(
        @Header("pjpos_key") api_key: String = Constants.API_KEY,
        @Field("sale_id") sale_id: String,
        @Field("cust_id") cust_id: String,
        @Field("ar_total") ar_total: Int
    ): Call<TrxAccReceivResponse>
}