package com.jatri.offlinecounterticketing.ui.dashboard

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jatri.domain.entity.StoppageEntity
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val changePasswordApiUseCase: ChangePasswordApiUseCase,
    private val syncedSoldTicketApiUseCase: SyncedSoldTicketApiUseCase,
    private val sharedPrefHelper: SharedPrefHelper
) : ViewModel() {

    val errorMessageLiveDataOfValidation = MutableLiveData<Int>()
    //val isPasswordUpdated = MutableLiveData<Boolean>()


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

    fun getCurrentCounterName() : String {
        return sharedPrefHelper.getString(SpKey.counterName)
    }

}