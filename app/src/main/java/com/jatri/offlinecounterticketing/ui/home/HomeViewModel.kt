package com.jatri.offlinecounterticketing.ui.home

import android.app.Application
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.jatri.cache.CacheRepository
import com.jatri.common.constant.AppConstant
import com.jatri.common.dateparser.DateTimeFormat
import com.jatri.common.dateparser.DateTimeParser
import com.jatri.entity.cachentity.SoldTicketEntity
import com.jatri.entity.cachentity.StoppageEntity
import com.jatri.entity.ticketdesign.TicketFormatEntity
import com.jatri.offlinecounterticketing.helper.BanglaConverterUtil
import com.jatri.offlinecounterticketing.helper.loadJsonFromAsset
import com.jatri.offlinecounterticketing.printer.SunmiPrintHelper
import com.jatri.sharedpref.SharedPrefHelper
import com.jatri.sharedpref.SpKey
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

    fun printAndInsertTicket(stoppageEntity: StoppageEntity, ticketFormatEntity: TicketFormatEntity, isConfigStudentFareEnable: Boolean, studentFare: Boolean){
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

                        when (it.type) {
                            AppConstant.companyName -> {
                                SunmiPrintHelper.instance.printText("${it.name}\n", it.font_size.toFloat(),
                                    isBold = it.is_bold, isUnderLine = false)
                            }
                            AppConstant.ticketSerial -> {
                                SunmiPrintHelper.instance.printText("${it.leading_text}${BanglaConverterUtil.convertNumberToBengaliNumber("$incrementSerial")}\n", it.font_size.toFloat(),
                                    isBold = it.is_bold, isUnderLine = false)
                            }
                            AppConstant.text -> {
                                SunmiPrintHelper.instance.printText("${it.text}\n", it.font_size.toFloat(),
                                    isBold = it.is_bold, isUnderLine = false)
                            }
                            AppConstant.complainNumber -> {
                                SunmiPrintHelper.instance.printText("${it.leading_text+it.text}\n", it.font_size.toFloat(),
                                    isBold = it.is_bold, isUnderLine = false)
                            }
                            AppConstant.date -> {
                                SunmiPrintHelper.instance.printText("${it.leading_text+currentDeviceDate}\n", it.font_size.toFloat(),
                                    isBold = it.is_bold, isUnderLine = false)
                            }
                            AppConstant.time -> {
                                SunmiPrintHelper.instance.printText("${it.leading_text+currentDeviceTime}\n", it.font_size.toFloat(),
                                    isBold = it.is_bold, isUnderLine = false)
                            }
                            AppConstant.dateTime -> {
                                SunmiPrintHelper.instance.printText("${it.leading_text}$currentDeviceDate $currentDeviceTime\n", it.font_size.toFloat(),
                                    isBold = it.is_bold, isUnderLine = false)
                            }
                            AppConstant.routeName -> {
                                if (it.is_dynamic){
                                    SunmiPrintHelper.instance.printText("${stoppageEntity.name}\n",
                                        it.font_size.toFloat(), isBold = it.is_bold, isUnderLine = false)
                                } else{
                                    SunmiPrintHelper.instance.printText("${it.name}\n",
                                        it.font_size.toFloat(), isBold = it.is_bold, isUnderLine = false)
                                }
                            }
                            AppConstant.fare -> {
                                if (isConfigStudentFareEnable&&studentFare){
                                    SunmiPrintHelper.instance.printText("${it.leading_student_fare_text}${BanglaConverterUtil.convertNumberToBengaliNumber("${stoppageEntity.fare_student}")}\n",
                                        it.font_size.toFloat(), isBold = it.is_bold, isUnderLine = false)
                                }else{
                                    SunmiPrintHelper.instance.printText("${it.leading_text}${BanglaConverterUtil.convertNumberToBengaliNumber("${stoppageEntity.fare}")}\n",
                                        it.font_size.toFloat(), isBold = it.is_bold, isUnderLine = false)
                                }
                            }
                            AppConstant.image->{
                                val imageId: Int = application.resources.getIdentifier(it.text, "drawable", application.packageName)
                                SunmiPrintHelper.instance.printBitmap(BitmapFactory.decodeResource(application.resources, imageId),0)
                                SunmiPrintHelper.instance.printText("\n" ,10f,isBold = true, isUnderLine = false)
                            }
                            AppConstant.qrCode ->{
                                var qrCodeData = ""
                                it.text.split(",").forEach { textQrCodeData->
                                    when(textQrCodeData){
                                        AppConstant.qrSerial ->{
                                            qrCodeData += "$incrementSerial,"
                                        }
                                        AppConstant.qrName ->{
                                            qrCodeData += "${stoppageEntity.name},"
                                        }
                                        AppConstant.qrDate ->{
                                            qrCodeData += "$currentDeviceDate,"
                                        }
                                        AppConstant.qrTime ->{
                                            qrCodeData += "$currentDeviceTime,"
                                        }
                                    }
                                }
                                SunmiPrintHelper.instance.printQr(qrCodeData,3,1)
                                SunmiPrintHelper.instance.printText("\n" ,10f,isBold = true, isUnderLine = false)
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
                    Toast.makeText(application, "Printer is abnormal. Please try again", Toast.LENGTH_SHORT).show()
                }
            }catch (e: Exception){
                Toast.makeText(application, "Print device is offline", Toast.LENGTH_SHORT).show()
                if (incrementSerial>currentSerial){
                    sharedPrefHelper.putInt(SpKey.soldTicketSerial, currentSerial)
                }
            }
        }
    }
}
