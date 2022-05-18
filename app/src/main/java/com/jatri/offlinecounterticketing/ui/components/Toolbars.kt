package com.jatri.offlinecounterticketing.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jatri.offlinecounterticketing.ui.theme.colorPrimary

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
fun ToolbarWithButtonLarge(
    toolbarTitle: String,
    toolbarIcon: ImageVector,
    onButtonPressed: () -> Unit
) {
    Box(Modifier.background(color = colorPrimary)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 16.dp),
            //verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.clickable(onClick = onButtonPressed),
                imageVector = toolbarIcon,
                tint = Color.White,
                contentDescription = "Back"
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = toolbarTitle,
                fontSize = MaterialTheme.typography.h5.fontSize,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ToolbarPre() {
    ToolbarWithButtonLarge(
        "Sea Maritime Service",
        Icons.Filled.ArrowBack
    ) {
    }
}