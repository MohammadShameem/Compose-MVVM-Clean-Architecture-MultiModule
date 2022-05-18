package com.jatri.offlinecounterticketing.ui.configuration

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.jatri.cache.CacheRepository
import com.jatri.domain.entity.CounterEntity
import com.jatri.domain.entity.CounterListEntity
import com.jatri.domain.entity.StoppageEntity
import com.jatri.entity.companylist.OfflineCompanyEntity
import com.jatri.sharedpref.SharedPrefHelper
import com.jatri.sharedpref.SpKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfigurationViewModel
@Inject constructor(
    private val gson: Gson,
    private val sharedPrefHelper: SharedPrefHelper,
    private val cacheRepository: CacheRepository
) : ViewModel() {

    fun getCounterListFromJson(jsonString: String): CounterListEntity {
        return gson.fromJson(jsonString, CounterListEntity::class.java)
    }

    fun saveCompanyInfoToSharedPreference(
        companyEntity: OfflineCompanyEntity,
        studentFare: Boolean,
        counterEntity: CounterEntity
    ) {
        viewModelScope.launch {
            sharedPrefHelper.putString(SpKey.companyName, companyEntity.name)
            sharedPrefHelper.putString(SpKey.counterFileName, companyEntity.counter_file_name)
            sharedPrefHelper.putString(SpKey.counterName, counterEntity.counter_name)
            sharedPrefHelper.putString(
                SpKey.ticketFormatFileName,
                companyEntity.ticket_format_file_name)
            sharedPrefHelper.putBool(SpKey.studentFare, studentFare)
            cacheRepository.insertSelectedBusCounterEntity(counterEntity.stoppage_list)
        }

    }
}