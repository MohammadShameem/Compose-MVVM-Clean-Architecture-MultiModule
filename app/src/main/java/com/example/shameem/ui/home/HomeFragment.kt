package com.example.shameem.ui.home

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
import com.example.common.dateparser.DateTimeFormat
import com.example.common.dateparser.DateTimeParser
import com.example.common.extfun.showAlertDialog
import com.example.entity.cachentity.StoppageEntity
import com.example.shameem.ui.components.AlertDialogTicketPrint
import com.example.shameem.ui.components.AlertDialog
import com.example.shameem.ui.components.ToolbarWithButtonLargeWithMenu
import com.example.shameem.ui.theme.OfflineCounterTicketingTheme
import com.example.sharedpref.SharedPrefHelper
import com.example.sharedpref.SpKey
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
                            toolbarTitle = sharedPrefHelper.getString(SpKey.counterName),
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
                            },
                            onMenuAboutClicked = {
                                findNavController().navigate(R.id.aboutFragment)
                            }
                        )

                    }) {
                        val isConfigStudentFare = sharedPrefHelper.getBoolean(SpKey.studentFare)
                        val itemStoppageEntity: MutableState<StoppageEntity> = remember { mutableStateOf(
                            StoppageEntity(0,"",0,0)
                        ) }
                        val itemStudentFare = remember { mutableStateOf(false) }

                        val isAlertDialogDialogOpenWithOutTitle = remember { mutableStateOf(false) }

                        AlertDialogTicketPrint(
                            messageText = stringResource(R.string.ticket_info,itemStoppageEntity.value.name,
                                if (isConfigStudentFare&&itemStudentFare.value)itemStoppageEntity.value.fare_student else itemStoppageEntity.value.fare ),
                            isAlertDialogOpen = isAlertDialogDialogOpenWithOutTitle
                        ) {
                            onBusCounterItemClick(itemStoppageEntity.value,isConfigStudentFare,itemStudentFare.value)
                        }

                        HomeScreen(
                            isConfigStudentFare,
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

    private fun onBusCounterItemClick(stoppage: StoppageEntity, isConfigStudentFareEnable: Boolean, studentFare: Boolean) {
        lifecycleScope.launch {
            val ticketFormatEntity = viewModel.getTicketFormatEntity()
            viewModel.printAndInsertTicket(
                stoppage,
                ticketFormatEntity,
                isConfigStudentFareEnable,
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
