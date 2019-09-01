package com.sheraz.listgithubrepoissues.utils

import android.os.Build
import android.util.Log
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun getFormattedDate(rawStringDate: String): String {

    var formattedDate: String = ""

    try {
        formattedDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val localDateTime = ZonedDateTime.parse(rawStringDate)
            val formatter = DateTimeFormatter.ofPattern("EEE dd.MM.yyyy HH:mm")
            formatter.format(localDateTime)
        } else {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
            val formatter = SimpleDateFormat("EEE dd.MM.yyyy HH:mm", Locale.getDefault())
            formatter.format(parser.parse(rawStringDate))
        }
    } catch (e: Exception) {
        Log.e("DateUtils", "getFormattedDate(): " +
                    "Exception occurred while parsing rawStringDat = $rawStringDate, Exception => ${e.message}")
    }

    Log.d("DateUtils", "getFormattedDate(): rawStringDate = $rawStringDate, formattedDate = $formattedDate")

    return formattedDate
}