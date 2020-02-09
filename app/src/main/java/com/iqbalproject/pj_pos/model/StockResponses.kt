package com.iqbalproject.pj_pos.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Stocks(
    val status: String? = null,
    val message: String? = null,
    val result: List<StockDetail>? = null
)

@Parcelize
data class StockDetail(
    val item_id: String,
    val item_name: String,
    val item_qty: Int,
    val selling_price: Int,
    val purchase_price: Int,
    // for Sales Confirmation :
    var cust_id: String? = null,
    var cust_name: String? = null,
    var cust_address: String? = null,
    var cust_telp: String? = null,
    var sale_qty: Int = 0,
    var pay: Int = 0
) : Parcelable