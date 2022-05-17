package com.jatri.offlinecounterticketing.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.jatri.domain.usecase.login.LoginApiUseCase
import com.jatri.entity.res.ApiResponse
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
                  viewModel.login(params = LoginApiUseCase.Params(
                      phoneNumber, password
                  )).observe(viewLifecycleOwner){
                      when(it){
                          is ApiResponse.Success ->  findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                      }
                   /* if (it is ApiResponse.Success){
                        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                    }*/
                  }
              }
            }
        }
    }
}