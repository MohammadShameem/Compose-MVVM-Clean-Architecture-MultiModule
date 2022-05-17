package com.jatri.offlinecounterticketing.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jatri.domain.entity.BusCounterEntity
import com.jatri.offlinecounterticketing.ui.configuration.Configuration
import com.jatri.offlinecounterticketing.ui.theme.OfflineCounterTicketingTheme


@Composable
fun HomeScreen(){
    //Home page of offline counter
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxHeight()
    ) {
        Card (modifier = Modifier.weight(1f)){
            CounterListShow()
        }
        Card(elevation = 8.dp, modifier = Modifier.weight(.11f)) {
            Column(modifier = Modifier
                .padding(8.dp)
                .height(100.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Total Ticket: 0", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Total Ticket: 0", fontSize = 16.sp,fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = { /*TODO*/ },
                        colors= ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                    ) {
                        Text(text = "Sync",color= Color.White)
                    }

                }

            }
        }

    }
}


@Composable
fun CounterList(messages: List<BusCounterEntity>) {
    LazyColumn() {
        items(messages) { message ->
            BusCounterItem(BusCounterEntity(message.id, message.name,message.fare,message.fare_student))
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun BusCounterItem(msg: BusCounterEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Red, shape = RoundedCornerShape(15))
            .padding(all = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(text = msg.name, color = Color.White, fontSize = 22.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = msg.fare.toString(), color = Color.White, fontSize = 24.sp)
    }
}

@Composable
fun CounterListShow() {
    Column(
        modifier = Modifier.padding(all = 8.dp)

    ) {
        val list = mutableListOf<BusCounterEntity>()
        list.add(BusCounterEntity(0,"Rxjava", 3,2))
        list.add(BusCounterEntity(0,"Rxjava", 3,2))
        CounterList(messages = list)
    }
}

@Preview
@Composable
fun CompanyCounterPrev() {
    OfflineCounterTicketingTheme {
        Surface {
            HomeScreen()
        }
    }
}