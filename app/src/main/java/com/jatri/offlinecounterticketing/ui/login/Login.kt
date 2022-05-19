package com.jatri.offlinecounterticketing.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jatri.offlinecounterticketing.R
import com.jatri.offlinecounterticketing.ui.components.RoundJatriButton

@Composable
fun LoginScreen(
    clickCallBack : (String,String) -> Unit
){
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.text_sign_in_to_your_account),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(
            value = phoneNumber ,
            onValueChange = { phoneNumber = it },
            label = { Text(text = stringResource(R.string.label_text_phone_number)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = password ,
            onValueChange = { password = it },
            label = { Text(text = stringResource(R.string.label_text_password)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Spacer(modifier = Modifier.height(20.dp))

        RoundJatriButton(stringResource(R.string.btn_text_login)) {
            clickCallBack.invoke(phoneNumber,password)
        }
    }
}