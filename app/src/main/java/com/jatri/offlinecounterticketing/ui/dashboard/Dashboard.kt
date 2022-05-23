package com.jatri.offlinecounterticketing.ui.dashboard

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jatri.entity.res.ApiResponse
import com.jatri.entity.stoppage.CounterListEntity
import com.jatri.offlinecounterticketing.R
import com.jatri.offlinecounterticketing.helper.loadJsonFromAsset
import com.jatri.offlinecounterticketing.ui.components.CircularProgressBar
import com.jatri.offlinecounterticketing.ui.components.DropDownCounterList
import com.jatri.offlinecounterticketing.ui.components.JatriRoundOutlinedButton
import com.jatri.offlinecounterticketing.ui.components.RoundJatriButton
import com.jatri.offlinecounterticketing.ui.theme.darkGrey
import com.jatri.offlinecounterticketing.ui.theme.lightGrey
import kotlinx.coroutines.launch

@Composable
fun Dashboard(
    username: String,
    phoneNumber: String
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val viewModel : DashboardViewModel = viewModel()
    val soldTicketListState by viewModel.soldTicketListState.collectAsState()
    var isProgressBarLoading by remember { mutableStateOf(false) }

    val unSyncTicketCountState by viewModel.unSyncTicketCountState.collectAsState()
    val unSyncTicketAmountState by viewModel.unSyncTicketAmountState.collectAsState()

    LaunchedEffect(soldTicketListState,unSyncTicketCountState, unSyncTicketAmountState, block = {
        viewModel.fetchAllSoldTicketGroupWiseDataList()
        viewModel.fetchSoldTicketCount()
        viewModel.fetchSoldTicketTotalFare()
    })
    Column(modifier = Modifier.fillMaxSize()) {
        UserInfo(username, phoneNumber)
        Spacer(modifier = Modifier.size(8.dp))
        TicketCountAndFare(unSyncTicketCountState, unSyncTicketAmountState)
        Spacer(modifier = Modifier.size(8.dp))
        ChangeCounter(viewModel, context)
        Spacer(modifier = Modifier.size(16.dp))
        RoundJatriButton(text = stringResource(R.string.btn_text_report_print), backgroundColor = lightGrey) {
                coroutineScope.launch {
                    if(unSyncTicketCountState > 0) isProgressBarLoading = true
                    val soldTicketBody = viewModel.getSoldTicketBodyToSync(soldTicketListState)
                    if(unSyncTicketCountState > 0){
                        viewModel.syncSoldTicket(soldTicketBody)
                            .observe(lifecycleOwner) {
                                isProgressBarLoading = when(it){
                                    is ApiResponse.Success -> {
                                        Toast.makeText(context,it.data.message,Toast.LENGTH_LONG).show()
                                        viewModel.printReport(unSyncTicketCountState,unSyncTicketAmountState)
                                        false
                                    }
                                    is ApiResponse.Failure ->{
                                        Toast.makeText(context, it.message,Toast.LENGTH_LONG).show()
                                        false
                                    }
                                    else -> {
                                        false
                                    }
                                }
                            }
                    }else  Toast.makeText(context,context.getString(R.string.msg_noting_to_print),Toast.LENGTH_LONG).show()

                }
            }

        }
        Spacer(modifier = Modifier.height(15.dp))
        CircularProgressBar(isDisplayed = isProgressBarLoading)
    }

@Composable
fun UserInfo(
    username: String,
    phoneNumber: String
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
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Column {
                    val name by remember { mutableStateOf(username) }
                    val number by remember { mutableStateOf(phoneNumber) }
                    Text(text = name)
                    Text(text = number)
                    ChangePasswordDialog(isDialogOpen)
                    JatriRoundOutlinedButton(
                        borderColor = darkGrey,
                        backgroundColor = lightGrey,
                        text = stringResource(R.string.btn_text_change_password)
                    ) {
                        isDialogOpen.value = true
                    }
                }
            }
        }
    }
}

@Composable
fun ChangeCounter(
    viewModel: DashboardViewModel,
    context: Context
) {
    DashboardCard {

        var counterList: CounterListEntity? by remember { mutableStateOf(null) }

        var counterDropDownTitle by remember {
            mutableStateOf(
                viewModel.getCurrentCounterName()
            )
        }
        LaunchedEffect(counterList) {
            context.loadJsonFromAsset(viewModel.getCounterFileName())
                .onSuccess {
                    counterList = viewModel.getCounterListFromJson(it)
                }
        }
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = stringResource(R.string.change_counter))
            Spacer(modifier = Modifier.size(8.dp))
            DropDownCounterList(counterDropDownTitle, counterList) { counterEntity ->
                counterDropDownTitle = counterEntity.counter_name
                viewModel.updateStoppageList(counterEntity)
            }
        }
    }
}

@Composable
fun TicketCountAndFare(unSyncTicketCountState: Int, unSyncTicketAmountState: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TicketCount(
            modifier = Modifier
                .weight(1.0f)
                .fillMaxWidth(),
            unSyncTicketCountState
        )
        TicketFare(
            modifier = Modifier
                .weight(1.0f)
                .fillMaxWidth(),
            unSyncTicketAmountState
        )
    }
}

@Composable
fun TicketCount(modifier: Modifier = Modifier, unSyncTicketCountState: Int) {
    DashboardCard(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.total_ticket_count))
            Text(text = "$unSyncTicketCountState")
        }
    }
}

@Composable
fun TicketFare(modifier: Modifier = Modifier, unSyncTicketAmountState: Int) {
    DashboardCard(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.total_ticket_fare))
            Text(text = "$unSyncTicketAmountState")
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
        backgroundColor = lightGrey,
        elevation = 0.dp,
    ) {
        content.invoke()
    }
}
