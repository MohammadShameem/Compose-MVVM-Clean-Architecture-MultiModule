package com.jatri.common.extfun

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.SystemClock
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.view.isVisible
import com.jatri.common.R
import com.jatri.common.constant.AppConstant
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.util.regex.Pattern

fun EditText.getTextFromEt():String = this.text.toString().trim()
fun AutoCompleteTextView.getTextFromAt():String = this.text.toString().trim()
fun TextView.getTextFromTv():String = this.text.toString().trim()

fun EditText.isEmptyInput(errorMessage:String):Boolean{
    val input = this.text.toString().trim()
    if (input.isEmpty()) this.error = errorMessage
    return input.isEmpty()
}

fun TextView.isEmpty(text: String, errorTV: TextView): Boolean {
    errorTV.isVisible = text.isBlank()
    //errorTV.text = errorText
    return text.isBlank()
}

fun View.hideKeyboard() {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun Context.showKeyboard(){
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
}


fun Activity.starPhoneCallDial(phoneNumber:String?){
    phoneNumber?.let {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$it")
        startActivity(intent)
    }
}

fun View.clickWithDebounce(debounceTime: Long =1200L, action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0
        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action()
            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}


fun EditText.isEmailValid(email: CharSequence): Boolean {
    return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

/*fun ImageView.loadImage(url : String)
{
    Picasso.get().load(url).placeholder(R.drawable.ic_image_placeholder)
        .into(this)
}

fun CircleImageView.loadImage(url : String)
{
    Picasso.get().load(url).placeholder(R.drawable.ic_avatar_placeholder)
        .into(this)
}*/

