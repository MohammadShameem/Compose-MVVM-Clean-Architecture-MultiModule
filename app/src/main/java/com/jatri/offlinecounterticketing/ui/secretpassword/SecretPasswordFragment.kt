package com.jatri.offlinecounterticketing.ui.secretpassword

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jatri.offlinecounterticketing.R
import com.jatri.offlinecounterticketing.helper.loadJsonFromAsset
import com.jatri.offlinecounterticketing.ui.theme.OfflineCounterTicketingTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
                    jsonString
                )
            if (secretPassword == resources.getString(R.string.secretPassword)) {
                findNavController().navigate(action)
            }
        }

        setContent {
            OfflineCounterTicketingTheme {
                SecretPassword(navigateToConfiguration)
            }
        }
    }
}