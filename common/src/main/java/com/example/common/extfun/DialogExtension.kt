package com.example.common.extfun

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment



fun Context.showOnlyPositiveButtonDialog(
    buttonText: String,
    message: String,
    cancelable: Boolean,
    positiveBtnCallback:(()->Unit)?,
): AlertDialog {
    return AlertDialog.Builder(this)
        .setCancelable(cancelable)
        .setMessage(message)
        .setPositiveButton(buttonText) { dialog, _ ->
            dialog.dismiss()
            positiveBtnCallback?.invoke()
        }.show()
}


fun Context.showAlertDialog(
    positiveBtn: String,
    negativeBtn: String,
    title: String?,
    message: String?,
    cancelable: Boolean,
    positiveBtnCallback:(()->Unit)?,
    negativeBtnCallback:(()->Unit)?,
) {
    AlertDialog.Builder(this)
        .setCancelable(cancelable)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(positiveBtn) { dialog, _ ->
            dialog.dismiss()
            positiveBtnCallback?.invoke()
        }.setNegativeButton(negativeBtn) { dialog, _ ->
            dialog.dismiss()
            negativeBtnCallback?.invoke()
        }.show()
}


fun Activity.showAlertDialogWithViewWithoutBtn(layoutId: Int, cancelable: Boolean): AlertDialog {
    val view = LayoutInflater.from(this).inflate(layoutId, null)
    return AlertDialog.Builder(this)
        .setView(view)
        .setCancelable(cancelable)
        .show()
}

fun Activity.showViewAlertDialog(view: View, title: String?, cancelable: Boolean): AlertDialog =
    AlertDialog.Builder(this)
        .setView(view)
        .setCancelable(cancelable)
        .setTitle(title)
        .show()

fun Fragment.showViewAlertDialog(view: View, title: String?, cancelable: Boolean): AlertDialog =
    AlertDialog.Builder(requireContext())
        .setView(view)
        .setCancelable(cancelable)
        .setTitle(title)
        .show()

fun Activity.showViewAlertDialogCustomTheme(
    view: View,
    theme: Int,
    cancelable: Boolean
): AlertDialog =
    AlertDialog.Builder(this, theme)
        .setView(view)
        .setCancelable(cancelable)
        .setTitle(title)
        .show()

fun Context.showViewAlertDialog(view: View, title: String?,theme:Int, cancelable: Boolean):AlertDialog =
    AlertDialog.Builder(this,theme)
        .setView(view)
        .setCancelable(cancelable)
        .setTitle(title)
        .show()

interface AlertDialogCallBack{
    fun onClickYes()
    fun onClickCancel()
}


