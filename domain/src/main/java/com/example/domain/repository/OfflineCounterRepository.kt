package com.example.domain.repository
import androidx.lifecycle.LiveData
import com.example.domain.usecase.dashboard.ChangePasswordApiUseCase
import com.example.domain.usecase.login.LoginApiUseCase
import com.example.entity.dashboard.ChangePasswordApiEntity
import com.example.entity.dashboard.SyncSoldTicketBody
import com.example.entity.dashboard.SyncedSoldTicketApiEntity
import com.example.entity.login.LoginEntity
import com.example.entity.res.ApiResponse

interface OfflineCounterRepository {
    fun fetchLogin(params: LoginApiUseCase.Params): LiveData<ApiResponse<LoginEntity>>

    fun changePassword(
        params: ChangePasswordApiUseCase.Params
    ) : LiveData<ApiResponse<ChangePasswordApiEntity>>

    fun syncSoldTicketData(
        syncSoldTicketBody: SyncSoldTicketBody
    ) : LiveData<ApiResponse<SyncedSoldTicketApiEntity>>


}