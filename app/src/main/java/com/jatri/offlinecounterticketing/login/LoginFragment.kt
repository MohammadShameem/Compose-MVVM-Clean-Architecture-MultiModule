package com.jatri.offlinecounterticketing.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jatri.domain.usecase.login.LoginApiUseCase
import com.jatri.entity.res.ApiResponse
import com.jatri.offlinecounterticketing.R
import com.jatri.offlinecounterticketing.ui.theme.OfflineCounterTicketingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

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
              LoginScreen{ phoneNumber,password ->
                  login(phoneNumber,password)
              }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.errorMessageLiveData.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(),getString(it),Toast.LENGTH_SHORT).show()
        }
    }

   /**
    * Login user to system
   * */
    private fun login(phoneNumber: String, password: String) {
        val validationMessage = viewModel.validatePhoneNumberAndPassword(phoneNumber,password)
        if(validationMessage){
            viewModel.login(params = LoginApiUseCase.Params(
                phoneNumber, password
            )).observe(viewLifecycleOwner){
                if (it is ApiResponse.Success) {
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                    Toast.makeText(requireContext(),getString(R.string.msg_login_success),Toast.LENGTH_LONG).show()
                }
                else if (it is ApiResponse.Failure) Toast.makeText(requireContext(),it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}