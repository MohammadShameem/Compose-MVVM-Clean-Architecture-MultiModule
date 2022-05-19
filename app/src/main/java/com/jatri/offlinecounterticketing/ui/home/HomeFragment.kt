package com.jatri.offlinecounterticketing.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.jatri.common.dateparser.DateTimeFormat
import com.jatri.common.dateparser.DateTimeParser
import com.jatri.offlinecounterticketing.ui.components.ToolbarWithButtonLarge
import com.jatri.offlinecounterticketing.ui.theme.OfflineCounterTicketingTheme
import com.jatri.sharedpref.SharedPrefHelper
import com.jatri.sharedpref.SpKey
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(){
    @Inject lateinit var sharedPrefHelper: SharedPrefHelper
    private val viewModel: HomeViewModel by viewModels()

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

        setContent {
            OfflineCounterTicketingTheme {
                Surface( modifier = Modifier.fillMaxSize()) {
                    Scaffold(topBar = {
                        ToolbarWithButtonLarge(toolbarTitle = sharedPrefHelper.getString(SpKey.companyName),
                            toolbarIcon = Icons.Filled.ArrowBack) {
                            requireActivity().finish()
                        }
                    }) {
                        HomeScreen(
                            sharedPrefHelper.getBoolean(SpKey.studentFare),
                            syncClickedCallBack = {
                                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
                            },
                            busCounterClickedCallback = { stoppage, studentFare ->
                                lifecycleScope.launch {
                                    val ticketFormatEntity = viewModel.getTicketFormatEntity()
                                    viewModel.printAndInsertTicket(stoppage, studentFare,ticketFormatEntity)
                                }

                            }
                        )
                    }

                }

            }
        }
    }


    override fun onResume() {
        super.onResume()
        if (sharedPrefHelper.getString(SpKey.soldTicketSerialCurrentDate) !=
            DateTimeParser.getCurrentDeviceDateTime(DateTimeFormat.outputDMY)){
            sharedPrefHelper.putString(SpKey.soldTicketSerialCurrentDate,
                DateTimeParser.getCurrentDeviceDateTime(DateTimeFormat.outputDMY))
            sharedPrefHelper.putInt(SpKey.soldTicketSerial,0)
        }
    }
}