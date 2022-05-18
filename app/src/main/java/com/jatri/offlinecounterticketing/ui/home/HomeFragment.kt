package com.jatri.offlinecounterticketing.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.jatri.domain.entity.StoppageEntity
import com.jatri.offlinecounterticketing.ui.theme.OfflineCounterTicketingTheme
import com.jatri.sharedpref.SharedPrefHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(){
    private val viewModel: HomeViewModel by viewModels()
    private var counterStoppageList = listOf<StoppageEntity>()
    @Inject lateinit var sharedPrefHelper: SharedPrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            requireActivity().finish()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View= ComposeView(inflater.context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)

        setContent {
            OfflineCounterTicketingTheme {
                Surface( modifier = Modifier.fillMaxSize()) {
                    HomeScreen(
                        syncClickedCallBack = {
                            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
                        },
                        busCounterClickedCallback = {
                            Toast.makeText(requireActivity(),"Hello World- Clicked", Toast.LENGTH_SHORT).show()
                        }
                    )
                }

            }
        }

    }


}