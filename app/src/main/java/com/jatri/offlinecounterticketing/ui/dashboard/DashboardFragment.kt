package com.jatri.offlinecounterticketing.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jatri.offlinecounterticketing.R
import com.jatri.offlinecounterticketing.ui.components.ToolbarWithButtonLarge
import com.jatri.offlinecounterticketing.ui.theme.OfflineCounterTicketingTheme
import com.jatri.sharedpref.SharedPrefHelper
import com.jatri.sharedpref.SpKey
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    @Inject
    lateinit var sharedPrefHelper: SharedPrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToHomeFragment())
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
                Scaffold(topBar = {
                    ToolbarWithButtonLarge(
                        toolbarTitle = context.getString(R.string.title_dashboard),
                        toolbarIcon = Icons.Filled.ArrowBack
                    ) {
                        findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToHomeFragment())
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