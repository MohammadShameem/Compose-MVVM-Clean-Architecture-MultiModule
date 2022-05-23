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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jatri.entity.cachentity.StoppageEntity
import com.jatri.offlinecounterticketing.R
import com.jatri.offlinecounterticketing.ui.theme.colorPrimary
import com.jatri.offlinecounterticketing.ui.theme.lightGrey

/**
 * Home Screen Stoppage list show
 * @param isConfigStudentFareEnable fare enable from config page
 * @param syncClickedCallBack sync button click callback
 * @param busCounterClickedCallback stoppage item click listener from stoppage list items
 * */
@Composable
fun HomeScreen(
    isConfigStudentFareEnable: Boolean,
    syncClickedCallBack: () -> Unit,
    busCounterClickedCallback: (stoppageEntity: StoppageEntity, studentFare: Boolean) -> Unit
) {
    val viewModel: HomeViewModel = viewModel()
    val stoppageListState by viewModel.stoppageState.collectAsState()
    val unSyncTicketCountState by viewModel.unSyncTicketCountState.collectAsState()
    val unSyncTicketAmountState by viewModel.unSyncTicketAmountState.collectAsState()

    /**
     * If Student Fare is enable from config then It will default
     * set student fare enable in checkbox in view
     * */
    val studentCheckboxState = remember { mutableStateOf(true) }

    LaunchedEffect(stoppageListState, unSyncTicketCountState, unSyncTicketAmountState, block = {
        viewModel.fetchBusCounterList()
        viewModel.fetchSoldTicketCount()
        viewModel.fetchSoldTicketTotalFare()
    })
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxHeight()
    ) {

        Card(
            modifier = Modifier
                .fillMaxHeight()
                .weight(if (isConfigStudentFareEnable) .8f else .9f)
        ) {
            LazyColumn(modifier = Modifier.padding(all = 8.dp)) {
                items(stoppageListState) { busCounter ->
                    BusCounterItem(
                        isConfigStudentFareEnable,
                        StoppageEntity(
                            busCounter.id, busCounter.name,
                            busCounter.fare, busCounter.fare_student
                        ), busCounterClickedCallback, studentCheckboxState
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        Card(
            elevation = 8.dp, modifier = Modifier
                .fillMaxHeight()
                .weight(if (isConfigStudentFareEnable) .2f else .1f)
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
                        if (isConfigStudentFareEnable) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = studentCheckboxState.value,
                                    onCheckedChange = { studentCheckboxState.value = it }
                                )
                                Text(
                                    text = stringResource(R.string.checkbox_message),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Text(
                            text = stringResource(
                                R.string.format_total_ticket_count,
                                unSyncTicketCountState
                            ),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = stringResource(
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
                        Text(text = stringResource(R.string.btn_syn), color = Color.White)
                    }

                }

            }
        }

    }
}

/**
 * Stoppage Item show in home page
 * @param isStudentFareEnable config student fare
 * @param stoppageEntity stoppage entity
 * @param busCounterClickedCallback list item click
 * @param studentCheckBoxState student fare State
 * */
@Composable
fun BusCounterItem(
    isStudentFareEnable: Boolean,
    stoppageEntity: StoppageEntity,
    busCounterClickedCallback: (stoppageEntity: StoppageEntity, studentFare: Boolean) -> Unit,
    studentCheckBoxState: MutableState<Boolean>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = lightGrey, shape = RoundedCornerShape(15))
            .padding(start = 8.dp, top = 8.dp, bottom = 8.dp, end = 16.dp)
            .clickable(onClick = {
                busCounterClickedCallback.invoke(stoppageEntity, studentCheckBoxState.value)
            }),
        horizontalArrangement = Arrangement.SpaceBetween,

        ) {

        Text(
            text = stoppageEntity.name,
            color = Color.Black,
            fontSize = 22.sp,
            modifier = Modifier.weight(0.9f)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = if (isStudentFareEnable&&studentCheckBoxState.value) stoppageEntity.fare_student.toString() else stoppageEntity.fare.toString(),
            color = Color.Black,
            fontSize = 24.sp,
            modifier = Modifier.weight(0.1f)
        )
    }
}
