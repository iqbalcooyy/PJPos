package com.iqbalproject.pj_pos.model

data class HistorySalesDetailResponse(
    val status: Boolean? = null,
    val message: String? = null,
    val rownum_history_detail: Int? = null,
    val result_history_detail: List<HistorySalesResult>? = null
)

data class HistorySalesResult(
    val sale_id: String? = null,
    val item_id: String? = null,
    val item_name: String? = null,
    val sale_qty: String? = null,
    val uom: String? = null
)