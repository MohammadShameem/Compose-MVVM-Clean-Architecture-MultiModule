package com.jatri.offlinecounterticketing.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jatri.entity.companylist.OfflineCompanyEntity
import com.jatri.entity.companylist.OfflineCompanyListEntity
import com.jatri.entity.counterlist.CounterEntity
import com.jatri.entity.counterlist.CounterListEntity

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
            item?.counter_list?.forEach {
                DropdownMenuItem(
                    onClick = {
                        //onItemClick.invoke(it.counter_name)
                        expanded = false
                    }
                ) {
                    Text(it.counter_name)
                }
            }
            Divider()
        }
    }
}