package com.jatri.domain.repository
import androidx.lifecycle.LiveData
import com.jatri.domain.usecase.dashboard.ChangePasswordApiUseCase
import com.jatri.domain.usecase.login.LoginApiUseCase
import com.jatri.entity.dashboard.ChangePasswordApiEntity
import com.jatri.entity.dashboard.SyncSoldTicketBody
import com.jatri.entity.dashboard.SyncedSoldTicketApiEntity
import com.jatri.entity.login.LoginEntity
import com.jatri.entity.res.ApiResponse

interface OfflineCounterRepository {
    fun fetchLogin(params: LoginApiUseCase.Params): LiveData<ApiResponse<LoginEntity>>

    fun changePassword(
        params: ChangePasswordApiUseCase.Params
    ) : LiveData<ApiResponse<ChangePasswordApiEntity>>

    fun syncSoldTicketData(
        syncSoldTicketBody: SyncSoldTicketBody
    ) : LiveData<ApiResponse<SyncedSoldTicketApiEntity>>


}