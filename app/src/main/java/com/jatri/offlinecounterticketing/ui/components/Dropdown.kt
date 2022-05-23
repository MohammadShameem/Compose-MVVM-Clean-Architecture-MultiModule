package com.jatri.offlinecounterticketing.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.jatri.domain.entity.CounterEntity
import com.jatri.domain.entity.CounterListEntity
import com.jatri.entity.companylist.OfflineCompanyEntity
import com.jatri.offlinecounterticketing.R
import com.jatri.offlinecounterticketing.ui.theme.lightGrey

@Composable
fun DropDown(
    text: String,
    item: List<OfflineCompanyEntity>,
    onItemClick: (OfflineCompanyEntity) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopStart)
    ) {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = lightGrey
            )


        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = text)
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Localized description")
            }
        }
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            item.forEach {
                DropdownMenuItem(
                    onClick = {
                        onItemClick.invoke(it)
                        expanded = false
                    }
                ) {
                    Text(it.name)
                }
            }
            Divider()
        }
    }
}


@Composable
fun DropDownCounterList(
    text: String,
    item: CounterListEntity?,
    ticketCount: Int = -1,
    onItemClick: (CounterEntity) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopStart)
    ) {
        OutlinedButton(
            onClick = {
                if(ticketCount> 0){
                    Toast.makeText(context,context.getString(R.string.msg_print_report),Toast.LENGTH_LONG).show()
                    return@OutlinedButton
                }
                expanded = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = lightGrey
            )

        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = text)
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Localized description")
            }
        }
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            item?.counter_list?.forEach { counterEntity ->
                DropdownMenuItem(
                    onClick = {
                        onItemClick.invoke(counterEntity)
                        expanded = false
                    }
                ) {
                    Text(counterEntity.counter_name)
                }
            }
            Divider()
        }
    }
}

@Composable
fun JatriDropDown(
    modifier: Modifier = Modifier,
    text: String,
    items: List<String>,
    onItemClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopStart)
    ) {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = lightGrey
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = text)
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Localized description")
            }
        }
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach {
                DropdownMenuItem(
                    onClick = {
                        onItemClick.invoke()
                        expanded = false
                    }
                ) {
                    Text(it)
                }
            }
            Divider()
        }
    }
}