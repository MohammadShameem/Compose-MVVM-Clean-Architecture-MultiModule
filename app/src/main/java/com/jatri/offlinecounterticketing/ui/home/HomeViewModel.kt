package com.jatri.offlinecounterticketing.ui.home

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.jatri.cache.CacheRepository
import com.jatri.common.constant.AppConstant
import com.jatri.common.dateparser.DateTimeFormat
import com.jatri.common.dateparser.DateTimeParser
import com.jatri.domain.entity.CounterListEntity
import com.jatri.domain.entity.SoldTicketEntity
import com.jatri.domain.entity.StoppageEntity
import com.jatri.entity.ticketdesign.TicketFormatEntity
import com.jatri.offlinecounterticketing.helper.BanglaConverterUtil
import com.jatri.offlinecounterticketing.helper.loadJsonFromAsset
import com.jatri.offlinecounterticketing.printer.SunmiPrintHelper
import com.jatri.sharedpref.SharedPrefHelper
import com.jatri.sharedpref.SpKey
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

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
        Timber.e("$fileName")
        application.loadJsonFromAsset(fileName)
            .onSuccess {
                jsonString = it
            }
        Timber.e("$jsonString")
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

    fun printAndInsertTicket(stoppageEntity: StoppageEntity,ticketFormatEntity: TicketFormatEntity){
        viewModelScope.launch {
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
                            if (it.is_center){
                                SunmiPrintHelper.instance.setAlign(1)
                            } else{
                                SunmiPrintHelper.instance.setAlign(0)
                            }

                            if (it.type== AppConstant.companyName) {
                                SunmiPrintHelper.instance.printText("${it.name}\n", it.font_size.toFloat(),
                                    isBold = it.is_bold, isUnderLine = false)
                            }
                            else if(it.type==AppConstant.ticketSerial){
                                SunmiPrintHelper.instance.printText("${it.leading_text+incrementSerial}\n", it.font_size.toFloat(),
                                    isBold = it.is_bold, isUnderLine = false)
                            }
                            else if(it.type==AppConstant.text){
                                SunmiPrintHelper.instance.printText("${it.text}\n", it.font_size.toFloat(),
                                    isBold = it.is_bold, isUnderLine = false)
                            }
                            else if(it.type==AppConstant.complainNumber){
                                SunmiPrintHelper.instance.printText("${it.text}\n", it.font_size.toFloat(),
                                    isBold = it.is_bold, isUnderLine = false)
                            }
                            else if(it.type==AppConstant.date){
                                SunmiPrintHelper.instance.printText("${it.leading_text+currentDeviceDate}\n", it.font_size.toFloat(),
                                    isBold = it.is_bold, isUnderLine = false)
                            }
                            else if(it.type==AppConstant.time){
                                SunmiPrintHelper.instance.printText("${it.leading_text+currentDeviceTime}\n", it.font_size.toFloat(),
                                    isBold = it.is_bold, isUnderLine = false)
                            }
                            else if (it.type==AppConstant.routeName){
                                if (it.is_dynamic){
                                    SunmiPrintHelper.instance.printText("${stoppageEntity.name}\n",
                                        it.font_size.toFloat(), isBold = it.is_bold, isUnderLine = false)
                                }
                                else{
                                    SunmiPrintHelper.instance.printText("${it.name}\n",
                                        it.font_size.toFloat(), isBold = it.is_bold, isUnderLine = false)
                                }
                            }
                            else if (it.type == AppConstant.fare){
                                if (sharedPrefHelper.getBoolean(SpKey.studentFare)){
                                    SunmiPrintHelper.instance.printText("${it.leading_student_fare_text+it.name}\n",
                                        it.font_size.toFloat(), isBold = it.is_bold, isUnderLine = false)
                                }else{
                                    SunmiPrintHelper.instance.printText("${it.leading_text+it.name}\n",
                                        it.font_size.toFloat(), isBold = it.is_bold, isUnderLine = false)
                                }
                            }
                        }
                        SunmiPrintHelper.instance.feedPaper()
/*
                    //print ticket here
                    SunmiPrintHelper.instance.setAlign(1)
                    SunmiPrintHelper.instance.printText("মেট্রো প্রভাতী / সোনার বাংলা\n", 25f, isBold = false, isUnderLine = false)
                    SunmiPrintHelper.instance.printText("বাস কাউন্টার সার্ভিস \n", 20f, isBold = false, isUnderLine = false)
                    SunmiPrintHelper.instance.printText("${busCounterEntity.name}\n", 35f, isBold = true, isUnderLine = false)

                    SunmiPrintHelper.instance.setAlign(0)
                    if (sharedPrefHelper.getBoolean(SpKey.studentFare)) SunmiPrintHelper.instance
                        .printText("স্টুডেন্ট ভাড়া - ${BanglaConverterUtil.convertNumberToBengaliNumber("${busCounterEntity.fare_student}")}\n",
                            40f, isBold = true, isUnderLine = false)
                    else SunmiPrintHelper.instance.printText("ভাড়া - ${BanglaConverterUtil.convertNumberToBengaliNumber("${busCounterEntity.fare}")}\n",
                        40f, isBold = true, isUnderLine = false)
                    SunmiPrintHelper.instance.printText("সিরিয়াল নং - ${BanglaConverterUtil.convertNumberToBengaliNumber("$incrementSerial")}\n",
                        26f, isBold = true, isUnderLine = false)
                    SunmiPrintHelper.instance.setAlign(0)
                    SunmiPrintHelper.instance.printText("তারিখ - $currentDeviceDate\n", 26f, isBold = true, isUnderLine = false)
                    SunmiPrintHelper.instance.printText("সময় - $currentDeviceTime\n", 26f, isBold = true, isUnderLine = false)

                    SunmiPrintHelper.instance.setAlign(1)
                    SunmiPrintHelper.instance.printText("অভিযোগ / পরামর্শ \n", 24f, isBold = false, isUnderLine = false)
                    SunmiPrintHelper.instance.printText("০১৯৮৬৩২৩২৩২/০১৯৯৭৭০৪০৬৪\n", 24f, isBold = false, isUnderLine = false)
                    SunmiPrintHelper.instance.printText("সহযোগিতায়: যাত্রী সার্ভিস লি।\n", 20f, isBold = true, isUnderLine = false)

                    SunmiPrintHelper.instance.setAlign(1)
                    val qrText = "$incrementSerial"+" , "+ busCounterEntity.name +" , "+currentDeviceDate + " " + currentDeviceTime
                    SunmiPrintHelper.instance.printQr(qrText,3,1)
                    SunmiPrintHelper.instance.feedPaper()*/

                        cacheRepository.insertSoldTicketEntity(
                            SoldTicketEntity(
                                serial = incrementSerial,
                                name = stoppageEntity.name,
                                fare = stoppageEntity.fare))

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



}
