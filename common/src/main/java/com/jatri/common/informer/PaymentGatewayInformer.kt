package com.jatri.common.informer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PaymentGatewayInformer : ViewModel(){
    private val _reloadPaymentInfo = MutableLiveData<Boolean>()
    val reloadPaymentInfo get() = _reloadPaymentInfo

    fun triggerReloadPaymentInfo(){
        _reloadPaymentInfo.value = true
    }
}