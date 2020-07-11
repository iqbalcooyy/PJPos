package com.iqbalproject.pj_pos.model

data class HistoryPurchaseDetailResponse(
    val status: Boolean? = null,
    val message: String? = null,
    val purchase_id: String? = null,
    val rownum_history_detail: Int? = null,
    val result_history_detail: List<HistoryPurchaseResult>? = null
)

data class HistoryPurchaseResult(
    val item_id: String? = null,
    val item_name: String? = null,
    val purchase_qty: String? = null,
    val purchase_uom: String? = null,
    val purchase_price: String? = null
)