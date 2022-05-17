package com.jatri.offlinecounterticketing.ui.configuration

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.RadioButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jatri.entity.companylist.OfflineCompanyListEntity
import com.jatri.offlinecounterticketing.helper.loadJsonFromAsset
import com.jatri.offlinecounterticketing.ui.components.DropDown
import com.jatri.offlinecounterticketing.ui.components.JatriLogo
import com.jatri.offlinecounterticketing.ui.components.RoundJatriButton
import com.jatri.offlinecounterticketing.ui.theme.OfflineCounterTicketingTheme


@Composable
fun Configuration(
    companyListEntity: OfflineCompanyListEntity?
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
                val listItem = listOf(
                    "ABC",
                    "DEF",
                    "GHI",
                    "JKL",
                )
                var company by rememberSaveable {
                    mutableStateOf("Select Company")
                }
                var counter by rememberSaveable {
                    mutableStateOf("Select Counter")
                }
                val companyListItem = companyListEntity?.offline_company_list?.map {
                    it.name
                }
                var isStudentFare by rememberSaveable { mutableStateOf(false) }
                if (companyListItem != null) {
                    DropDown(company, companyListItem) {
                        company = it
                    }
                }
                Spacer(modifier = Modifier.size(24.dp))

                /***
                 * Dropdown Menu of Counters
                 * */
                DropDown(counter, listItem) {
                    counter = it
                }
                Spacer(modifier = Modifier.size(8.dp))

                val context = LocalContext.current



                Text(text = "Student Fare", fontWeight = FontWeight.Bold)

                Row(
                    modifier = Modifier.selectableGroup(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Enabled")
                    RadioButton(
                        selected = isStudentFare,
                        onClick = { isStudentFare = true }
                    )
                    Text(text = "Disabled")
                    RadioButton(
                        selected = !isStudentFare,
                        onClick = { isStudentFare = false }
                    )
                }
            }
        }
        RoundJatriButton("Configure") {

        }
    }
}


@Preview
@Composable
fun CompanyCounterPrev() {
    OfflineCounterTicketingTheme {
        Surface {
            Configuration(null)
        }
    }
}
