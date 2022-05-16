package com.jatri.offlinecounterticketing.ui.components

import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.jatri.offlinecounterticketing.ui.theme.OfflineCounterTicketingTheme

@Composable
fun OfflineCounterTicketingScaffold(
    content: @Composable () -> Unit
) {

    OfflineCounterTicketingTheme {
        Surface {
            Scaffold(topBar = {
                ToolbarWithBackButton(title = "") {

                }
            }) {
                content
            }
        }
    }

}