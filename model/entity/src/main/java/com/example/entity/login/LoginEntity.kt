package com.example.entity.login

data class LoginEntity(
    val message: String,
    val status: String,
    val token: String,
    val address: Any,
    val company_id: Int,
    val created_at: String,
    val id: Int,
    val mobile: String,
    val name: String,
    val updated_at: String
)