package com.jatri.common.extfun

import android.app.Activity
import android.content.Intent
import android.net.Uri

fun Activity.openBrowser(url:String){
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    startActivity(intent)
}