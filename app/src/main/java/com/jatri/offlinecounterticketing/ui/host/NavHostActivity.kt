package com.jatri.offlinecounterticketing.ui.host


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.jatri.offlinecounterticketing.databinding.ActivityNavHostBinding
import com.jatri.offlinecounterticketing.ui.LocalBackPressedDispatcher


class NavHostActivity : AppCompatActivity() {

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
/*
    private fun findNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }*/
}