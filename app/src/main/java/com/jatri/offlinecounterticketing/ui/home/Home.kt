package com.jatri.offlinecounterticketing.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.jatri.domain.entity.StoppageEntity
import com.jatri.offlinecounterticketing.ui.theme.OfflineCounterTicketingTheme


@Composable
fun HomeScreen(
    counterList: List<StoppageEntity>,
    syncClickedCallBack: () -> Unit,
    busCounterClickedCallback: (stoppageEntity: StoppageEntity) -> Unit
) {
    //Home page of offline counter
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxHeight()
    ) {
        Card(modifier = Modifier.weight(1f)) {
            Column(
                modifier = Modifier.padding(all = 8.dp)) {

                LazyColumn{
                    items(counterList) { busCounter ->
                        BusCounterItem(
                            StoppageEntity(
                                busCounter.id, busCounter.name,
                                busCounter.fare, busCounter.fare_student
                            ), busCounterClickedCallback
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
        Card(elevation = 8.dp, modifier = Modifier.weight(.11f)) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .height(100.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Total Ticket: 0",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Total Ticket: 0",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Button(
                        onClick = { syncClickedCallBack.invoke() },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                    ) {
                        Text(text = "Sync", color = Color.White)
                    }

                }

            }
        }

    }
}


@Composable
fun BusCounterItem(
    stoppageEntity: StoppageEntity,
    busCounterClickedCallback: (stoppageEntity: StoppageEntity) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Red, shape = RoundedCornerShape(15))
            .padding(all = 8.dp)
            .clickable(onClick = {
                busCounterClickedCallback.invoke(stoppageEntity)
            }),
        horizontalArrangement = Arrangement.SpaceBetween,

        ) {

        Text(text = stoppageEntity.name, color = Color.White, fontSize = 22.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = stoppageEntity.fare.toString(), color = Color.White, fontSize = 24.sp)
    }
}


@Preview
@Composable
fun CompanyCounterPrev() {
    OfflineCounterTicketingTheme {
        Surface {
            val list = mutableListOf<StoppageEntity>()
            list.add(StoppageEntity(0, "Rxjava", 3, 2))
            list.add(StoppageEntity(0, "Rxjava", 3, 2))

        }
    }
}