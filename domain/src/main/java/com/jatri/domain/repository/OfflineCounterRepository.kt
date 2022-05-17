package com.jatri.domain.repository
import androidx.lifecycle.LiveData
import com.jatri.domain.usecase.dashboard.ChangePasswordProfileInfoApiUseCase
import com.jatri.domain.usecase.login.LoginApiUseCase
import com.jatri.entity.dashboard.ChangePasswordProfileInfoApiEntity
import com.jatri.entity.dashboard.SyncSoldTicketBody
import com.jatri.entity.dashboard.SyncedSoldTicketApiEntity
import com.jatri.entity.login.LoginEntity
import com.jatri.entity.res.ApiResponse

interface OfflineCounterRepository {
    fun fetchLogin(params: LoginApiUseCase.Params): LiveData<ApiResponse<LoginEntity>>

    fun changePassword(
        params: ChangePasswordProfileInfoApiUseCase.Params
    ) : LiveData<ApiResponse<ChangePasswordProfileInfoApiEntity>>

    fun syncSoldTicketData(
        syncSoldTicketBody: SyncSoldTicketBody
    ) : LiveData<ApiResponse<SyncedSoldTicketApiEntity>>


}