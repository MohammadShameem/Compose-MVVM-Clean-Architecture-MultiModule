package com.example.shameem.ui.dashboard

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.example.cache.CacheRepository
import com.example.common.constant.AppConstant
import com.example.common.dateparser.DateTimeFormat
import com.example.common.dateparser.DateTimeParser
import com.example.domain.usecase.dashboard.ChangePasswordApiUseCase
import com.example.domain.usecase.dashboard.SyncedSoldTicketApiUseCase
import com.example.entity.dashboard.ChangePasswordApiEntity
import com.example.entity.dashboard.SyncSoldTicketBody
import com.example.entity.dashboard.SyncSoldTicketBodyCollection
import com.example.entity.dashboard.SyncedSoldTicketApiEntity
import com.example.entity.res.ApiResponse
import com.example.entity.cachentity.SoldTicketGroupWiseEntity
import com.example.entity.stoppage.CounterEntity
import com.example.entity.stoppage.CounterListEntity
import com.example.shameem.R
import com.example.shameem.helper.BanglaConverterUtil
import com.example.shameem.printer.SunmiPrintHelper
import com.example.sharedpref.SharedPrefHelper
import com.example.sharedpref.SpKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val gson: Gson,
    private val changePasswordApiUseCase: ChangePasswordApiUseCase,
    private val syncedSoldTicketApiUseCase: SyncedSoldTicketApiUseCase,
    private val sharedPrefHelper: SharedPrefHelper,
    private val cacheRepository: CacheRepository,
    private val application: Application,
) : ViewModel() {
    private val _unSyncTicketCountState = MutableStateFlow(0)
    val unSyncTicketCountState : StateFlow<Int> = _unSyncTicketCountState

    private val _unSyncTicketAmountState = MutableStateFlow(0)
    val unSyncTicketAmountState : StateFlow<Int> = _unSyncTicketAmountState

    private val _soldTicketListState = MutableStateFlow<List<SoldTicketGroupWiseEntity>>(listOf())
    var soldTicketListState : StateFlow<List<SoldTicketGroupWiseEntity>> = _soldTicketListState

    fun changePassword(
        params: ChangePasswordApiUseCase.Params
    ): LiveData<ApiResponse<ChangePasswordApiEntity>> = changePasswordApiUseCase.execute(params)

    fun syncSoldTicket(
        soldTicketBody: SyncSoldTicketBody
    ): LiveData<ApiResponse<SyncedSoldTicketApiEntity>> =
        syncedSoldTicketApiUseCase.execute(soldTicketBody)

    fun validateOldPasswordAndNewPassword(oldPassword: String, newPassword: String): Int {
        return if (oldPassword.isEmpty()) R.string.error_msg_enter_oldPassword
        else if (newPassword.isEmpty()) R.string.error_msg_enter_newPassword
        else if (newPassword.length < 6) R.string.error_msg_6_character_required
        else AppConstant.validation_successful
    }

    fun fetchAllSoldTicketGroupWiseDataList(){
        viewModelScope.launch {
            cacheRepository.fetchSoldTicketGroupWiseDataFlow()
                .collect {
                   _soldTicketListState.value = it
                }
        }
    }


    fun getSoldTicketBodyToSync(soldTicketGroupWiseDataList: List<SoldTicketGroupWiseEntity>): SyncSoldTicketBody{
       return SyncSoldTicketBody(
            device_id = sharedPrefHelper.getString(SpKey.deviceId),
            ticket_collection = soldTicketGroupWiseDataList.map {
                SyncSoldTicketBodyCollection(
                    stoppage = it.name,
                    unit_fare = it.fare,
                    total_fare = it.total_fare,
                    total_ticket = it.ticket_count
                )
            }

        )
    }

    /**
     * Get Current Counter name from shared preference
     * @return counterName : String
     * */
    fun getCurrentCounterName(): String {
        return sharedPrefHelper.getString(SpKey.counterName)
    }
    /**
     * Get Current Counter Json File name from shared preference
     * @return json file name : String
     * */
    fun getCounterFileName(): String {
        return sharedPrefHelper.getString(SpKey.counterFileName)
    }
    /**
     * Get CounterListEntity generated from Json String Using Gson
     * @return CounterListEntity
     * */
    fun getCounterListFromJson(jsonString: String): CounterListEntity {
        return gson.fromJson(jsonString, CounterListEntity::class.java)
    }
    /**
     * @param counterEntity
     * Update stoppage list if the user changes counter from dashboard screen.
     * */
    fun updateStoppageList(counterEntity: CounterEntity) {
        viewModelScope.launch {
            sharedPrefHelper.putString(SpKey.counterName, counterEntity.counter_name)
            cacheRepository.deleteSelectedBusCounterEntity()
            cacheRepository.insertSelectedBusCounterEntity(counterEntity.stoppage_list)
        }
    }
    fun fetchSoldTicketCount(){
        viewModelScope.launch {
            cacheRepository.fetchSoldTicketCount()
                .collect {
                    _unSyncTicketCountState.value = it
                }
        }
    }

    fun fetchSoldTicketTotalFare(){
        viewModelScope.launch {
            cacheRepository.fetchSoldTicketTotalFare()
                .collect {
                    _unSyncTicketAmountState.value = it
                }
        }
    }


    fun printReport(totalCount:Int,totalFare:Int) {
        if (SunmiPrintHelper.instance.showPrinterStatus(application)) {
            try {
                viewModelScope.launch {
                    val currentDeviceDate = BanglaConverterUtil.convertMonthNumberToBengali(DateTimeParser.getCurrentDeviceDateTime(DateTimeFormat.outputDMY))
                    val currentDeviceTime = BanglaConverterUtil.convertNumberToBengaliNumber(DateTimeParser.getCurrentDeviceDateTime(DateTimeFormat.outputHMSA))

                    cacheRepository.deleteAllSoldTicket()

                }

            } catch (e: Exception) {
                Toast.makeText(application, "Print device is offline", Toast.LENGTH_SHORT).show()
                viewModelScope.launch {
                    cacheRepository.deleteAllSoldTicket()
                }
            }
        }else{
            viewModelScope.launch {
                cacheRepository.deleteAllSoldTicket()
            }
        }
    }


}