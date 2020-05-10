package com.iqbalproject.pj_pos.model

data class DetailAccReceivRespon(
    val status: Boolean? = null,
    val message: String? = null,
    val rownum: Int? = null,
    val result: List<DetailAccReceivable>? = null
)

data class DetailAccReceivable(
    val ar_detail_id: String? = null,
    val ar_id: String? = null,
    val ar_paid: String? = null,
    val notes: String? = null,
    val ar_paid_date: String? = null
)
