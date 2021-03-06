package com.iqbalproject.pj_pos.model

data class LoginResponse(
    val status: String? = null,
    val message: String? = null,
    val result: List<LoginResult>? = null
)

data class LoginResult(
    val username: String? = null,
    val password: String? = null,
    val position: String? = null,
    val fullname: String? = null
)