package com.jatri.apiresponse.dashboard

data class ChangePasswordProfileInfoApiResponse(
    val code: Int,
    val message: String,
    val profile: Profile,
    val status: String
)

data class Profile(
    val company_id: Int,
    val created_at: String,
    val id: Int,
    val mobile: String,
    val name: String,
    val status: Int,
    val updated_at: String
)