package com.jatri.common.dateparser

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateTimeParser {

    private const val SECOND_MILLIS = 1000
    private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private const val DAY_MILLIS = 24 * HOUR_MILLIS

    fun getCurrentDeviceDateTime(format:String):String{
        val c = Calendar.getInstance().time
        val df = SimpleDateFormat(format, Locale.US)
        return df.format(c)
    }


    fun convertDateFormatToMilliSeconds(dateFormat: String,dateTime:String): Long =
        SimpleDateFormat(dateFormat,Locale.US).parse(dateTime)?.time?:0L

    fun convertLongToReadableDateTime(time:Long,format:String):String{
        val df = SimpleDateFormat(format, Locale.US)
        return df.format(time)
    }


    fun convertReadableDateTime(givenDateTime:String?,dateFormat:String,outputFormat:String):String{
        var formatDate = ""
        var sf = SimpleDateFormat(dateFormat, Locale.US)
        try {
            givenDateTime?.let {
                val parseDate = sf.parse(it)
                sf = SimpleDateFormat(outputFormat, Locale.US)
                formatDate = sf.format(parseDate!!)
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return formatDate
    }

    fun checkCurrentTimeInsideGivenTimeRange(currentTime:String?, fromTime:String?, toTime:String?):Boolean{
        return try {
            val currentTimeCalender = Calendar.getInstance()
            currentTimeCalender.time = SimpleDateFormat(DateTimeFormat.sqlHMS,Locale.US).parse(currentTime)

            val fromTimeCalender = Calendar.getInstance()
            fromTimeCalender.time = SimpleDateFormat(DateTimeFormat.sqlHMS,Locale.US).parse(fromTime)

            val toTimeCalender = Calendar.getInstance()
            toTimeCalender.time = SimpleDateFormat(DateTimeFormat.sqlHMS,Locale.US).parse(toTime)

            (currentTimeCalender.time.after(fromTimeCalender.time) && currentTimeCalender.time.before(toTimeCalender.time))
        }catch (e: ParseException) {
            e.printStackTrace()
            false
        }
    }

    fun checkCurrentDateTimeInsideGivenDateTimeRange(currentDateTime:String?, fromDateTime:String?, toDateTime:String?):Boolean{
        return try {
            val currentDateTimeCalender = Calendar.getInstance()
            currentDateTimeCalender.time = SimpleDateFormat(DateTimeFormat.sqlYMDHMS,Locale.US).parse(currentDateTime)

            val fromDateTimeCalender = Calendar.getInstance()
            fromDateTimeCalender.time = SimpleDateFormat(DateTimeFormat.sqlYMDHMS,Locale.US).parse(fromDateTime)

            val toDateTimeCalender = Calendar.getInstance()
            toDateTimeCalender.time = SimpleDateFormat(DateTimeFormat.sqlYMDHMS,Locale.US).parse(toDateTime)
            (currentDateTimeCalender.time.after(fromDateTimeCalender.time) && currentDateTimeCalender.time.before(toDateTimeCalender.time))
        }catch (e: ParseException) {
            e.printStackTrace()
            false
        }
    }

    /**
     * ...current date and trip date validation
     * ...if trip date time is getter than current date time then return true
     * ...otherwise return false
     * @param currentDateTime received from spManager
     * @param tripDateTime selected trip date time by user
     */
    fun isTripDateIsGetterThanCurrentDateTime(currentDateTime : String, tripDateTime : String) : Boolean
    {
        return try {
            val currentDateTimeCalender = Calendar.getInstance()
            currentDateTimeCalender.time = SimpleDateFormat(DateTimeFormat.sqlYMDHM,Locale.US).parse(currentDateTime)
            currentDateTimeCalender.add(Calendar.HOUR,1)

            val tripDateTimeCalender = Calendar.getInstance()
            tripDateTimeCalender.time = SimpleDateFormat(DateTimeFormat.sqlYMDHM,Locale.US).parse(tripDateTime)

            Log.d("dateTimerInfo","${currentDateTimeCalender.time} and ${tripDateTimeCalender.time}")
            //currentDateTimeCalender.time.before(tripDateTimeCalender.time)
            currentDateTimeCalender.time <= tripDateTimeCalender.time
        }catch (e: ParseException) {
            e.printStackTrace()
            false
        }
    }

    /**
     * ...current date and trip date validation
     * ...if trip date time is getter than current date time then return true
     * ...otherwise return false
     * @param returnTripDate selected return trip date time
     * @param tripDateTime selected trip date time by user
     */
    fun isReturnTripDateIsGetterThanTripDate(tripDateTime : String,returnTripDate : String) : Boolean
    {
        return try {
            val tripDateCalender = Calendar.getInstance()
            tripDateCalender.time = SimpleDateFormat(DateTimeFormat.sqlYMDHMS,Locale.US).parse(tripDateTime)

            val returnDateTimeCalender = Calendar.getInstance()
            returnDateTimeCalender.time = SimpleDateFormat(DateTimeFormat.sqlYMDHMS,Locale.US).parse(returnTripDate)

            Log.d("dateTimerInfo","$returnTripDate and $tripDateTime")
            //tripDateCalender.time.before(returnDateTimeCalender.time)
            tripDateCalender.time < returnDateTimeCalender.time
        }catch (e: ParseException) {
            e.printStackTrace()
            false
        }
    }

   /* fun calculateRemainTime(scheduled_date: String?) :String{
        var diffence_in_minute: Long = 0
        // date format
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm")
        // two dates
        val scheduledDate: Date
        val current = Calendar.getInstance()
        val currentDate: Date
        val current_date = format.format(current.time)
        try {
            scheduledDate = format.parse(scheduled_date)
            currentDate = format.parse(current_date)
            val diffInMillies = scheduledDate.time - currentDate.time
             diffence_in_minute = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS)
            println(diffence_in_minute)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return diffence_in_minute.toString()
    }

    fun getTimeAfterFromDateTime(context: Context, dateTime:String, format:String):String{
        var time = SimpleDateFormat(format,Locale.US).parse(dateTime).time
        if (time<1000000000000L)
            time *= 1000
        val now = Calendar.getInstance().time.time
        if (time < now || time <= 0) return "আগে"
        val diff = time - now
        return when {
            diff < MINUTE_MILLIS -> context.resources.getString(R.string.offer_sometimes)
            diff < 2 * MINUTE_MILLIS -> context.resources.getString(R.string.offer_minute)
            diff < 60 * MINUTE_MILLIS -> context.resources.getString(R.string.offer_minutes, diff / MINUTE_MILLIS)
            diff < 2 * HOUR_MILLIS -> context.resources.getString(R.string.offer_one_hour)
            diff < 24 * HOUR_MILLIS -> context.resources.getString(R.string.offer_hour, diff / HOUR_MILLIS)
            diff < 48 * HOUR_MILLIS -> context.resources.getString(R.string.offer_tomorrow)
            else -> context.resources.getString(R.string.offer_day,diff / DAY_MILLIS)
        }
    }*/

    fun convertMySqlFormatDate(givenDateTime:String?,format:String):String{
        var formatDate = ""
        var dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        try {
            givenDateTime?.let {
                val parseDate = dateFormat.parse(it)
                dateFormat = SimpleDateFormat(format, Locale.US)
                formatDate = dateFormat.format(parseDate!!)
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return formatDate
    }

    fun convertHumanFormatDate(givenDateTime:String?,format:String):String{
        var formatDate = ""
        var dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        try {
            givenDateTime?.let {
                val parseDate = dateFormat.parse(it)
                dateFormat = SimpleDateFormat(format, Locale.US)
                formatDate = dateFormat.format(parseDate!!)
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return formatDate
    }

    fun convert24HourFormatTo12HourFormat(time : String) : String {
        return try {
            val sdf = SimpleDateFormat("H:mm", Locale.US)
            val dateObj = sdf.parse(time)
            SimpleDateFormat("hh:mm aa", Locale.US).format(dateObj)
        } catch (e: ParseException) {
            ""
        }
    }

    fun convert12HourFormatTo24HourFormat(time : String) : String {
        return try {
            val sdf = SimpleDateFormat("hh:mm aa", Locale.US)
            val dateObj = sdf.parse(time)
            SimpleDateFormat("H:mm", Locale.US).format(dateObj)
        } catch (e: ParseException) {
            ""
        }
    }

    fun addOneHourWithCurrentTime() : String
    {
        return try {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.HOUR, 1)
            val currentTime = SimpleDateFormat(DateTimeFormat.sqlHM,Locale.getDefault()).format(calendar.time)
            currentTime
        }catch (e : ParseException) {
            "12:00"
        }
    }

    fun convertHumanFormatDate2(givenDateTime:String?,format:String):String{
        var formatDate = ""
        var dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        try {
            givenDateTime?.let {
                val parseDate = dateFormat.parse(it)
                dateFormat = SimpleDateFormat(format, Locale.US)
                formatDate = dateFormat.format(parseDate!!)
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return formatDate
    }

    fun compareCurrentTimeWithPickTime(currentDateTime:String, pickDateTime: String): Boolean {
        return when {
            pickDateTime > currentDateTime -> {
                true
            }
            pickDateTime < currentDateTime -> {
                false
            }
            pickDateTime.compareTo(currentDateTime) == 0 -> {
                true
            }
            else -> true
        }
    }

    fun isDateTodayDate(date:String): String{
        val sdf = SimpleDateFormat(DateTimeFormat.sqlYMD, Locale.US)
        val currentDate = sdf.format(Date())
        return if (currentDate == date) date else ""
    }


    fun convertSecondsToHMmSs(millis: Long): String {
        return String.format(
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(millis) -
                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
            TimeUnit.MILLISECONDS.toSeconds(millis) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        )
    }

    fun convertSecondToTime(second: Long): String {
        val minute = String.format("%02d",(second / 60).toInt())
        val restOfSecond = String.format("%02d",second % 60)

        return "$minute:$restOfSecond"
    }

    fun convertSecondToMinute(second: Long): String {
        return  (second / 60).toString()
    }

}
