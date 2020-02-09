package com.iqbalproject.pj_pos.model

data class Customers(
    val status: String? = null,
    val message: String? = null,
    val result: List<CustomerResult>? = null
)

data class CustomerResult(
    val cust_id: String? = null,
    val cust_name: String? = null,
    val cust_address: String? = null,
    val cust_telp: String? = null
)