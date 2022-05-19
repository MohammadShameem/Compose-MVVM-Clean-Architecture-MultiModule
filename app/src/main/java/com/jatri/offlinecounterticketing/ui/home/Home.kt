package com.jatri.offlinecounterticketing.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jatri.domain.entity.StoppageEntity
import com.jatri.offlinecounterticketing.R
import com.jatri.offlinecounterticketing.ui.theme.OfflineCounterTicketingTheme
import com.jatri.offlinecounterticketing.ui.theme.colorPrimary


@Composable
fun HomeScreen(
    isStudentFareEnable: Boolean,
    syncClickedCallBack: () -> Unit,
    busCounterClickedCallback: (stoppageEntity: StoppageEntity, studentFare: Boolean) -> Unit
) {
    val viewModel: HomeViewModel = viewModel()
    val stoppageListState by viewModel.stoppageState.collectAsState()
    val unSyncTicketCountState by viewModel.unSyncTicketCountState.collectAsState()
    val unSyncTicketAmountState by viewModel.unSyncTicketAmountState.collectAsState()

    val studentState = remember { mutableStateOf(true) }

    LaunchedEffect(stoppageListState, unSyncTicketCountState, unSyncTicketAmountState, block = {
        viewModel.fetchBusCounterList()
        viewModel.fetchSoldTicketCount()
        viewModel.fetchSoldTicketTotalFare()
    })

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxHeight()
    ) {
        val context = LocalContext.current

        Card(
            modifier = Modifier
            .fillMaxHeight()
            .weight(.8f)
        ) {
            LazyColumn(modifier = Modifier.padding(all = 8.dp)) {
                items(stoppageListState) { busCounter ->
                    BusCounterItem(
                        StoppageEntity(
                            busCounter.id, busCounter.name,
                            busCounter.fare, busCounter.fare_student
                        ), busCounterClickedCallback, studentState
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
        Card(
            elevation = 8.dp, modifier = Modifier
            .fillMaxHeight()
            .weight(.2f)
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                //.height(120.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        if (isStudentFareEnable) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = studentState.value,
                                    onCheckedChange = { studentState.value = it }
                                )
                                Text(
                                    text = "Student Fare",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Text(
                            text = context.getString(
                                R.string.format_total_ticket_count,
                                unSyncTicketCountState
                            ),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = context.getString(
                                R.string.format_total_ticket_fare,
                                unSyncTicketAmountState
                            ),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Button(
                        onClick = { syncClickedCallBack.invoke() },
                        colors = ButtonDefaults.buttonColors(backgroundColor = colorPrimary)
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
    busCounterClickedCallback: (stoppageEntity: StoppageEntity, studentFare: Boolean) -> Unit,
    studentState: MutableState<Boolean>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorPrimary, shape = RoundedCornerShape(15))
            .padding(all = 8.dp)
            .clickable(onClick = {
                busCounterClickedCallback.invoke(stoppageEntity, studentState.value)
            }),
        horizontalArrangement = Arrangement.SpaceBetween,

        ) {

        Text(
            text = stoppageEntity.name,
            color = Color.White,
            fontSize = 22.sp,
            modifier = Modifier.weight(0.9f)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = if (studentState.value) stoppageEntity.fare_student.toString() else stoppageEntity.fare.toString(),
            color = Color.White,
            fontSize = 24.sp,
            modifier = Modifier.weight(0.1f)
        )
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
            // HomeScreen(syncClickedCallBack = { /*TODO*/ }, busCounterClickedCallback = {})

        }
    }
}