package com.jatri.offlinecounterticketing.ui.components

import android.widget.Toolbar
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ToolbarWithBackButton(title: String, onBackPressed: () -> Unit) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(Icons.Filled.ArrowBack, contentDescription = null)
            }
        }
    )
}

@Composable
fun ToolbarWithBackButtonLarge() {
    Column {
        IconButton(onClick = {}) {
            
        }

    }
}

@Preview
@Composable
fun Toolbar() {
    ToolbarWithBackButtonLarge()
}