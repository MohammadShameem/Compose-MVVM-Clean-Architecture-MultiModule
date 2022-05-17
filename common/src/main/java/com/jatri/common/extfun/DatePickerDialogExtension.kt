package com.jatri.common.extfun

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.jatri.common.dateparser.DateTimeFormat
import com.jatri.common.dateparser.DateTimeParser
import java.lang.Exception
import java.util.*


fun AppCompatActivity.showMaterialTimePicker(
    givenTime:String="",
    title:String = "",
    pickedTimeCallback:(pickedTime:String)->Unit
){
    val picker = MaterialTimePicker.Builder()
        .setTimeFormat(TimeFormat.CLOCK_12H)
        .apply {
            try {
                val currentTime =
                    if (givenTime.isEmpty())
                        DateTimeParser.getCurrentDeviceDateTime(DateTimeFormat.sqlhm).split(":")
                    else
                        givenTime.split(":")
                this.setHour(currentTime[0].toInt())
                this.setMinute(currentTime[1].toInt())
            }catch (e:Exception){ }
        }
        .setTitleText(title)
        .build()
    picker.show(supportFragmentManager,"MATERIAL_DATE_TIME_PICKER")
    picker.addOnPositiveButtonClickListener {
        pickedTimeCallback.invoke(DateTimeParser.convertReadableDateTime(
            givenDateTime = "${picker.hour}:${picker.minute}",
            dateFormat = DateTimeFormat.sqlhm,
            outputFormat = DateTimeFormat.outputHMA
        ))

    }
}


fun Fragment.showMaterialTimePicker(
    givenTime:String="",
    title:String = "",
    pickedTimeCallback:(pickedTime:String)->Unit
){
    val picker = MaterialTimePicker.Builder()
        .setTimeFormat(TimeFormat.CLOCK_12H)
        .apply {
            try {
                val currentTime =
                    if (givenTime.isEmpty())
                        DateTimeParser.getCurrentDeviceDateTime(DateTimeFormat.sqlhm).split(":")
                    else
                        givenTime.split(":")
                this.setHour(currentTime[0].toInt())
                this.setMinute(currentTime[1].toInt())
            }catch (e:Exception){ }
        }
        .setTitleText(title)
        .build()
    picker.show(childFragmentManager,"MATERIAL_DATE_TIME_PICKER")
    picker.addOnPositiveButtonClickListener {
        pickedTimeCallback.invoke(DateTimeParser.convertReadableDateTime(
            givenDateTime = "${picker.hour}:${picker.minute}",
            dateFormat = DateTimeFormat.sqlhm,
            outputFormat = DateTimeFormat.outputHMA
        ))

    }
}



fun Activity.showCurrentDatePickerDialogWithDateLimit(maxDay:Int,callback:(pickDate:String)->Unit ){
    val fromCalender = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(this, { _, year, month, day ->
        callback.invoke("$year-${String.format("%02d", month+1)}-${String.format("%02d", day)}")
    }, fromCalender.get(Calendar.YEAR),  fromCalender.get(Calendar.MONTH), fromCalender.get(Calendar.DAY_OF_MONTH))
    val datePicker = datePickerDialog.datePicker
    datePicker.minDate = fromCalender.timeInMillis

    val toCalender = Calendar.getInstance()
    toCalender.set(fromCalender.get(Calendar.YEAR),fromCalender.get(Calendar.MONTH),fromCalender.get(Calendar.DAY_OF_MONTH)+maxDay)
    datePicker.maxDate = toCalender.timeInMillis
    datePickerDialog.show()

}

fun Activity.showDatePickerDialog(year:Int,month:Int,day:Int,callback:(pickDate:String)->Unit){
    val calendar = Calendar.getInstance()
    calendar.set(year,month,day)
    val datePicker = DatePickerDialog(this, { _, selectedYear, monthOfYear, dayOfMonth ->
            callback.invoke("$selectedYear-${String.format("%02d", monthOfYear+1)}-${String.format("%02d", dayOfMonth)}")
        }, calendar.get(Calendar.YEAR),  calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
    datePicker.datePicker.maxDate = System.currentTimeMillis()
    datePicker.show()
}

fun Activity.showTimePickerDialog(callback:(pickTime:String)->Unit){
    val cal = Calendar.getInstance()
    TimePickerDialog(this, {_, hour, minute ->
        callback.invoke("$hour:$minute:00")
    }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show()
}


