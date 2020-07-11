package com.iqbalproject.pj_pos.model

data class HistorySalesResponse(
    val status: Boolean? = null,
    val message: String? = null,
    val rownum_history: Int? = null,
    val result_history: List<Sales>? = null
)