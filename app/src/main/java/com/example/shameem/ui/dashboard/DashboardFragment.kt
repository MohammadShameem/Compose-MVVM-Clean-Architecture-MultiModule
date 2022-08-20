package com.example.shameem.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.common.extfun.showAlertDialog
import com.example.shameem.R
import com.example.shameem.ui.components.AlertDialog
import com.example.shameem.ui.components.ToolbarWithButtonLarge
import com.example.shameem.ui.theme.OfflineCounterTicketingTheme
import com.example.sharedpref.SharedPrefHelper
import com.example.sharedpref.SpKey
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    @Inject
    lateinit var sharedPrefHelper: SharedPrefHelper


    /**
     * This will be executed at the time of our system backPressClick
     * By clicking conform button it proms to navigate you to home screen
     * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            requireActivity().showAlertDialog(
                positiveBtn = getString(R.string.btn_text_confirm),
                negativeBtn = getString(R.string.btn_text_cancel),
                title = getString(R.string.title_are_you_sure),
                message = getString(R.string.msg_back_to_home),
                cancelable = true,
                positiveBtnCallback = {
                    findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToHomeFragment())
                },
                negativeBtnCallback = {}
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        setContent {
            OfflineCounterTicketingTheme {
                /**
                 * This dialog will be shown at the time of appBar back button click
                 * */
                val isAlertDialogDialogOpen = remember { mutableStateOf(false) }
                AlertDialog(
                    titleText = getString(R.string.title_are_you_sure),
                    messageText = getString(R.string.msg_back_to_home),
                    isAlertDialogOpen = isAlertDialogDialogOpen
                ) {
                    findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToHomeFragment())
                }
                Scaffold(topBar = {
                    ToolbarWithButtonLarge(
                        toolbarTitle = context.getString(R.string.title_dashboard),
                        toolbarIcon = Icons.Filled.ArrowBack
                    ) {
                        isAlertDialogDialogOpen.value = true
                    }
                }) {
                    Dashboard(
                        sharedPrefHelper.getString(SpKey.userName),
                        sharedPrefHelper.getString(SpKey.phoneNumber)
                    )
                }
            }
        }
    }
}