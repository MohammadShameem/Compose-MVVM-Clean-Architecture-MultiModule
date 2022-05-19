package com.jatri.offlinecounterticketing.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jatri.offlinecounterticketing.ui.theme.lightViolet

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


@Composable
fun RoundJatriButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor
        ),
        modifier = Modifier
            //.align(alignment = Alignment.CenterHorizontally)
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .height(50.dp)
    ) {
        Text(text = text)
    }
}

@Composable
fun JatriRoundOutlinedButton(
    modifier: Modifier = Modifier,
    borderColor: Color = MaterialTheme.colors.primary,
    backgroundColor: Color = MaterialTheme.colors.primarySurface,
    text: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        border = BorderStroke(1.dp, borderColor),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor
        ),
        modifier = Modifier
            //.align(alignment = Alignment.CenterHorizontally)
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Text(text = text, color = lightViolet)
    }
}