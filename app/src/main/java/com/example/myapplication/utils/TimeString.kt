package com.example.myapplication.utils

import android.util.Log
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object TimeString {
  private val dateFormat: DateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

  fun longToString(time:Long):String{
    Log.d("TAG23", "longToString: ${dateFormat.format(time)}")
    return dateFormat.format(time)
  }

  fun HHMMIntToString(hourOfDay:Int, minute:Int):String{
    val HH:String
    if(hourOfDay<10) HH = "0$hourOfDay"
    else HH = "$hourOfDay"
    val MM:String
    if(minute < 10) MM = "0$minute"
    else MM = "$minute"

    return "$HH:$MM"
  }

  fun getCalendarInstance(time:String): Calendar {
    val timeHour = time.split(':')[0].toInt()
    val timeMinute = time.split(':')[1].toInt()

    val timeCalendar = Calendar.getInstance()
    timeCalendar.set(Calendar.HOUR_OF_DAY,timeHour)
    timeCalendar.set(Calendar.MINUTE,timeMinute)
    timeCalendar.set(Calendar.SECOND,0)
    timeCalendar.set(Calendar.MILLISECOND,0)

    return timeCalendar
  }
}