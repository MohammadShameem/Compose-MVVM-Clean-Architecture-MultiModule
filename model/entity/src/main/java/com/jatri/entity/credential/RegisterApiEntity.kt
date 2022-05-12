package com.jatri.entity.credential

data class RegisterApiEntity(
    val code: Int,
    val message: String,
    val status: String,
    val token: String
)