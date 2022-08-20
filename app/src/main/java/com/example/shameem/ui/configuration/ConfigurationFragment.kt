package com.example.shameem.ui.configuration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.example.entity.companylist.OfflineCompanyListEntity
import com.example.shameem.R
import com.example.shameem.ui.theme.OfflineCounterTicketingTheme
import com.example.sharedpref.SharedPrefHelper
import com.example.sharedpref.SpKey
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ConfigurationFragment : Fragment() {
    @Inject lateinit var sharedPrefHelper: SharedPrefHelper
    @Inject lateinit var gson: Gson
    private val args : ConfigurationFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if(sharedPrefHelper.getBoolean(SpKey.configured)){
                findNavController().navigate(R.id.homeFragment)
            }
            else {
                requireActivity().finish()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)

        val companyList = gson.fromJson(args.companyListJsonString, OfflineCompanyListEntity::class.java)
        setContent {
            OfflineCounterTicketingTheme {
                Configuration(companyList.offline_company_list){
                    sharedPrefHelper.putBool(SpKey.configured,true)
                    findNavController().navigate(R.id.homeFragment)
                }
            }
        }
    }
}