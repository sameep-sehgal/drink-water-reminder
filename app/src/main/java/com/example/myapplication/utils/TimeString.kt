package com.example.myapplication.utils

import android.util.Log
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class TimeString {
    companion object{
        private val dateFormat: DateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        fun longToString(time:Long):String{
            Log.d("TAG23", "longToString: ${dateFormat.format(time)}")
            return dateFormat.format(time)
        }
    }
}