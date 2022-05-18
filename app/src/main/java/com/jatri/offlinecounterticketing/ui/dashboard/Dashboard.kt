package com.jatri.offlinecounterticketing.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jatri.offlinecounterticketing.ui.components.JatriDropDown
import com.jatri.offlinecounterticketing.ui.components.JatriRoundOutlinedButton
import com.jatri.offlinecounterticketing.ui.components.RoundJatriButton
import com.jatri.offlinecounterticketing.ui.theme.OfflineCounterTicketingTheme
import com.jatri.offlinecounterticketing.ui.theme.darkGrey
import com.jatri.offlinecounterticketing.ui.theme.lightGrey

@Composable
fun Dashboard(
    username: String,
    phoneNumber: String,
    changePasswordCallBack: (String,String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        UserInfo(username, phoneNumber, changePasswordCallBack)
        Spacer(modifier = Modifier.size(8.dp))
        TicketCountAndFare()
        Spacer(modifier = Modifier.size(8.dp))
        ChangeCounter()
        Spacer(modifier = Modifier.size(16.dp))
        RoundJatriButton(text = "ReportPrint", backgroundColor = lightGrey) {
        }
    }
}

@Composable
fun UserInfo(
    username: String,
    phoneNumber: String,
    changePasswordCallBack: (String,String) -> Unit
) {
    val isDialogOpen = remember { mutableStateOf(false) }
    DashboardCard {
        Column {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "",
                    modifier = Modifier.size(48.dp))
                Spacer(modifier = Modifier.size(16.dp))
                Column {
                    val name by remember { mutableStateOf(username) }
                    val number by remember { mutableStateOf(phoneNumber) }
                    Text(text = name)
                    Text(text = number)
                    ChangePasswordDialog(isDialogOpen, changePasswordCallBack)
                    JatriRoundOutlinedButton(
                        borderColor = darkGrey,
                        backgroundColor = lightGrey,
                        text = "Change Password"
                    ) {
                        isDialogOpen.value = true
                    }
                }
            }
        }
    }
}

@Composable
fun ChangeCounter() {
    DashboardCard {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Change Counter")
            Spacer(modifier = Modifier.size(8.dp))
            JatriDropDown(text = "Select Counter", items = listOf("a", "b", "c")) {
            }
        }
    }
}

@Composable
fun TicketCountAndFare() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TicketCount(
            modifier = Modifier
                .weight(1.0f)
                .fillMaxWidth()
        )
        TicketFare(
            modifier = Modifier
                .weight(1.0f)
                .fillMaxWidth()
        )
    }

}

@Composable
fun TicketCount(modifier: Modifier = Modifier) {
    DashboardCard(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Total Ticket Count")
            Text(text = "100")
        }
    }
}

@Composable
fun TicketFare(modifier: Modifier = Modifier) {
    DashboardCard(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Total Ticket Fare")
            Text(text = "100")
        }
    }
}

@Composable
fun DashboardCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier.padding(all = 8.dp),
        shape = RoundedCornerShape(10),
        backgroundColor = lightGrey
    ) {
        content.invoke()
    }
}
