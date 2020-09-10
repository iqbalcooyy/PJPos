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
        @Field("paid") paid: Int,
        @Field("user") user: String
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
        @Field("purchase_price[]") purchase_price: List<Int>,
        @Field("user") user: String
    ): Call<TrxResponses>

    @POST("supplier")
    @FormUrlEncoded
    fun addSupplier(
        @Header("pjpos_key") api_key: String = Constants.API_KEY,
        @Field("supplier_name") supplier_name: String,
        @Field("supplier_address") supplier_address: String,
        @Field("supplier_telp") supplier_telp: String
    ): Call<EditResponse>

    @PUT("supplier")
    @FormUrlEncoded
    fun editSupplier(
        @Header("pjpos_key") api_key: String = Constants.API_KEY,
        @Field("supplier_id") supplier_id: String,
        @Field("supplier_name") supplier_name: String,
        @Field("supplier_address") supplier_address: String,
        @Field("supplier_telp") supplier_telp: String
    ): Call<EditResponse>

    @HTTP(method = "DELETE", path = "supplier", hasBody = true)
    @FormUrlEncoded
    fun deleteSupplier(
        @Header("pjpos_key") api_key: String = Constants.API_KEY,
        @Field("supplier_id") supplier_id: String
    ): Call<EditResponse>

    @POST("customers")
    @FormUrlEncoded
    fun addCustomer(
        @Header("pjpos_key") api_key: String = Constants.API_KEY,
        @Field("cust_name") cust_name: String,
        @Field("cust_address") cust_address: String,
        @Field("cust_telp") cust_telp: String
    ): Call<EditResponse>

    @PUT("customers")
    @FormUrlEncoded
    fun editCustomer(
        @Header("pjpos_key") api_key: String = Constants.API_KEY,
        @Field("cust_id") cust_id: String,
        @Field("cust_name") cust_name: String,
        @Field("cust_address") cust_address: String,
        @Field("cust_telp") cust_telp: String
    ): Call<EditResponse>

    @HTTP(method = "DELETE", path = "customers", hasBody = true)
    @FormUrlEncoded
    fun deleteCustomer(
        @Header("pjpos_key") api_key: String = Constants.API_KEY,
        @Field("cust_id") cust_id: String
    ): Call<EditResponse>

    @POST("stock")
    @FormUrlEncoded
    fun addStocks(
        @Header("pjpos_key") api_key: String = Constants.API_KEY,
        @Field("item_name") item_name: String,
        @Field("item_qty") item_qty: Int = 0,
        @Field("uom") uom: String,
        @Field("selling_price") selling_price: Int,
        @Field("purchase_price") purchase_price: Int
    ): Call<EditResponse>

    @PUT("stock")
    @FormUrlEncoded
    fun editStocks(
        @Header("pjpos_key") api_key: String = Constants.API_KEY,
        @Field("item_id") item_id: String,
        @Field("item_name") item_name: String,
        @Field("item_qty") item_qty: Int,
        @Field("uom") uom: String,
        @Field("selling_price") selling_price: Int,
        @Field("purchase_price") purchase_price: Int
    ): Call<EditResponse>

    @HTTP(method = "DELETE", path = "stock", hasBody = true)
    @FormUrlEncoded
    fun deleteStocks(
        @Header("pjpos_key") api_key: String = Constants.API_KEY,
        @Field("item_id") item_id: String
    ): Call<EditResponse>

    @POST("report")
    @FormUrlEncoded
    fun getReport(
        @Header("pjpos_key") api_key: String = Constants.API_KEY,
        @Field("start_date") start_date: String,
        @Field("end_date") end_date: String
    ): Call<GetReportResponse>

    @POST("trx_acc_receivable")
    @FormUrlEncoded
    fun trx_acc_receivable(
        @Header("pjpos_key") api_key: String = Constants.API_KEY,
        @Field("sale_id") sale_id: String,
        @Field("cust_id") cust_id: String,
        @Field("ar_total") ar_total: Int,
        @Field("user") user: String
    ): Call<TrxAccReceivResponse>

    @GET("trx_acc_receivable")
    fun getAccReceivable(
        @Header("pjpos_key") api_key: String = Constants.API_KEY
    ): Call<GetAccReceivResponse>

    @PUT("trx_acc_receivable")
    @FormUrlEncoded
    fun editAccReceivable(
        @Header("pjpos_key") api_key: String = Constants.API_KEY,
        @Field("ar_id") ar_id: String,
        @Field("ar_paid") ar_paid: String,
        @Field("notes") notes: String? = "",
        @Field("sale_id") sale_id: String
    ): Call<TrxAccReceivResponse>

    @GET("detail_acc_receivable")
    fun getDetailAccReceivable(
        @Header("pjpos_key") api_key: String = Constants.API_KEY,
        @Query("ar_id") ar_id: String
    ): Call<DetailAccReceivRespon>

    @GET("history_sales")
    fun getHistorySales(
        @Header("pjpos_key") api_key: String = Constants.API_KEY
    ): Call<HistorySalesResponse>

    @POST("history_sales")
    @FormUrlEncoded
    fun getHistorySalesDetail(
        @Header("pjpos_key") api_key: String = Constants.API_KEY,
        @Field("sale_id") sale_id: String
    ): Call<HistorySalesDetailResponse>

    @GET("history_purchase")
    fun getHistoryPurchase(
        @Header("pjpos_key") api_key: String = Constants.API_KEY
    ): Call<HistoryPurchaseResponse>

    @POST("history_purchase")
    @FormUrlEncoded
    fun getHistoryPurchaseDetail(
        @Header("pjpos_key") api_key: String = Constants.API_KEY,
        @Field("purchase_id") purchase_id: String
    ): Call<HistoryPurchaseDetailResponse>

}