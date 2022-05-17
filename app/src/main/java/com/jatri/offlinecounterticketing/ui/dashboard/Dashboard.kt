package com.jatri.offlinecounterticketing.ui.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jatri.offlinecounterticketing.R
import com.jatri.offlinecounterticketing.ui.components.JatriDropDown
import com.jatri.offlinecounterticketing.ui.components.JatriRoundOutlinedButton
import com.jatri.offlinecounterticketing.ui.components.RoundJatriButton
import com.jatri.offlinecounterticketing.ui.theme.*

@Composable
fun Dashboard() {
    Column(modifier = Modifier.fillMaxSize()) {
        UserInfo()
        TicketCountAndFare()
        ChangeCounter()
        RoundJatriButton(text = "ReportPrint", backgroundColor = lightGrey) {
        }


    }

}


@Composable
fun UserInfo() {
    DashboardCard {
        Row (modifier = Modifier.padding(16.dp)){

            Column {
                Text(text = "Kamrul Hasan")
                Text(text = "+880xxxxxxxx")
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
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Change Counter")
            JatriDropDown(text = "Select Counter", items = listOf("a","b","c")) {
            }
        }
    }
}

@Composable
fun TicketCountAndFare() {
    Row(
        modifier = Modifier.fillMaxWidth(),
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


@Preview
@Composable
fun DeshPrev() {
    OfflineCounterTicketingTheme {
        androidx.compose.material.Surface {

            Dashboard()
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