package com.jatri.offlinecounterticketing.ui.configuration

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.RadioButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jatri.entity.companylist.OfflineCompanyEntity
import com.jatri.entity.counterlist.CounterListEntity
import com.jatri.offlinecounterticketing.helper.loadJsonFromAsset
import com.jatri.offlinecounterticketing.ui.components.DropDown
import com.jatri.offlinecounterticketing.ui.components.DropDownCounterList
import com.jatri.offlinecounterticketing.ui.components.JatriLogo
import com.jatri.offlinecounterticketing.ui.components.RoundJatriButton
import com.jatri.offlinecounterticketing.ui.theme.OfflineCounterTicketingTheme
import kotlinx.coroutines.launch


@Composable
fun Configuration(
    companyList: List<OfflineCompanyEntity>
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        JatriLogo()
        Text(text = "Please Setup Configuration", fontWeight = FontWeight.Bold)

        Box {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
            ) {

                var company by rememberSaveable {
                    mutableStateOf("Select Company")
                }
                val counter by rememberSaveable {
                    mutableStateOf("Select Counter")
                }
                var counterList : CounterListEntity? by rememberSaveable {
                    mutableStateOf(null)
                }



                val coroutineScope = rememberCoroutineScope()
                val context = LocalContext.current
                val viewModel: ConfigurationViewModel = viewModel()

                var isStudentFare by rememberSaveable { mutableStateOf(false) }

                //company drop down
                DropDown(company, companyList) { offlineCompanyEntity ->
                    company = offlineCompanyEntity.name
                    coroutineScope.launch {
                        context.loadJsonFromAsset(offlineCompanyEntity.counter_file_name).onSuccess {
                             counterList = viewModel.getCounterListFromJson(it)


                        }

                    }
                }

                Spacer(modifier = Modifier.size(24.dp))

                /***
                 * Dropdown Menu of Counters
                 * */
                DropDownCounterList(counter, counterList) {
                    //counter = it.counter_file_name
                }


                Spacer(modifier = Modifier.size(8.dp))
                Text(text = "Student Fare", fontWeight = FontWeight.Bold)
                Row(
                    modifier = Modifier.selectableGroup(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = isStudentFare,
                        onClick = { isStudentFare = true }
                    )
                    Text(text = "Enabled")
                    RadioButton(
                        selected = !isStudentFare,
                        onClick = { isStudentFare = false }
                    )
                    Text(text = "Disabled")
                }
            }
        }
        RoundJatriButton("Configure") {}
    }
}


@Preview
@Composable
fun CompanyCounterPrev() {
    OfflineCounterTicketingTheme {
        Surface {
            //Configuration()
        }
    }
}
