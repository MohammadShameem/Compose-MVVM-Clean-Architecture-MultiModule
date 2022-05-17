package com.jatri.offlinecounterticketing.ui.configuration

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.jatri.entity.companylist.OfflineCompanyEntity
import com.jatri.entity.counterlist.CounterListEntity
import com.jatri.sharedpref.SharedPrefHelper
import com.jatri.sharedpref.SpKey
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ConfigurationViewModel
@Inject constructor(
    private val gson: Gson,
    private val sharedPrefHelper: SharedPrefHelper
) : ViewModel() {
    fun getCounterListFromJson(jsonString: String): CounterListEntity {
        return gson.fromJson(jsonString, CounterListEntity::class.java)
    }

    fun saveCompanyInfoToSharedPreference(
        companyEntity: OfflineCompanyEntity,
        studentFare: Boolean
    ) {
        sharedPrefHelper.putString(SpKey.companyName, companyEntity.name)
        sharedPrefHelper.putString(
            SpKey.ticketFormatFileName,
            companyEntity.ticket_format_file_name
        )
        sharedPrefHelper.putBool(SpKey.studentFare, studentFare)
    }
}