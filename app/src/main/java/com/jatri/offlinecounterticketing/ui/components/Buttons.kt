package com.jatri.offlinecounterticketing.ui.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RoundJatriButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        modifier = Modifier
            //.align(alignment = Alignment.CenterHorizontally)
            .fillMaxWidth()
            .fillMaxHeight(.12f)
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Text(text = text)
    }
}