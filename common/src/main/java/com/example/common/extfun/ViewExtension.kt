package com.example.common.extfun

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.SystemClock
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.core.view.isVisible

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

