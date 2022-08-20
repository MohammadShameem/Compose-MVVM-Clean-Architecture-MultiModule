package com.example.shameem.application

import android.app.Application
import com.example.shameem.BuildConfig
import com.example.shameem.printer.SunmiPrintHelper
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