package com.jatri.offlinecounterticketing.ui.secretpassword

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jatri.offlinecounterticketing.ui.components.JatriLogo
import com.jatri.offlinecounterticketing.ui.components.RoundJatriButton
import com.jatri.offlinecounterticketing.ui.theme.OfflineCounterTicketingTheme

@Composable
fun SecretPassword(
    onContinueClick : (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var text by remember { mutableStateOf("") }
        JatriLogo()
        Text(text = "Please Setup Configuration", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.size(48.dp))
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Secret Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onContinueClick.invoke(text)
                }
            ),
        )
        Spacer(modifier = Modifier.size(48.dp))
        RoundJatriButton("Continue"){
            onContinueClick.invoke(text)
        }
    }
}

@Preview
@Composable
fun SecretPasswordPrev() {
    OfflineCounterTicketingTheme {
        Surface {
            SecretPassword{

            }
        }
    }
}
