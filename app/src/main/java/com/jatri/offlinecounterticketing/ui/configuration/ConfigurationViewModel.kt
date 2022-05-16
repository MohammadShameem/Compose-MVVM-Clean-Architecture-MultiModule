package com.jatri.offlinecounterticketing.ui.configuration

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.jatri.entity.counterlist.CounterListEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConfigurationViewModel
@Inject constructor(
    private val gson: Gson
) : ViewModel() {

    fun getCounterListFromJson(jsonString: String) : CounterListEntity{
        return gson.fromJson(jsonString,CounterListEntity::class.java)
    }
}