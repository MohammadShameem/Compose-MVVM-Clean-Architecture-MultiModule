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
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.jatri.common.dateparser.DateTimeFormat
import com.jatri.common.dateparser.DateTimeParser
import com.jatri.common.extfun.showAlertDialog
import com.jatri.domain.entity.StoppageEntity
import com.jatri.offlinecounterticketing.R
import com.jatri.offlinecounterticketing.ui.components.AlertDialogTicketPrint
import com.jatri.offlinecounterticketing.ui.components.AlertDialog
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
                positiveBtn = getString(R.string.btn_text_confirm),
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
                val isAlertDialogDialogOpenWithTitle = remember { mutableStateOf(false) }
                AlertDialog(
                    titleText = getString(R.string.title_are_you_sure),
                    messageText = getString(R.string.msg_exit),
                    isAlertDialogOpen = isAlertDialogDialogOpenWithTitle
                ) {
                    requireActivity().finish()
                }

                Surface(modifier = Modifier.fillMaxSize()) {
                    val unSyncTicketCountState by viewModel.unSyncTicketCountState.collectAsState()
                    Scaffold(topBar = {
                        ToolbarWithButtonLargeWithMenu(
                            toolbarTitle = sharedPrefHelper.getString(SpKey.companyName),
                            toolbarIcon = Icons.Filled.ArrowBack,
                            onBackButtonPressed = {
                                isAlertDialogDialogOpenWithTitle.value = true
                            },
                            onMenuDashboardClicked = {
                                if(unSyncTicketCountState > 0){
                                   Toast.makeText(requireContext(),requireActivity().getString(R.string.msg_print_report),Toast.LENGTH_LONG).show()
                                    return@ToolbarWithButtonLargeWithMenu
                                }
                                findNavController().navigate(
                                    HomeFragmentDirections.actionHomeFragmentToSecretPasswordFragment(
                                        true
                                    )
                                )
                            }
                        )

                    }) {
                        val itemStoppageEntity: MutableState<StoppageEntity> = remember { mutableStateOf(StoppageEntity(0,"",0,0)) }
                        val itemStudentFare = remember { mutableStateOf(false) }

                        val isAlertDialogDialogOpenWithOutTitle = remember { mutableStateOf(false) }

                        AlertDialogTicketPrint(
                            messageText = stringResource(R.string.ticket_info,itemStoppageEntity.value.name,itemStoppageEntity.value.fare),
                            isAlertDialogOpen = isAlertDialogDialogOpenWithOutTitle
                        ) {
                            onBusCounterItemClick(itemStoppageEntity.value, itemStudentFare.value)
                        }

                        HomeScreen(
                            sharedPrefHelper.getBoolean(SpKey.studentFare),
                            syncClickedCallBack = {
                                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
                            },
                            busCounterClickedCallback = { stoppage, studentFare ->
                                itemStoppageEntity.value = stoppage
                                itemStudentFare.value = studentFare
                                isAlertDialogDialogOpenWithOutTitle.value = true
                            }
                        )
                    }
                }
            }
        }
    }

    private fun onBusCounterItemClick(stoppage: StoppageEntity, studentFare: Boolean) {
        lifecycleScope.launch {
            val ticketFormatEntity = viewModel.getTicketFormatEntity()
            viewModel.printAndInsertTicket(
                stoppage,
                ticketFormatEntity,
                studentFare
            )
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
