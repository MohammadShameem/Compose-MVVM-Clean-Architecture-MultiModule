package com.jatri.offlinecounterticketing.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.jatri.domain.entity.StoppageEntity
import com.jatri.offlinecounterticketing.ui.components.ToolbarWithButtonLarge
import dagger.hilt.android.AndroidEntryPoint
import com.jatri.offlinecounterticketing.ui.theme.OfflineCounterTicketingTheme
import com.jatri.sharedpref.SharedPrefHelper
import com.jatri.sharedpref.SpKey
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(){
    private val viewModel: HomeViewModel by viewModels()
    //private var counterList = listOf<StoppageEntity>(StoppageEntity(0, "Rxjava", 3, 2), StoppageEntity(0, "Rxjava", 3, 2))
    private var counterStoppageList = listOf<StoppageEntity>()
    @Inject lateinit var sharedPrefHelper: SharedPrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            requireActivity().finish()
        }
    }

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
                    when (uiState) {
                        is BusCounterListUiState.Success -> {
                            counterStoppageList = uiState.counterStoppageList
                            Timber.e("counterStoppageList $counterStoppageList")
                        }
                        is BusCounterListUiState.Error -> {
                            Toast.makeText(requireActivity(),uiState.errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        }

        setContent {
            OfflineCounterTicketingTheme {
                Surface( modifier = Modifier.fillMaxSize()) {
                    Scaffold(topBar = {
                        ToolbarWithButtonLarge(toolbarTitle = sharedPrefHelper.getString(SpKey.companyName),
                            toolbarIcon =Icons.Filled.ArrowBack ) {
                            requireActivity().finish()
                        }
                    }) {
                        HomeScreen(
                            counterList = counterStoppageList,
                            syncClickedCallBack = {
                                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
                            },
                            busCounterClickedCallback = {
                                Toast.makeText(requireActivity(),"Hello World- Clicked", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }


                }

            }
        }

    }


}