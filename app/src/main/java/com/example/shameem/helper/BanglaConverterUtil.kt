package com.example.shameem.helper

object BanglaConverterUtil {
    fun convertNumberToBengaliNumber(value: String): String? {
        return (value + "")
            .replace("0".toRegex(), "০")
            .replace("1".toRegex(), "১").replace("2".toRegex(), "২")
            .replace("3".toRegex(), "৩").replace("4".toRegex(), "৪")
            .replace("5".toRegex(), "৫").replace("6".toRegex(), "৬")
            .replace("7".toRegex(), "৭").replace("8".toRegex(), "৮")
            .replace("9".toRegex(), "৯")
    }

    fun convertMonthNumberToBengali(value: String): String {
        return (value + "")
            .replace("Jan".toRegex(), "জানু")
            .replace("Feb".toRegex(), "ফেব").replace("Mar".toRegex(), "মার্চ")
            .replace("Apr".toRegex(), "এপ্রি").replace("May".toRegex(), "মে")
            .replace("Jun".toRegex(), "জুন").replace("Jul".toRegex(), "জুলা")
            .replace("Aug".toRegex(), "অগাস্ট").replace("Sep".toRegex(), "সেপ্টে")
            .replace("Oct".toRegex(), "অক্টো").replace("Nov".toRegex(), "নভে")
            .replace("Dec".toRegex(), "ডিসে")
            .replace("0".toRegex(), "০")
            .replace("1".toRegex(), "১").replace("2".toRegex(), "২")
            .replace("3".toRegex(), "৩").replace("4".toRegex(), "৪")
            .replace("5".toRegex(), "৫").replace("6".toRegex(), "৬")
            .replace("7".toRegex(), "৭").replace("8".toRegex(), "৮")
            .replace("9".toRegex(), "৯")

    }

}