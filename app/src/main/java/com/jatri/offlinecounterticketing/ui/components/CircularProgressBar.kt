package com.jatri.offlinecounterticketing.ui.components
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jatri.offlinecounterticketing.ui.theme.colorPrimary

@Composable
fun CircularProgressBar(isDisplayed: Boolean) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (isDisplayed) {
            CircularProgressIndicator(
                color = colorPrimary
            )
        }
    }
}