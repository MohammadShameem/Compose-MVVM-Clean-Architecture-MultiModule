package co.jatri.dateutil

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateTimeFormatter {
    const val sqlYMD = "yyyy-MM-dd"
    const val sqlYMDHMS = "yyyy-MM-dd HH:mm:ss"
    const val sqlHMS = "hh:mm"
    const val sqlHM = "hh:mm"

    const val outputYMD = "dd MMM yyyy"
    const val outputYMDHMSA = "dd MMM yyyy hh:mm:ss aa"
    const val outputYMDHMA = "dd MMM yyyy, hh:mm aa"
    const val outputYMDHMS = "dd MMM yyyy hh:mm:ss"
    const val outputHMSA = "hh:mm:ss aa"
    const val outputHMS = "hh:mm:ss"
    const val outputHMA = "hh:mm aa"
    const val outputHM = "hh:mm"

    fun getCurrentDeviceDateTime(format:String):String{
        val c = Calendar.getInstance().time
        val df = SimpleDateFormat(format, Locale.US)
        return df.format(c)
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

    fun convertYMDHMSATodMY(dateTime: String): String{
        val serverDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        var date: Date? = null
        try {
            date = serverDateFormat.parse(dateTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val readableDateFormat = SimpleDateFormat("dd MMM yyyy", Locale.US)
        return readableDateFormat.format(date!!)
    }
}
