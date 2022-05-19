package com.jatri.offlinecounterticketing.ui.dashboard

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jatri.common.constant.AppConstant
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

    fun changePassword(
        params: ChangePasswordApiUseCase.Params
    ): LiveData<ApiResponse<ChangePasswordApiEntity>> = changePasswordApiUseCase.execute(params)


    fun syncSoldTicket(
        soldTicketBody: SyncSoldTicketBody
    ): LiveData<ApiResponse<SyncedSoldTicketApiEntity>> =
        syncedSoldTicketApiUseCase.execute(soldTicketBody)

    fun validateOldPasswordAndNewPassword(oldPassword: String, newPassword: String): Int {
        var message = 0
        message = if (oldPassword.isEmpty()) R.string.error_msg_enter_oldPassword
        else if (newPassword.isEmpty()) R.string.error_msg_enter_newPassword
        else if(newPassword.length < 6) R.string.error_msg_6_character_required
        else AppConstant.validation_successful
        return message
    }

    fun getCurrentCounterName() : String {
        return sharedPrefHelper.getString(SpKey.counterName)
    }

}