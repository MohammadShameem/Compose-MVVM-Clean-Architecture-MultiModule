package com.jatri.offlinecounterticketing.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jatri.domain.entity.StoppageEntity
import dagger.hilt.android.AndroidEntryPoint
import com.jatri.offlinecounterticketing.ui.theme.OfflineCounterTicketingTheme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(){
    private val viewModel: HomeViewModel by viewModels()
    private var counterList = listOf<StoppageEntity>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View= ComposeView(inflater.context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect {uiState->
                    // New value received
                    when (uiState) {
                        is BusCounterListUiState.Success -> {
                            counterList = uiState.busCounterList
                        }
                        is BusCounterListUiState.Error -> {

                        }
                        is BusCounterListUiState.Loading -> {

                        }
                    }
                }
            }
            setContent {
                OfflineCounterTicketingTheme {
                    HomeScreen(counterList){
                        Toast.makeText(requireActivity(),"Hello World- Clicked", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }



    }


}