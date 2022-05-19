package com.jatri.offlinecounterticketing.ui.dashboard

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.jatri.cache.CacheRepository
import com.jatri.common.constant.AppConstant
import com.jatri.common.dateparser.DateTimeFormat
import com.jatri.common.dateparser.DateTimeParser
import com.jatri.domain.entity.*
import com.jatri.domain.usecase.dashboard.ChangePasswordApiUseCase
import com.jatri.domain.usecase.dashboard.SyncedSoldTicketApiUseCase
import com.jatri.entity.dashboard.ChangePasswordApiEntity
import com.jatri.entity.dashboard.SyncSoldTicketBody
import com.jatri.entity.dashboard.SyncSoldTicketBodyCollection
import com.jatri.entity.dashboard.SyncedSoldTicketApiEntity
import com.jatri.entity.res.ApiResponse
import com.jatri.offlinecounterticketing.R
import com.jatri.offlinecounterticketing.helper.BanglaConverterUtil
import com.jatri.offlinecounterticketing.printer.SunmiPrintHelper
import com.jatri.sharedpref.SharedPrefHelper
import com.jatri.sharedpref.SpKey
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
    private val _unSyncTicketCountState = MutableStateFlow<Int>(0)
    val unSyncTicketCountState : StateFlow<Int> = _unSyncTicketCountState

    private val _unSyncTicketAmountState = MutableStateFlow<Int>(0)
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
        var message = 0
        message = if (oldPassword.isEmpty()) R.string.error_msg_enter_oldPassword
        else if (newPassword.isEmpty()) R.string.error_msg_enter_newPassword
        else if (newPassword.length < 6) R.string.error_msg_6_character_required
        else AppConstant.validation_successful
        return message
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


    fun getCurrentCounterName(): String {
        return sharedPrefHelper.getString(SpKey.counterName)
    }

    fun getCounterFileName(): String {
        return sharedPrefHelper.getString(SpKey.counterFileName)
    }

    fun getCounterListFromJson(jsonString: String): CounterListEntity {
        return gson.fromJson(jsonString, CounterListEntity::class.java)
    }

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

                    SunmiPrintHelper.instance.setAlign(1)
                    SunmiPrintHelper.instance.printText("${sharedPrefHelper.getString(SpKey.companyName)}\n", 40f, isBold = true, isUnderLine = false)
                    SunmiPrintHelper.instance.printText("${sharedPrefHelper.getString(SpKey.counterName)}\n\n", 30f, isBold = true, isUnderLine = false)

                    SunmiPrintHelper.instance.setAlign(0)
                    val soldTicketList = cacheRepository.fetchSoldTicketGroupWise()
                    soldTicketList.forEach {
                        SunmiPrintHelper.instance.printText("${BanglaConverterUtil.convertNumberToBengaliNumber(it.fare.toString())} x ${BanglaConverterUtil.convertNumberToBengaliNumber(it.ticket_count.toString())} = " +
                                "${BanglaConverterUtil.convertNumberToBengaliNumber(it.total_fare.toString())} টাকা\n", 26f, isBold = true, isUnderLine = false)
                    }
                    SunmiPrintHelper.instance.printText("\n", 26f, isBold = true, isUnderLine = false)
                    SunmiPrintHelper.instance.printText("সর্বমোট টিকেট: ${BanglaConverterUtil.convertNumberToBengaliNumber(totalCount.toString())}\n",
                        26f, isBold = true, isUnderLine = false)
                    SunmiPrintHelper.instance.printText("সর্বমোট ভাড়া: ${BanglaConverterUtil.convertNumberToBengaliNumber(totalFare.toString())} টাকা\n",
                        26f, isBold = true, isUnderLine = false)
                    SunmiPrintHelper.instance.printText("তারিখ: \n$currentDeviceDate $currentDeviceTime\n", 26f, isBold = true, isUnderLine = false)

                    SunmiPrintHelper.instance.setAlign(1)
                    SunmiPrintHelper.instance.printText("সার্বিক সহযোগিতায় যাত্রী সার্ভিস লিমিটেড\n", 20f, isBold = true, isUnderLine = false)
                    SunmiPrintHelper.instance.feedPaper()

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