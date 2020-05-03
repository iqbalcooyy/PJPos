package com.iqbalproject.pj_pos.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Customers(
    val status: Boolean? = null,
    val message: String? = null,
    val rownum: Int? = null,
    val result: List<CustomerResult>? = null
)

@Parcelize
data class CustomerResult(
    val cust_id: String? = null,
    val cust_name: String? = null,
    val cust_address: String? = null,
    val cust_telp: String? = null
) : Parcelable