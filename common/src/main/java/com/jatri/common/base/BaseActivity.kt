package com.jatri.common.base

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.splitcompat.SplitCompat
import com.jatri.common.constant.AppConstant
import com.jatri.sharedpref.SpKey
import java.util.*

abstract class BaseActivity<D:ViewBinding> : AppCompatActivity(){

    protected lateinit var binding:D
    protected var activityContext: Activity? = null
    protected abstract fun viewBindingLayout(): D
    protected abstract fun initializeView(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewBindingLayout()
        setContentView(binding.root)
        activityContext = this
        initializeView(savedInstanceState)
    }

    override fun attachBaseContext(base: Context?) {
        val configuration = Configuration()
        val languageTag = PreferenceManager.getDefaultSharedPreferences(base).getString(SpKey.appLanguage,
            AppConstant.appLanguageEn)
        configuration.setLocale(Locale.forLanguageTag(languageTag?: AppConstant.appLanguageEn))
        val context = base?.createConfigurationContext(configuration)
        super.attachBaseContext(context)
        SplitCompat.install(this)
    }

    protected fun showMessage(message:String?){
        Snackbar.make(findViewById(android.R.id.content),""+message, Snackbar.LENGTH_LONG).show()
    }

    protected fun showToastMessage(message:String?){
        Toast.makeText(applicationContext,message, Toast.LENGTH_SHORT).show()
    }

    protected fun showProgressBar(isLoading: Boolean,view: View){
        if (isLoading) {
            view.visibility = View.VISIBLE
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        } else {
            view.visibility = View.GONE
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }


    override fun onDestroy() {
        super.onDestroy()
        activityContext = null
        //finish()
    }
}