package com.jatri.offlinecounterticketing.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jatri.domain.usecase.login.LoginApiUseCase
import com.jatri.entity.login.LoginEntity
import com.jatri.entity.res.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginApiUseCase: LoginApiUseCase
): ViewModel(){

     fun login(params: LoginApiUseCase.Params) : LiveData<ApiResponse<LoginEntity>> =
         loginApiUseCase.execute(params)

}