package com.jatri.offlinecounterticketing.ui.configuration

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import com.google.gson.Gson
import com.jatri.entity.companylist.OfflineCompanyListEntity
import com.jatri.offlinecounterticketing.R
import com.jatri.offlinecounterticketing.helper.loadJsonFromAsset
import com.jatri.offlinecounterticketing.ui.components.JatriLogo
import com.jatri.offlinecounterticketing.ui.components.RoundJatriButton
import com.jatri.offlinecounterticketing.ui.theme.OfflineCounterTicketingTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SecretPasswordFragment : Fragment() {

    private lateinit var jsonString: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        lifecycleScope.launch {
            requireContext().loadJsonFromAsset("offline_companies.json").onSuccess {
                jsonString = it
            }
        }


        val navigateToConfiguration = { secretPassword: String ->
            val action =
                SecretPasswordFragmentDirections.actionSecretPasswordFragmentToConfigurationFragment(
                    secretPassword,
                    jsonString
                )
            findNavController().navigate(action)
        }

        setContent {
            OfflineCounterTicketingTheme {
                SecretPassword(navigateToConfiguration)
            }
        }
    }
}