package com.example.shameem.ui.home

import android.app.Application
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.example.cache.CacheRepository
import com.example.common.constant.AppConstant
import com.example.common.dateparser.DateTimeFormat
import com.example.common.dateparser.DateTimeParser
import com.example.entity.cachentity.SoldTicketEntity
import com.example.entity.cachentity.StoppageEntity
import com.example.entity.ticketdesign.TicketFormatEntity
import com.example.shameem.R
import com.example.shameem.helper.BanglaConverterUtil
import com.example.shameem.helper.loadJsonFromAsset
import com.example.shameem.printer.SunmiPrintHelper
import com.example.sharedpref.SharedPrefHelper
import com.example.sharedpref.SpKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cacheRepository: CacheRepository,
    private val sharedPrefHelper: SharedPrefHelper,
    private val application: Application,
    private val gson: Gson,
): ViewModel() {

    private val _stoppageState = MutableStateFlow<List<StoppageEntity>>(listOf())
    val stoppageState : StateFlow<List<StoppageEntity>> = _stoppageState

    private val _unSyncTicketCountState = MutableStateFlow<Int>(0)
    val unSyncTicketCountState : StateFlow<Int> = _unSyncTicketCountState

    private val _unSyncTicketAmountState = MutableStateFlow<Int>(0)
    val unSyncTicketAmountState : StateFlow<Int> = _unSyncTicketAmountState

    private var currentSerial = 0
    private var incrementSerial = 0

    suspend fun getTicketFormatEntity() : TicketFormatEntity{
        var jsonString = ""
        val fileName = sharedPrefHelper.getString(SpKey.ticketFormatFileName)
        application.loadJsonFromAsset(fileName)
            .onSuccess {
                jsonString = it
            }
        return gson.fromJson(jsonString, TicketFormatEntity::class.java)
    }


    fun fetchBusCounterList(){
        viewModelScope.launch {
            cacheRepository.fetchSelectedBusCounterEntityList()
                .collect {
                    _stoppageState.value = it
                }
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

    fun printAndInsertTicket(stoppageEntity: StoppageEntity, ticketFormatEntity: TicketFormatEntity,
                             isConfigStudentFareEnable: Boolean, studentFare: Boolean){
        if (SunmiPrintHelper.instance.showPrinterStatus(application)){
            currentSerial = sharedPrefHelper.getInt(SpKey.soldTicketSerial)
            try {
                incrementSerial = currentSerial + 1
                if (incrementSerial>currentSerial){
                    sharedPrefHelper.putInt(SpKey.soldTicketSerial, incrementSerial)
                    val currentDeviceDate = BanglaConverterUtil.convertMonthNumberToBengali(
                        DateTimeParser.getCurrentDeviceDateTime(DateTimeFormat.outputDMY))
                    val currentDeviceTime = BanglaConverterUtil.convertNumberToBengaliNumber(
                        DateTimeParser.getCurrentDeviceDateTime(DateTimeFormat.outputHMSA))

                    ticketFormatEntity.ticket_format.forEach{
                        if (it.is_center!=null){
                            if (it.is_center!!){
                                SunmiPrintHelper.instance.setAlign(1)
                            } else{
                                SunmiPrintHelper.instance.setAlign(0)
                            }
                        }


                    }
                    SunmiPrintHelper.instance.feedPaper()

                    viewModelScope.launch {
                        cacheRepository.insertSoldTicketEntity(
                            SoldTicketEntity(
                                serial = incrementSerial,
                                name = stoppageEntity.name,
                                fare = if (isConfigStudentFareEnable&&studentFare) stoppageEntity.fare_student else stoppageEntity.fare
                            )
                        )
                    }
                }else{
                    Toast.makeText(application, application.getString(R.string.toast_printer_is_abnormal), Toast.LENGTH_SHORT).show()
                }
            }catch (e: Exception){
                Toast.makeText(application, application.getString(R.string.toast_printer_device_is_offline), Toast.LENGTH_SHORT).show()
                if (incrementSerial>currentSerial){
                    sharedPrefHelper.putInt(SpKey.soldTicketSerial, currentSerial)
                }
            }
        }
    }
}
