package com.jatri.offlinecounterticketing.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jatri.offlinecounterticketing.ui.components.RoundJatriButton
import kotlin.reflect.typeOf

@Composable
fun LoginScreen(
    clickCallBack : (String,String) -> Unit
){
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    //val viewModel : LoginViewModel = viewModel()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Text(
            text = "Sign in to your account",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(
            value = phoneNumber ,
            onValueChange = { phoneNumber = it },
            label = { Text(text = "phone number") },
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
            label = { Text(text = "password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Spacer(modifier = Modifier.height(20.dp))

        RoundJatriButton("Login") {
            clickCallBack.invoke(phoneNumber,password)

            /*viewModel.login(params = LoginApiUseCase.Params(
                phoneNumber, password
            ))*/
        }
    }
}