package com.jatri.offlinecounterticketing.ui.home

import android.app.Application
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

    private val _uiState = MutableStateFlow<BusCounterListUiState>(BusCounterListUiState.Empty)
    val uiState : StateFlow<BusCounterListUiState> = _uiState

    init {
        getBusCounterList()
    }

    private fun getBusCounterList(){
        viewModelScope.launch {
            cacheRepository.fetchSelectedBusCounterEntityList()
                .catch {
                    _uiState.value = BusCounterListUiState.Error("Some Things Went Wrong.")
                }
                .collect {
                    _uiState.value = BusCounterListUiState.Success(it)
                }
        }
    }
}

sealed class BusCounterListUiState {
    data class Success(val counterStoppageList: List<StoppageEntity>): BusCounterListUiState()
    data class Error(val errorMessage:String): BusCounterListUiState()
    object Empty: BusCounterListUiState()
}