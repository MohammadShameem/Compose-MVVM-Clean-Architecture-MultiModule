package com.example.shameem.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.shameem.BuildConfig
import com.example.shameem.R
import com.example.shameem.ui.components.ToolbarWithButtonLarge
import com.example.shameem.ui.theme.OfflineCounterTicketingTheme
import com.example.sharedpref.SharedPrefHelper
import com.example.sharedpref.SpKey
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AboutFragment : Fragment() {
    @Inject
    lateinit var sharedPrefHelper: SharedPrefHelper
    private var appName = ""
    private var version = BuildConfig.VERSION_NAME
    private var type = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        appName = sharedPrefHelper.getString(SpKey.companyName)
        type = if (BuildConfig.DEBUG) getString(R.string.app_type_dev) else getString(R.string.app_type_live)


        setContent {
            OfflineCounterTicketingTheme {
                Scaffold(
                    topBar = {
                        ToolbarWithButtonLarge(
                            toolbarTitle = stringResource(id = R.string.about),
                            toolbarIcon = Icons.Filled.ArrowBack
                        ) {
                            findNavController().popBackStack()
                        }
                    }
                ) {

                    About(companyName = appName, version = version, type = type)

                }
            }
        }
    }
}