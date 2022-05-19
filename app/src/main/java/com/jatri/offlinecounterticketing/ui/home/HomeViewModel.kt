package com.jatri.offlinecounterticketing.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jatri.cache.CacheRepository
import com.jatri.domain.entity.StoppageEntity
import com.jatri.sharedpref.SharedPrefHelper
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

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
