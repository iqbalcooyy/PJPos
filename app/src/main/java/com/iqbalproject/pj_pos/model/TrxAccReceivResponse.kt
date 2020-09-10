package com.iqbalproject.pj_pos.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class TrxAccReceivResponse(
    val status: Boolean? = null,
    val id: String? = null,
    val ar_status: String? = null,
    val message: String? = null
)

data class GetAccReceivResponse(
    val status: Boolean? = null,
    val message: String? = null,
    val rownum: Int? = null,
    val result: List<AccReceivable>? = null
)

@Parcelize
data class AccReceivable(
    val ar_date: String? = null,
    val ar_id: String? = null,
    val sale_id: String? = null,
    val cust_id: String? = null,
    val cust_name: String? = null,
    val cust_address: String? = null,
    val cust_telp: String? = null,
    val ar_total: String? = null,
    val remaining_payment: String? = null,
    val ar_status: String? = null,
    val created_by: String? = null
) : Parcelable
