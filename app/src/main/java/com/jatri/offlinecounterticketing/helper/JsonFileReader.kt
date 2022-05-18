package com.jatri.offlinecounterticketing.helper

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun Context.loadJsonFromAsset(name:String):Result<String>{
    return withContext(Dispatchers.IO){
        runCatching {
            var jsonString = ""
            try {
                val inputStream = this@loadJsonFromAsset.assets.open(name)
                jsonString = inputStream.bufferedReader().use {
                    it.readText()
                }
                inputStream.close()
            }catch (e:Exception){

            }
            jsonString
        }
    }
}

