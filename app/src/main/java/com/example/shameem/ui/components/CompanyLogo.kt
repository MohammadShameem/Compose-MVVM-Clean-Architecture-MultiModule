package com.example.shameem.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.shameem.R

@Composable
fun CompanyLogo() {
    Image(
        painter = painterResource(id = R.drawable.ic_app_logo),
        contentDescription = "Company Logo",
        modifier = Modifier.size(160.dp)
    )
}