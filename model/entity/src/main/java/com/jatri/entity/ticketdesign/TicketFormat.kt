package com.jatri.entity.ticketdesign

data class TicketFormat(
    val font_size: Int,
    val is_bold: Boolean,
    val is_center: Boolean,
    val is_dynamic: Boolean=false,
    val leading_student_fare_text: String="",
    val leading_text: String="",
    val name: String="",
    val text: String="",
    val trailing_text: String="",
    val type: String
)