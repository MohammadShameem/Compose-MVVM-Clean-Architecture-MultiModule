package com.jatri.offlinecounterticketing.ui.dashboard
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jatri.offlinecounterticketing.ui.components.JatriDropDown
import com.jatri.offlinecounterticketing.ui.components.JatriRoundOutlinedButton
import com.jatri.offlinecounterticketing.ui.components.RoundJatriButton
import com.jatri.offlinecounterticketing.ui.theme.*

@Composable
fun Dashboard(
    username: String,
    phoneNumber: String
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        UserInfo(username,phoneNumber)
        TicketCountAndFare()
        ChangeCounter()
        RoundJatriButton(
            text = "ReportPrint",
            backgroundColor = Color.Gray) {
        }
    }
}


@Composable
fun UserInfo(
    username: String,
    phoneNumber: String
) {
    DashboardCard {
        Row (modifier = Modifier.padding(18.dp)){
            Column {
                val name by remember { mutableStateOf(username) }
                val number by remember { mutableStateOf(phoneNumber) }
                Text(text = name)
                Text(text = number)
                JatriRoundOutlinedButton(
                    borderColor = darkGrey,
                    backgroundColor = lightGrey,
                    text = "Change Password"
                ) {

                }
            }
        }
    }
}


@Composable
fun ChangeCounter() {
    DashboardCard {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(text = "Change Counter")
            JatriDropDown(text = "Select Counter", items = listOf("a","b","c")) {
            }
        }
    }
}

@Composable
fun TicketCountAndFare() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TicketCount()
        TicketFare()
    }

}

@Composable
fun TicketCount() {
    DashboardCard {
        Column(
            modifier = Modifier.padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Total Ticket Count")
            Text(text = "100")
        }
    }
}

@Composable
fun TicketFare() {
    DashboardCard {
        Column(
            modifier = Modifier.padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Total Ticket Fare")
            Text(text = "100")
        }
    }
}

@Composable
fun DashboardCard(
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.padding(all = 5.dp),
        shape = RoundedCornerShape(10),
        backgroundColor = lightGrey
    ) {
        content.invoke()
    }
}
