package com.jatri.offlinecounterticketing.ui.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jatri.offlinecounterticketing.R
import com.jatri.offlinecounterticketing.ui.components.RoundJatriButton
import com.jatri.offlinecounterticketing.ui.theme.*

@Composable
fun Dashboard() {
    Column {
        UserInfo()
        TicketCountAndFare()
        ChangeCounter()
        RoundJatriButton(text = "ReportPrint") {
        }

    }

}


@Composable
fun UserInfo() {
    Row {
        Image(painter = painterResource(id = R.drawable.ic_app_logo), contentDescription = "Profile Picture")
        Column {
            Text(text = "Kamrul Hasan")
            Text(text = "+880xxxxxxxx")
            RoundJatriButton(text = "Change Password") {}
        }
    }
}


@Composable
fun ChangeCounter() {
    Card {
        Column {
            Text(text = "Change Counter")
        }
    }
}

@Composable
fun TicketCountAndFare() {
    Row {
        TicketCount()
        TicketCount()
    }
    
}

@Composable
fun TicketCount() {
    Column (horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = "Total Ticket Count")
        Text(text = "100")
    }
}

@Composable
fun TicketFare() {
    Column (horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = "Total Ticket Count")
        Text(text = "100")
    }
}



@Preview
@Composable
fun DeshPrev() {
    OfflineCounterTicketingTheme {
        //Dashboard()
        DashboardCard {
            TicketCountAndFare()
        }
    }
}

@Composable
fun DashboardCard(
    content: @Composable () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20) ,
        backgroundColor = lightGrey

    ) {
        content.invoke()
    }
}