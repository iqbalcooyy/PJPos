package com.iqbalproject.pj_pos.model

data class Stocks(
    val stocks: List<StockDetail>? = null
)

data class StockDetail(
    val item_id: String,
    val item_name: String,
    val item_qty: Int,
    val selling_price: Int,
    val purchase_price: Int,
    var temporary: Int //only temporary variable for calculate total pay
)