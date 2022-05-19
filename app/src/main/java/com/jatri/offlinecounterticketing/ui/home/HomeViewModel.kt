package com.jatri.offlinecounterticketing.ui.home

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jatri.cache.CacheRepository
import com.jatri.common.dateparser.DateTimeFormat
import com.jatri.common.dateparser.DateTimeParser
import com.jatri.domain.entity.SoldTicketEntity
import com.jatri.domain.entity.StoppageEntity
import com.jatri.offlinecounterticketing.helper.BanglaConverterUtil
import com.jatri.offlinecounterticketing.printer.SunmiPrintHelper
import com.jatri.sharedpref.SharedPrefHelper
import com.jatri.sharedpref.SpKey
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cacheRepository: CacheRepository,
    private val sharedPrefHelper: SharedPrefHelper,
    private val application: Application
): ViewModel() {

    private val _stoppageState = MutableStateFlow<List<StoppageEntity>>(listOf())
    val stoppageState : StateFlow<List<StoppageEntity>> = _stoppageState

    private val _unSyncTicketCountState = MutableStateFlow<Int>(0)
    val unSyncTicketCountState : StateFlow<Int> = _unSyncTicketCountState

    private val _unSyncTicketAmountState = MutableStateFlow<Int>(0)
    val unSyncTicketAmountState : StateFlow<Int> = _unSyncTicketAmountState

    private var currentSerial = 0
    private var incrementSerial = 0

    init {
        fetchBusCounterList()
        fetchSoldTicketCount()
        fetchSoldTicketTotalFare()
    }

    private fun fetchBusCounterList(){
        viewModelScope.launch {
            cacheRepository.fetchSelectedBusCounterEntityList()
                .collect {
                    _stoppageState.value = it
                }
        }
    }

    private fun fetchSoldTicketCount(){
        viewModelScope.launch {
            cacheRepository.fetchSoldTicketCount()
                .collect {
                    _unSyncTicketCountState.value = it
                }
        }
    }

    private fun fetchSoldTicketTotalFare(){
        viewModelScope.launch {
            cacheRepository.fetchSoldTicketTotalFare()
                .collect {
                    _unSyncTicketAmountState.value = it
                }
        }
    }

    fun printAndInsertTicket(stoppageEntity: StoppageEntity){
        if (SunmiPrintHelper.instance.showPrinterStatus(application)){
            currentSerial = sharedPrefHelper.getInt(SpKey.soldTicketSerial)
            try {
                incrementSerial = currentSerial + 1
                if (incrementSerial>currentSerial){
                    sharedPrefHelper.putInt(SpKey.soldTicketSerial, incrementSerial)
/*

                    val currentDeviceDate = BanglaConverterUtil.convertMonthNumberToBengali(
                        DateTimeParser.getCurrentDeviceDateTime(DateTimeFormat.outputDMY))
                    val currentDeviceTime = BanglaConverterUtil.convertNumberToBengaliNumber(
                        DateTimeParser.getCurrentDeviceDateTime(DateTimeFormat.outputHMSA))

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
                    SunmiPrintHelper.instance.feedPaper()
*/

                    //Insert to database
                    viewModelScope.launch {
                        cacheRepository.insertSoldTicketEntity(
                            SoldTicketEntity(
                                serial = incrementSerial,
                                name = stoppageEntity.name,
                                fare = stoppageEntity.fare
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
