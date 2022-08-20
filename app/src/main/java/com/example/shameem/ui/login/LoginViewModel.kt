package com.example.shameem.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.usecase.login.LoginApiUseCase
import com.example.entity.login.LoginEntity
import com.example.entity.res.ApiResponse
import com.example.shameem.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginApiUseCase: LoginApiUseCase
): ViewModel(){
    val errorMessageLiveDataOfValidation = MutableLiveData<Int>()
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
            errorMessageLiveDataOfValidation.value  = R.string.error_message_empty_number
            false
        }else if(phoneNumber.length < 11){
            errorMessageLiveDataOfValidation.value = R.string.error_msg_invalid_number
            false
        }else if(password.isEmpty()){
            errorMessageLiveDataOfValidation.value = R.string.error_msg_enter_password
            false
        }else true
    }

}