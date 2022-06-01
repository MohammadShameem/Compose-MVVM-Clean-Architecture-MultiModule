package com.jatri.offlinecounterticketing.ui.secretpassword

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jatri.offlinecounterticketing.R
import com.jatri.offlinecounterticketing.ui.components.JatriLogo
import com.jatri.offlinecounterticketing.ui.components.RoundJatriButton
import com.jatri.offlinecounterticketing.ui.theme.OfflineCounterTicketingTheme

@Composable
fun SecretPassword(
    onContinueClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var text by remember { mutableStateOf("") }
        JatriLogo()
        Text(
            text = stringResource(R.string.please_setup_configuration),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(48.dp))
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(stringResource(R.string.label_secret_password)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    /**
                     * Send the entered text if user clicks soft keyboards done button
                     * to check if it matched with our predefined password
                     * */
                    onContinueClick.invoke(text)
                }
            ),
        )
        Spacer(modifier = Modifier.size(48.dp))
        RoundJatriButton(stringResource(R.string.btn_continue)) {
            /**
             * Send the entered text if user clicks Continue button
             * to check if it matched with our predefined password
             * */
            onContinueClick.invoke(text)
        }
    }
}

@Preview
@Composable
fun SecretPasswordPrev() {
    OfflineCounterTicketingTheme {
        Surface {
            SecretPassword {
            }
        }
    }
}