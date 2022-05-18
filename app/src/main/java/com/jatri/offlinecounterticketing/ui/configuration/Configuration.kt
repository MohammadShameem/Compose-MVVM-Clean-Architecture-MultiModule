package com.jatri.offlinecounterticketing.ui.configuration

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.RadioButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jatri.domain.entity.CounterListEntity
import com.jatri.domain.entity.StoppageEntity
import com.jatri.entity.companylist.OfflineCompanyEntity
import com.jatri.offlinecounterticketing.helper.loadJsonFromAsset
import com.jatri.offlinecounterticketing.ui.components.DropDown
import com.jatri.offlinecounterticketing.ui.components.DropDownCounterList
import com.jatri.offlinecounterticketing.ui.components.JatriLogo
import com.jatri.offlinecounterticketing.ui.components.RoundJatriButton
import com.jatri.offlinecounterticketing.ui.theme.OfflineCounterTicketingTheme
import kotlinx.coroutines.launch


@Composable
fun Configuration(
    companyList: List<OfflineCompanyEntity>,
    onConfigureClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val viewModel: ConfigurationViewModel = viewModel()

        var companyEntity: OfflineCompanyEntity? by remember { mutableStateOf(null) }
        var isStudentFareSelected by remember { mutableStateOf(false) }

        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current
        var stoppageEntityList: List<StoppageEntity> = listOf()


        JatriLogo()
        Text(
            text = "Please Setup Configuration",
            fontWeight = FontWeight.Bold
        )
        Box {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                var companyDropDownTitle by remember { mutableStateOf("Select Company") }
                var counterDropDownTitle by remember { mutableStateOf("Select Counter") }
                var counterList: CounterListEntity? by remember { mutableStateOf(null) }


                //company drop down
                DropDown(companyDropDownTitle, companyList) { offlineCompanyEntity ->
                    companyDropDownTitle = offlineCompanyEntity.name
                    companyEntity = offlineCompanyEntity
                    coroutineScope.launch {
                        context.loadJsonFromAsset(offlineCompanyEntity.counter_file_name)
                            .onSuccess {
                                counterList = viewModel.getCounterListFromJson(it)
                                counterDropDownTitle =
                                    counterList?.counter_list?.get(0)?.counter_name ?: "No Counters"
                            }
                    }
                }

                Spacer(modifier = Modifier.size(24.dp))

                /***
                 * Dropdown Menu of Counters
                 * */
                DropDownCounterList(counterDropDownTitle, counterList) {
                    counterDropDownTitle = it.counter_name
                    stoppageEntityList = it.stoppage_list
                }

                Spacer(modifier = Modifier.size(8.dp))
                Text(text = "Student Fare", fontWeight = FontWeight.Bold)
                Row(
                    modifier = Modifier.selectableGroup(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = isStudentFareSelected,
                        onClick = { isStudentFareSelected = true }
                    )
                    Text(text = "Enabled")
                    RadioButton(
                        selected = !isStudentFareSelected,
                        onClick = { isStudentFareSelected = false }
                    )
                    Text(text = "Disabled")
                }
            }
        }

        RoundJatriButton("Configure") {
            companyEntity?.let {
                coroutineScope.launch {
                    viewModel.saveCompanyInfoToSharedPreference(
                        companyEntity!!, isStudentFareSelected, stoppageEntityList
                    )
                }
                onConfigureClick.invoke()
            }
        }
    }
}


/*@Preview
@Composable
fun CompanyCounterPrev() {
    OfflineCounterTicketingTheme {
        Surface {
            Configuration(emptyList())
        }
    }
}*/
