package com.example.myapplication.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DateString {
    companion object {
        private val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd",Locale.getDefault())

        fun getTodaysDate():String {
            return dateFormat.format(Date())
        }

        fun getYesterdaysDate():String {
            val date = Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24)
            return dateFormat.format(date)
        }

        fun convertToDateString(date: Date):String {
            return dateFormat.format(date)
        }
    }
}