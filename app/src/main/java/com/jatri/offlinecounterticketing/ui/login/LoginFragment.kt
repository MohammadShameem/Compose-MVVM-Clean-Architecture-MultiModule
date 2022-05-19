package com.jatri.offlinecounterticketing.ui.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jatri.domain.usecase.login.LoginApiUseCase
import com.jatri.entity.res.ApiResponse
import com.jatri.offlinecounterticketing.R
import com.jatri.offlinecounterticketing.ui.components.ToolbarWithButtonLarge
import com.jatri.offlinecounterticketing.ui.dashboard.Dashboard
import com.jatri.offlinecounterticketing.ui.theme.OfflineCounterTicketingTheme
import com.jatri.sharedpref.SharedPrefHelper
import com.jatri.sharedpref.SpKey
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    @Inject
    lateinit var sharedPrefHelper: SharedPrefHelper
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)

        setContent {
            OfflineCounterTicketingTheme {
                Scaffold(topBar = {
                    ToolbarWithButtonLarge(
                        toolbarTitle = context.getString(R.string.title_login),
                        toolbarIcon = Icons.Filled.ArrowBack
                    ) {
                        findNavController().navigate(R.id.homeFragment)
                    }
                }) {
                    LoginScreen{ phoneNumber,password ->
                        login(phoneNumber,password)
                    }
                }

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.errorMessageLiveDataOfValidation.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(),getString(it),Toast.LENGTH_SHORT).show()
        }
    }

   /**
    * Login user to system
   * */
    private fun login(phoneNumber: String, password: String) {
        getDeviceId()
        val validationMessage = viewModel.validatePhoneNumberAndPassword(phoneNumber,password)
        if(validationMessage){
            viewModel.login(params = LoginApiUseCase.Params(
                phoneNumber, password
            )).observe(viewLifecycleOwner){
                if (it is ApiResponse.Success) {
                    sharedPrefHelper.putString(SpKey.userName,it.data.name)
                    sharedPrefHelper.putString(SpKey.phoneNumber,it.data.mobile)
                    sharedPrefHelper.putString(SpKey.userAuthKey,it.data.token)

                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToDashboardFragment())
                    Toast.makeText(requireContext(),getString(R.string.msg_login_success),Toast.LENGTH_LONG).show()
                }
                else if (it is ApiResponse.Failure) Toast.makeText(requireContext(),it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Get Device id and save to shared Preference
     * */
    @SuppressLint("HardwareIds")
    private fun getDeviceId() {
        try {
            val androidId = Settings.Secure.getString(requireActivity().contentResolver, Settings.Secure.ANDROID_ID)
            sharedPrefHelper.putString(SpKey.deviceId, androidId?:"")
        } catch (e: Exception) {
            sharedPrefHelper.putString(SpKey.deviceId, sharedPrefHelper.getString(SpKey.deviceId))
        }

    }
}