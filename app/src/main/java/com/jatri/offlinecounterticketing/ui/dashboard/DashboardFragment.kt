package com.jatri.offlinecounterticketing.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.jatri.offlinecounterticketing.ui.components.ToolbarWithButtonLarge
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jatri.domain.usecase.dashboard.ChangePasswordApiUseCase
import com.jatri.entity.res.ApiResponse
import com.jatri.offlinecounterticketing.R
import com.jatri.offlinecounterticketing.ui.theme.OfflineCounterTicketingTheme
import com.jatri.sharedpref.SharedPrefHelper
import com.jatri.sharedpref.SpKey
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    @Inject
    lateinit var sharedPrefHelper: SharedPrefHelper

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
                        findNavController().navigate(R.id.loginFragment)
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