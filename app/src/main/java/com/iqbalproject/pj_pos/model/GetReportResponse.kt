package com.iqbalproject.pj_pos.model

data class GetReportResponse(
    val status: Boolean? = null,
    val message: String? = null,
    val rownum_sales: Int? = null,
    val result_sales: List<Sales>? = null,
    val rownum_purchase: Int? = null,
    val result_purchase: List<Purchase>? = null
)