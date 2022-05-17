package com.jatri.offlinecounterticketing.ui.host


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.jatri.offlinecounterticketing.R
import com.jatri.offlinecounterticketing.databinding.ActivityNavHostBinding
import com.jatri.sharedpref.SharedPrefHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NavHostActivity : AppCompatActivity() {
    @Inject
    lateinit var sharedPrefHelper : SharedPrefHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            ComposeView(this).apply {
                setContent {
                    CompositionLocalProvider(
                        LocalBackPressedDispatcher provides this@NavHostActivity.onBackPressedDispatcher
                    ) {
                        Scaffold {
                            AndroidViewBinding(ActivityNavHostBinding::inflate)
                        }
                    }
                }
            }
        )
    }
    private fun findNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }
}