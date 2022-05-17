package com.jatri.apiresponse.login

data class LoginApiResponse(
    val message: String,
    val offline_counterman: OfflineCounterman,
    val status: String,
    val token: String
)

data class OfflineCounterman(
    val address: Any,
    val company_id: Int,
    val created_at: String,
    val id: Int,
    val mobile: String,
    val name: String,
    val status: Int,
    val updated_at: String
)