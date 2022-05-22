package com.jatri.offlinecounterticketing.ui.secretpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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
    private lateinit var offlineCompaniesJsonString: String
    private val args: SecretPasswordFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * Navigate to Home Screen if configuration was completed before.
         * But if the user wants to re configure the app,
         * then the user must open Secret Password Screen from "Home Screen" and $args.cameFromHome will be true
         * so the app will not navigate automatically to Home screen again even it was configured before
         * */
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if(args.cameFromHomeFragment){
                findNavController().navigate(R.id.homeFragment)
            }
            else {
                requireActivity().finish()
            }
        }
        if (!args.cameFromHomeFragment && sharedPrefHelper.getBoolean(SpKey.configured)) {
            findNavController().navigate(SecretPasswordFragmentDirections.actionSecretPasswordFragmentToHomeFragment())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        /**
         * Load offline_companies.json file from assets module
         * and store as a string
         * */
        lifecycleScope.launch {
            requireContext().loadJsonFromAsset("offline_companies.json").onSuccess {
                offlineCompaniesJsonString = it
            }
        }

        setContent {
            OfflineCounterTicketingTheme {
                SecretPassword { secretPassword: String ->
                    /**
                     * If the secret password matches with our predefined password,
                     * navigate to Configuration Fragment and pass the json string of all companies
                     * */
                    if (secretPassword == resources.getString(R.string.secretPassword)) {
                        findNavController().navigate(
                            SecretPasswordFragmentDirections.actionSecretPasswordFragmentToConfigurationFragment(
                                offlineCompaniesJsonString
                            )
                        )
                        sharedPrefHelper.putBool(SpKey.passwordEntered, true)
                    } else {
                        /**
                         * Show a Toast Message if the entered password is wrong
                         * */
                        Toast.makeText(
                            requireContext(),
                            context.getString(R.string.msg_wrong_password),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}