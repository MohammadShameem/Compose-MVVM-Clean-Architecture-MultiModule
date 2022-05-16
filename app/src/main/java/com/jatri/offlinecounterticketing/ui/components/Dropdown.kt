package com.jatri.offlinecounterticketing.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DropDown(text: String, item: List<String>, onItemClick: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopStart)
    ) {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier
                .fillMaxWidth().height(48.dp),

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
            item.forEach { itemText ->
                DropdownMenuItem(
                    onClick = {
                        onItemClick.invoke(itemText)
                        expanded = false
                    }
                ) {
                    Text(itemText)
                }
            }
            Divider()
        }
    }
}