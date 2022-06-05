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

/**
 * @param modifier Modifier to be applied to the button
 * @param backgroundColor Background color to be applied to the button. Default is Theme Primary Color
 * @param text Button Text
 * @param onClick Button On CLick Behaviour
 * */

@Composable
fun RoundJatriButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.primary,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor
        ),
        modifier = modifier
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
    textColor : Color=MaterialTheme.colors.onPrimary,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        border = BorderStroke(1.dp, borderColor),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor
        ),
        modifier = modifier
    ) {
        Text(text = text, color = textColor)
    }
}