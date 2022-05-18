package com.jatri.offlinecounterticketing.ui.secretpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jatri.offlinecounterticketing.R
import com.jatri.offlinecounterticketing.helper.loadJsonFromAsset
import com.jatri.offlinecounterticketing.ui.theme.OfflineCounterTicketingTheme
import com.jatri.sharedpref.SharedPrefHelper
import com.jatri.sharedpref.SpKey
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SecretPasswordFragment : Fragment() {
    @Inject
    lateinit var sharedPrefHelper: SharedPrefHelper
    private lateinit var jsonString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //findNavController().navigate(R.id.homeFragment)
    }

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
            if (secretPassword == resources.getString(R.string.secretPassword)) {
                findNavController().navigate(SecretPasswordFragmentDirections.actionSecretPasswordFragmentToConfigurationFragment(jsonString))
                sharedPrefHelper.putBool(SpKey.passwordEntered, true)
            }
        }
        setContent {
            OfflineCounterTicketingTheme {
                SecretPassword(navigateToConfiguration)
            }
        }
    }
}