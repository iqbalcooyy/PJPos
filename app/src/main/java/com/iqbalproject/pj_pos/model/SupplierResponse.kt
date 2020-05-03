package com.iqbalproject.pj_pos.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Suppliers(
    val status: Boolean? = null,
    val message: String? = null,
    val rownum: Int? = null,
    val result: List<SupplierResult>? = null
)

@Parcelize
data class SupplierResult(
    val supplier_id: String? = null,
    val supplier_name: String? = null,
    val supplier_address: String? = null,
    val supplier_telp: String? = null
) : Parcelable