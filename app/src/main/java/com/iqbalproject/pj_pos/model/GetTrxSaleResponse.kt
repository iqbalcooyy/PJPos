package com.iqbalproject.pj_pos.model

data class GetTrxSaleResponse(
    val status: String? = null,
    val message: String? = null,
    val result: List<Sales>? = null
)

data class Sales(
    val sale_id: String? = null,
    val cust_id: String? = null,
    val to_be_paid: String? = null,
    val discount: String? = null,
    val paid: String? = null,
    val status: String? = null,
    val sale_date: String? = null
)