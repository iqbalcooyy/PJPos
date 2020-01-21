package com.iqbalproject.pj_pos.model

data class Stocks (
    val stocks: List<StockDetail>? = null
)

data class StockDetail(
    val item_id: String? = null,
    val item_name: String? = null,
    val item_qty: Int? = null,
    val selling_price: Int? = null,
    val purchase_price: Int? = null
)