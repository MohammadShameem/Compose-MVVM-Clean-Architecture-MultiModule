package com.jatri.common.extfun

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController

fun <T> Fragment.navigationBackStackResult(key: String = "key") =
    findNavController().currentBackStackEntry?.savedStateHandle?.get<T>(key)


fun <T> Fragment.navigationBackStackResultLiveData(key: String = "key"): MutableLiveData<T>? {

    viewLifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_DESTROY) {
            findNavController().previousBackStackEntry?.savedStateHandle?.remove<T>(key)
        }
    })

    return findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData(key)
}

fun <T> Fragment.setNavigationBackStackResult(key: String = "key", result: T) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}