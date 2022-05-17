package com.jatri.offlinecounterticketing.application

import android.app.Application
import com.jatri.offlinecounterticketing.BuildConfig
import com.jatri.offlinecounterticketing.printer.SunmiPrintHelper
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        SunmiPrintHelper.instance.initSunmiPrinterService(this)
        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }

    }

}