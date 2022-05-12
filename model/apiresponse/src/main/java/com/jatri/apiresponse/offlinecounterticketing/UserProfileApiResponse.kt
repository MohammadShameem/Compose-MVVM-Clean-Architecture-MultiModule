package com.jatri.apiresponse.offlinecounterticketing

data class UserProfileApiResponse(
    val code: Int,
    val status: String,
    val message: String,
    val token: String?,
    val user: UserProfileData
)

data class UserProfileData(
    val address: String?,
    val balance: String?,
    val city: String?,
    val created_at: String?,
    val date_of_birth: String?,
    val device_id: String?,
    val device_token: String?,
    val device_type: String?,
    val email: String?,
    val first_name: String?,
    val gender: String?,
    val id: Int,
    val image: String?,
    val last_name: String?,
    val my_ref_code: String?,
    val phone: String,
    val refer_by: String?,
    val role_id: Int?,
    val tracking_active_to_date: String?,
    val tracking_status: Int?,
    val updated_at: String?,
    val point: Double?,
    val g_m_a: GoogleMapApi?
)

data class GoogleMapApi(
    val android_user:String,
)


