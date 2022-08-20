package com.example.shameem.ui.components
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.shameem.ui.theme.colorPrimary

/**
 * This will be used at the time of reportPrint and sync data
 * @param: isDisplayed as Boolean which will be used to determined when our progressBar will be shown or hide
 * */
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