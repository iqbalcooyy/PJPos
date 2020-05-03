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
    val uom: String,
    // for Sales or Purchase Confirmation :
    var id_dummy: String? = null,
    var name_dummy: String? = null,
    var address_dummy: String? = null,
    var telp_dummy: String? = null,
    var qty_dummy: Int = 0,
    var amount_dummy: Int = 0
) : Parcelable