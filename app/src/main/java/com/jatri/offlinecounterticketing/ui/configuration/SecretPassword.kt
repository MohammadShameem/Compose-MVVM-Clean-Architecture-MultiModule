package com.jatri.offlinecounterticketing.ui.configuration

import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jatri.offlinecounterticketing.ui.components.JatriLogo
import com.jatri.offlinecounterticketing.ui.components.RoundJatriButton
import com.jatri.offlinecounterticketing.ui.components.ToolbarWithBackButton
import com.jatri.offlinecounterticketing.ui.theme.OfflineCounterTicketingTheme

@Composable
fun SecretPassword() {

    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        JatriLogo()
        var text by remember { mutableStateOf("") }

        Text(text = "Please Setup Configuration", fontWeight = FontWeight.Bold)

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Secret Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        )
        RoundJatriButton("Continue") {}
    }

}

@Preview
@Composable
fun SecretPasswordPrev() {
    OfflineCounterTicketingTheme {
        Surface {
            SecretPassword()
        }
    }
}
