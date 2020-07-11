package com.iqbalproject.pj_pos.model

data class HistoryPurchaseResponse (
    val status: Boolean? = null,
    val message: String? = null,
    val rownum_history: Int? = null,
    val result_history: List<Purchase>? = null
)