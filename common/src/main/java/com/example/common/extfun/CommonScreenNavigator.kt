package com.example.common.extfun

import android.app.Activity
import android.content.Intent
import com.example.common.constant.IntentKey

fun Activity.navigateLoginActivity() {
    startActivity(
        Intent().setClassName(this, "")
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    )
    finish()
}

fun Activity.navigateWebViewActivity(title: String, url: String) {
    startActivity(
        Intent().setClassName(this, "com.jatri.myapplication.WebViewActivity")
            .putExtra(IntentKey.WEB_VIEW_TOOLBAR_TITLE, title)
            .putExtra(IntentKey.WEB_VIEW_URL, url)
    )
}

fun Activity.navigateRentalNavHostActivity(intentKey: String? = "", intentValue: Int? = -1) {
    startActivity(
        Intent().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            .setClassName(
                this,
                "com.jatri.jatriuser.featrental.presenter.navhost.RentalMainNavHostActivity"
            )
            .putExtra(intentKey, intentValue)
    )
    finish()
}

fun Activity.navigateBtPaymentHostActivity() {
    startActivity(Intent().setClassName(this, "com.jatri.jatriuser.host.BTPaymentHostActivity"))
    finish()
}

fun Activity.navigateBusTicketHistoryHostActivity() {
    startActivity(
        Intent().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            .setClassName(this, "com.jatri.jatriuser.host.BTTicketHistoryHostActivity")
    )
    finish()
}

fun Activity.navigateBusTicketHomeHostActivity() {
    startActivity(
        Intent().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            .setClassName(this, "com.jatri.jatriuser.host.BTHostActivity")
    )
    finish()
}

