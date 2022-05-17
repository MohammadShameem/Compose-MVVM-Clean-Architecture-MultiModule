package com.jatri.offlinecounterticketing.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jatri.domain.usecase.login.LoginApiUseCase
import com.jatri.entity.login.LoginEntity
import com.jatri.entity.res.ApiResponse
import com.jatri.offlinecounterticketing.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.security.AccessControlContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginApiUseCase: LoginApiUseCase
): ViewModel(){
    val errorMessageLiveData = MutableLiveData<Int>()
    /**
     * login user
     * @param: params as LoginUseCaseParas which include phoneNumber and password
    * */
    fun login(params: LoginApiUseCase.Params) : LiveData<ApiResponse<LoginEntity>> =
         loginApiUseCase.execute(params)

    /**
     * Validate phone number and password
     * @param: phoneNumber as User Phone number
     * @param: password as User password
     * @return: boolean
    * */
    fun validatePhoneNumberAndPassword(phoneNumber: String , password: String) : Boolean {
        return if(phoneNumber.isEmpty()){
            errorMessageLiveData.value  = R.string.error_message_empty_number
            false
        }else if(phoneNumber.length < 11){
            errorMessageLiveData.value = R.string.error_msg_invalid_number
            false
        }else if(password.isEmpty()){
            errorMessageLiveData.value = R.string.error_msg_enter_password
            false
        }else true
    }

}