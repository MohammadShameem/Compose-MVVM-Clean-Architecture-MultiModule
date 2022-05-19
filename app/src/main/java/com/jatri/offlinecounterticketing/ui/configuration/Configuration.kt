package com.jatri.offlinecounterticketing.ui.configuration

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jatri.domain.entity.CounterEntity
import com.jatri.domain.entity.CounterListEntity
import com.jatri.entity.companylist.OfflineCompanyEntity
import com.jatri.offlinecounterticketing.helper.loadJsonFromAsset
import com.jatri.offlinecounterticketing.ui.components.DropDown
import com.jatri.offlinecounterticketing.ui.components.DropDownCounterList
import com.jatri.offlinecounterticketing.ui.components.JatriLogo
import com.jatri.offlinecounterticketing.ui.components.RoundJatriButton
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
        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current

        /**
         * Initial Titles of the Dropdown menus
         * */
        var companyDropDownTitle by remember { mutableStateOf("Select Company") }
        var counterDropDownTitle by remember { mutableStateOf("Select Counter") }

        var selectedCompanyEntity: OfflineCompanyEntity? by remember { mutableStateOf(null) }
        var counterListEntity: CounterListEntity? by remember { mutableStateOf(null) }
        var selectedCounterEntity by remember { mutableStateOf<CounterEntity?>(null) }
        var isStudentFareSelected by remember { mutableStateOf(false) }

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
                /**
                 * Company drop down List
                 * */
                DropDown(companyDropDownTitle, companyList) { offlineCompanyEntity ->
                    companyDropDownTitle = offlineCompanyEntity.name
                    selectedCompanyEntity = offlineCompanyEntity
                    coroutineScope.launch {
                        context.loadJsonFromAsset(offlineCompanyEntity.counter_file_name)
                            .onSuccess {
                                counterListEntity = viewModel.getCounterListFromJson(it)
                                /**
                                 * Set the first counter name on the Counter Dropdown Menu
                                 * */
                                counterDropDownTitle =
                                    counterListEntity?.counter_list?.get(0)?.counter_name
                                        ?: "No Counters"
                                /**
                                 * Set the first counterEntity as selected counter                                 * */
                                selectedCounterEntity = counterListEntity?.counter_list?.get(0)
                            }
                    }
                }
                Spacer(modifier = Modifier.size(24.dp))
                /***
                 * Dropdown Menu of Counters
                 * */
                DropDownCounterList(counterDropDownTitle, counterListEntity) {
                    counterDropDownTitle = it.counter_name //Set the dropdown title
                    selectedCounterEntity = it //Set selected counter state
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
            selectedCompanyEntity?.let {
                coroutineScope.launch {
                    selectedCounterEntity?.let { counterEntity ->
                        viewModel.saveCompanyInfoToSharedPreference(
                            selectedCompanyEntity!!, isStudentFareSelected, counterEntity
                        )
                        onConfigureClick.invoke()
                    }
                }
            }
        }
    }
}

