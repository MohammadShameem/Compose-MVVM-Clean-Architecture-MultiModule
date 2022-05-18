package com.jatri.offlinecounterticketing.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.jatri.offlinecounterticketing.ui.components.ToolbarWithButtonLarge
import androidx.fragment.app.viewModels
import com.jatri.domain.usecase.dashboard.ChangePasswordApiUseCase
import com.jatri.entity.res.ApiResponse
import com.jatri.offlinecounterticketing.R
import com.jatri.offlinecounterticketing.ui.theme.OfflineCounterTicketingTheme
import com.jatri.sharedpref.SharedPrefHelper
import com.jatri.sharedpref.SpKey
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    @Inject
    lateinit var sharedPrefHelper: SharedPrefHelper

    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)
        setContent {
            OfflineCounterTicketingTheme {
                Scaffold(topBar = { ToolbarWithButtonLarge(toolbarTitle = "Dashboard", toolbarIcon = Icons.Filled.ArrowBack) {

                }}) {
                    Dashboard(sharedPrefHelper.getString(SpKey.userName), sharedPrefHelper.getString(SpKey.phoneNumber),
                        changePasswordCallBack = { oldPassword, newPassword ->
                            changePassword(oldPassword,newPassword)
                        })
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.errorMessageLiveDataOfValidation.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(),getString(it), Toast.LENGTH_SHORT).show()
        }
    }

    private fun changePassword(oldPassword: String, newPassword: String) {
       val isValid = viewModel.validateOldPasswordAndNewPassword(oldPassword,newPassword)
        if(isValid){
            viewModel.changePassword(ChangePasswordApiUseCase.Params(oldPassword,newPassword))
                .observe(viewLifecycleOwner){
                    if (it is ApiResponse.Success) {

                        Toast.makeText(requireContext(),getString(R.string.msg_success_update_password),Toast.LENGTH_LONG).show()
                    }
                    else if (it is ApiResponse.Failure) Toast.makeText(requireContext(),it.message, Toast.LENGTH_SHORT).show()
                }
        }
    }
}