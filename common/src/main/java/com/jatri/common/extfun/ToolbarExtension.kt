package com.jatri.common.extfun

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.jatri.common.R

fun AppCompatActivity.toolBarWithoutBackBtn(toolbar: Toolbar, title:String){
    setSupportActionBar(toolbar)
    supportActionBar?.title = title
}
fun AppCompatActivity.toolBarSubTitleWithoutBackBtn(toolbar: Toolbar,title:String,subTitle:String){
    setSupportActionBar(toolbar)
    supportActionBar?.title = title
    supportActionBar?.subtitle = subTitle
}

fun AppCompatActivity.toolBarWithBackBtn(toolbar: Toolbar,title:String){
    setSupportActionBar(toolbar)
    supportActionBar?.title = title
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
}


