package com.example.shameem.ui.configuration

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.entity.companylist.OfflineCompanyEntity
import com.example.entity.stoppage.CounterEntity
import com.example.entity.stoppage.CounterListEntity
import com.example.shameem.R
import com.example.shameem.helper.loadJsonFromAsset
import com.example.shameem.ui.components.DropDown
import com.example.shameem.ui.components.DropDownCounterList
import com.example.shameem.ui.components.JatriLogo
import com.example.shameem.ui.components.RoundJatriButton
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
            text = stringResource(id = R.string.please_setup_configuration),
            fontWeight = FontWeight.Bold
        )
        Box {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                //company drop down
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
                Text(text = stringResource(R.string.student_fare), fontWeight = FontWeight.Bold)
                Row(
                    modifier = Modifier.selectableGroup(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = isStudentFareSelected,
                        onClick = { isStudentFareSelected = true }
                    )
                    Text(text = stringResource(R.string.checkBox_enabled))
                    RadioButton(
                        selected = !isStudentFareSelected,
                        onClick = { isStudentFareSelected = false }
                    )
                    Text(text = stringResource(R.string.checkBox_disabled))
                }
            }
        }

        RoundJatriButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(start = 16.dp, end = 16.dp),
            text = stringResource(R.string.btn_configure)) {
            selectedCompanyEntity?.let {
                coroutineScope.launch {
                    selectedCounterEntity?.let { counterEntity ->
                        viewModel.clearCache()
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

