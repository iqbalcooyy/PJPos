package com.iqbalproject.pj_pos.model

data class GetTrxPurchaseResponse(
    val status: String? = null,
    val message: String? = null,
    val result: List<Purchase>? = null
)

data class Purchase(
    val purchase_id: String? = null,
    val supplier_id: String? = null,
    val purchase_price_total: String? = null,
    val purchase_date: String? = null
)
