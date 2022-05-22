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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.jatri.common.dateparser.DateTimeFormat
import com.jatri.common.dateparser.DateTimeParser
import com.jatri.common.extfun.showAlertDialog
import com.jatri.offlinecounterticketing.R
import com.jatri.offlinecounterticketing.ui.components.BackPressAlertDialog
import com.jatri.offlinecounterticketing.R
import com.jatri.offlinecounterticketing.ui.components.ToolbarWithButtonLarge
import com.jatri.offlinecounterticketing.ui.dashboard.DashboardFragmentDirections
import com.jatri.offlinecounterticketing.ui.components.ToolbarWithButtonLargeWithMenu
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
            requireActivity().showAlertDialog(
                positiveBtn = getString(R.string.btn_text_conform),
                negativeBtn = getString(R.string.btn_text_cancel),
                title = getString(R.string.title_are_you_sure),
                message = getString(R.string.msg_exit),
                cancelable = true,
                positiveBtnCallback = {
                    requireActivity().finish()
                },
                negativeBtnCallback = {}
            )
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
                val isAlertDialogDialogOpen = remember { mutableStateOf(false) }
                BackPressAlertDialog(
                    titleText = getString(R.string.title_are_you_sure),
                    messageText = getString(R.string.msg_exit),
                    isAlertDialogOpen = isAlertDialogDialogOpen,
                    navigateTo = {
                        requireActivity().finish()
                    })


                Surface(modifier = Modifier.fillMaxSize()) {
                    Scaffold(topBar = {
                        ToolbarWithButtonLarge(toolbarTitle = sharedPrefHelper.getString(SpKey.companyName),
                            toolbarIcon = Icons.Filled.ArrowBack) {
                            isAlertDialogDialogOpen.value = true
                        }
                        ToolbarWithButtonLargeWithMenu(
                            toolbarTitle = sharedPrefHelper.getString(SpKey.companyName),
                            toolbarIcon = Icons.Filled.ArrowBack,
                            onBackButtonPressed = {
                                requireActivity().finish()
                            },
                            onMenuDashboardClicked = {
                                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSecretPasswordFragment(true))
                            }
                        )

                    }) {
                        HomeScreen(
                            sharedPrefHelper.getBoolean(SpKey.studentFare),
                            syncClickedCallBack = {
                                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
                            },
                            busCounterClickedCallback = { stoppage, studentFare ->
                                lifecycleScope.launch {
                                    val ticketFormatEntity = viewModel.getTicketFormatEntity()
                                    viewModel.printAndInsertTicket(
                                        stoppage,
                                        ticketFormatEntity,
                                        studentFare
                                    )
                                }
                            }
                        )
                    }
                }

        }
    }


    override fun onResume() {
        super.onResume()
        if (sharedPrefHelper.getString(SpKey.soldTicketSerialCurrentDate) !=
            DateTimeParser.getCurrentDeviceDateTime(DateTimeFormat.outputDMY)
        ) {
            sharedPrefHelper.putString(
                SpKey.soldTicketSerialCurrentDate,
                DateTimeParser.getCurrentDeviceDateTime(DateTimeFormat.outputDMY)
            )
            sharedPrefHelper.putInt(SpKey.soldTicketSerial, 0)
        }
    }
}