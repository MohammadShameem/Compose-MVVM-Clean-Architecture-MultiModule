package com.jatri.offlinecounterticketing.ui.components
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jatri.offlinecounterticketing.R

@Composable
fun BackPressAlertDialog(
    titleText: String,
    messageText: String,
    isAlertDialogOpen: MutableState<Boolean>,
    navigateTo: () -> Unit
) {
    if(isAlertDialogOpen.value){
        AlertDialog(
            onDismissRequest = {
                isAlertDialogOpen.value = false
            },
            confirmButton = {
                TextButton(onClick = {
                    navigateTo.invoke()
                    isAlertDialogOpen.value = false
                }) {
                    Text(text = stringResource(id = R.string.btn_text_conform), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    isAlertDialogOpen.value = false
                }) {
                    Text(text = stringResource(id = R.string.btn_text_cancel),fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            },
            title = {
                Text(text = titleText,fontSize = 20.sp, fontWeight = FontWeight.Bold)
            },
            text = {
                Text(text = messageText, fontSize = 16.sp,color = Color.Black)
            }
        )
    }

}

