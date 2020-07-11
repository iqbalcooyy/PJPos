package com.iqbalproject.pj_pos.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class GetTrxPurchaseResponse(
    val status: String? = null,
    val message: String? = null,
    val result: List<Purchase>? = null
)

@Parcelize
data class Purchase(
    val purchase_id: String? = null,
    val supplier_id: String? = null,
    val supplier_name: String? = null,
    val purchase_price_total: String? = null,
    val purchase_date: String? = null,
    val created_by: String? = null

) : Parcelable
