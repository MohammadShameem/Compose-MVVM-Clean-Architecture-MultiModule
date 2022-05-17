package com.jatri.offlinecounterticketing.ui.configuration

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.jatri.entity.companylist.OfflineCompanyListEntity
import com.jatri.offlinecounterticketing.ui.theme.OfflineCounterTicketingTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ConfigurationFragment : Fragment() {
    @Inject
    lateinit var gson: Gson
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            requireActivity().finish()
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)
        /**
         * Get navigation arguments
         * */
        val args : ConfigurationFragmentArgs by navArgs()
        val companyList = gson.fromJson(args.companyListJsonString, OfflineCompanyListEntity::class.java)

        Timber.d(args.companyListJsonString)

        setContent {
            OfflineCounterTicketingTheme {
                Configuration(companyList.offline_company_list)
            }
        }
    }
}