package com.example.common.utils

import java.util.regex.Matcher
import java.util.regex.Pattern

object Utils {
    fun formatPhoneNumber(phoneNumber:String):String{
        return when {
            phoneNumber.startsWith("+880") -> phoneNumber
            phoneNumber.substring(0,1)=="0" -> "+88$phoneNumber"
            else -> "+880$phoneNumber"
        }
    }

    fun isPhoneNumberValid(number: String): Boolean {
        val pattern: Pattern = Pattern.compile( "((0|01|\\+88|\\+88\\s*\\(0\\)|\\+88\\s*0)\\s*)?1(\\s*[0-9]){9}")
        val matcher: Matcher = pattern.matcher(number)
        return matcher.matches()
    }


    /*fun getVectorMarkerToBitmap(context: Context, vectorResId:Int): BitmapDescriptor?{
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

    fun decodePoly(encoded: String): List<LatLng> {
        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = encoded[index++].code- 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val p = LatLng(
                lat.toDouble() / 1E5,
                lng.toDouble() / 1E5
            )
            poly.add(p)
        }

        return poly
    }*/

}