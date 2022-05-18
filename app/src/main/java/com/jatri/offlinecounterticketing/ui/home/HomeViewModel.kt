package com.jatri.offlinecounterticketing.ui.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jatri.cache.CacheRepository
import com.jatri.domain.entity.StoppageEntity
import com.jatri.sharedpref.SharedPrefHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cacheRepository: CacheRepository,
    private val sharedPrefHelper: SharedPrefHelper,
    private val application: Application
): ViewModel() {

    private val _uiState = MutableStateFlow<List<StoppageEntity>>(listOf())
    val uiState : StateFlow<List<StoppageEntity>> = _uiState

    init {
        getBusCounterList()
    }

    private fun getBusCounterList(){
        viewModelScope.launch {
            cacheRepository.fetchSelectedBusCounterEntityList()
                .collect {
                    _uiState.value = it
                }
        }
    }
}
