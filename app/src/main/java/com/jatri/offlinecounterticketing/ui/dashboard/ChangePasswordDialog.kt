package com.jatri.offlinecounterticketing.ui.dashboard

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jatri.domain.usecase.dashboard.ChangePasswordApiUseCase
import com.jatri.entity.res.ApiResponse
import kotlinx.coroutines.launch

@Composable
fun ChangePasswordDialog(
    isDialogOpen : MutableState<Boolean>,
    owner: LifecycleOwner
) {
    val dashboardViewModel: DashboardViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()
   // val isPasswordUpdated by dashboardViewModel.isPasswordUpdated.observeAsState(initial = false)
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
                color = Color.LightGray
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
                            text = "Change Password",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = oldPassword,
                            onValueChange = { oldPassword = it },
                            label = { Text(text = "Enter old password") },
                            modifier = Modifier
                                .fillMaxWidth(),
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = newPassword,
                            onValueChange = { newPassword = it },
                            label = { Text(text = "Enter new password") },
                            modifier = Modifier
                                .fillMaxWidth(),
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
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
                                Text(text = "Cancel")
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Button(
                                onClick = {
                                    coroutineScope.launch {
                                        dashboardViewModel.changePassword(ChangePasswordApiUseCase.Params(
                                            oldPassword,newPassword
                                        )).observe(owner) {
                                            if(it is ApiResponse.Success){
                                                isDialogOpen.value = false
                                            }
                                        }
                                    }

                                }
                            ) {
                                Text(text = "Update")
                            }
                        }
                    }
                }
            }
        }
    }
}

