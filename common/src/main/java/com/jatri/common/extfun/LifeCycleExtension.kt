package com.jatri.common.extfun

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

fun <T : ViewModel> AppCompatActivity.createViewModel(
    factory: ViewModelProvider.Factory, classType: Class<T>
): T = ViewModelProvider(this, factory)[classType]

fun <T : ViewModel> Fragment.createViewModel(
    factory: ViewModelProvider.Factory, classType: Class<T>
): T = ViewModelProvider(this, factory)[classType]


inline fun <T> LifecycleOwner.observe(liveData: LiveData<T>, crossinline action: (t: T) -> Unit) {
    liveData.observe(this, { it?.let { t -> action(t) } })
}


fun <T> Fragment.observeFragmentLiveData(liveData: LiveData<T>,  action: (t: T) -> Unit){
    liveData.observe(viewLifecycleOwner, { it?.let { t -> action(t) } })
}


