package com.jatri.offlinecounterticketing.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import com.jatri.offlinecounterticketing.R

@Composable
fun JatriLogo() {
    Image(
        painter = painterResource(id = R.drawable.ic_app_logo),
        contentDescription = "Jatri Service Limited Logo"
    )
}