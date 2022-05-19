package com.jatri.offlinecounterticketing.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.jatri.cache.CacheRepository
import com.jatri.domain.entity.CounterEntity
import com.jatri.domain.entity.CounterListEntity
import com.jatri.domain.usecase.dashboard.ChangePasswordApiUseCase
import com.jatri.domain.usecase.dashboard.SyncedSoldTicketApiUseCase
import com.jatri.entity.dashboard.ChangePasswordApiEntity
import com.jatri.entity.dashboard.SyncSoldTicketBody
import com.jatri.entity.dashboard.SyncedSoldTicketApiEntity
import com.jatri.entity.res.ApiResponse
import com.jatri.offlinecounterticketing.R
import com.jatri.sharedpref.SharedPrefHelper
import com.jatri.sharedpref.SpKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val gson: Gson,
    private val changePasswordApiUseCase: ChangePasswordApiUseCase,
    private val syncedSoldTicketApiUseCase: SyncedSoldTicketApiUseCase,
    private val sharedPrefHelper: SharedPrefHelper,
    private val cacheRepository: CacheRepository
) : ViewModel() {

    val errorMessageLiveDataOfValidation = MutableLiveData<Int>()

    fun changePassword(
        params: ChangePasswordApiUseCase.Params
    ): LiveData<ApiResponse<ChangePasswordApiEntity>> = changePasswordApiUseCase.execute(params)


    fun syncSoldTicket(
        soldTicketBody: SyncSoldTicketBody
    ): LiveData<ApiResponse<SyncedSoldTicketApiEntity>> =
        syncedSoldTicketApiUseCase.execute(soldTicketBody)


    fun validateOldPasswordAndNewPassword(oldPassword: String, newPassword: String): Boolean {
        return if (oldPassword.isEmpty()) {
            errorMessageLiveDataOfValidation.value = R.string.error_msg_enter_oldPassword
            false
        } else if (newPassword.isEmpty()) {
            errorMessageLiveDataOfValidation.value = R.string.error_msg_enter_newPassword
            false
        } else true
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

}