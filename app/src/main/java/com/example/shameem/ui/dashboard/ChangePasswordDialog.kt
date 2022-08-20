package com.example.shameem.ui.dashboard

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.common.constant.AppConstant
import com.example.domain.usecase.dashboard.ChangePasswordApiUseCase
import com.example.entity.res.ApiResponse
import com.example.shameem.R
import com.example.shameem.ui.theme.lightGrey
import kotlinx.coroutines.launch

/**
 * This is our custom dialog
 * Which will be used at the time of Change Password
 * @param:isDialogOpen as boolean value which will determine when dialog will be shown or hide
 * */
@Composable
fun ChangePasswordDialog(
    isDialogOpen : MutableState<Boolean>
) {
    val dashboardViewModel: DashboardViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }

    if(isDialogOpen.value){
        Dialog(
            onDismissRequest = {
                isDialogOpen.value = false
            },
        ) {
            Surface(
                modifier = Modifier
                    .width(300.dp)
                    .height(300.dp),
                shape = RoundedCornerShape(5.dp),
                color = lightGrey
            ) {
                Column {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = stringResource(R.string.text_change_password),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = oldPassword,
                            onValueChange = { oldPassword = it },
                            label = { Text(text = stringResource(R.string.text_enter_old_password)) },
                            modifier = Modifier
                                .fillMaxWidth(),
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            )
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = newPassword,
                            onValueChange = { newPassword = it },
                            label = { Text(text = stringResource(R.string.text_enter_new_password)) },
                            modifier = Modifier
                                .fillMaxWidth(),
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            )
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.End

                        ) {
                            Button(
                                onClick = {
                                    isDialogOpen.value = false
                                }
                            ) {
                                Text(text = stringResource(R.string.btn_text_cancel))
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Button(
                                onClick = {
                                    coroutineScope.launch {
                                        val message  = dashboardViewModel.validateOldPasswordAndNewPassword(oldPassword,newPassword)
                                        if(message == AppConstant.validation_successful){
                                            dashboardViewModel.changePassword(ChangePasswordApiUseCase.Params(
                                                oldPassword,newPassword
                                            )).observe(lifecycleOwner) {
                                                when(it){
                                                    is ApiResponse.Success -> {
                                                        isDialogOpen.value = false
                                                        Toast.makeText(context,context.getString(R.string.msg_success_update_password),Toast.LENGTH_SHORT).show()
                                                    }
                                                    is ApiResponse.Failure -> Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                                                    else -> {}
                                                }
                                            }
                                        }else  Toast.makeText(context,context.getString(message),Toast.LENGTH_SHORT).show()

                                    }

                                }
                            ) {
                                Text(text = stringResource(R.string.btn_text_update))
                            }
                        }
                    }
                }
            }
        }
    }
}

