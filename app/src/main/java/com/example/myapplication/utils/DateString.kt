package com.example.myapplication.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

class DateString {
  companion object {
    const val NOT_SET = ""
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

    fun getPrevDate(date:String):String {
      if(date == NOT_SET) return NOT_SET
      val calendar = getCalendarInstance(date)
      calendar.add(Calendar.SECOND,-24*60*60)
      return dateFormat.format(calendar.time)
    }

    fun clipToMMDD(date:String):String {
      //Accepts date in yyyy-mm-dd format
      val dd = date.split('-')[2]
      val mm = date.split('-')[1]
      return "$mm/$dd"
    }

    fun reduceDays(date:String,days:Int):String {
      if(date == NOT_SET) return NOT_SET
      val calendar = getCalendarInstance(date)
      calendar.add(Calendar.SECOND,-days*24*60*60)
      return dateFormat.format(calendar.time)
    }

    fun calculateDaysDifference(date1:String, date2:String): Int {
      if(date1 == NOT_SET || date2 == NOT_SET) return 0
      val calendar1 = getCalendarInstance(date1)
      val calendar2 = getCalendarInstance(date2)
      return calculateDaysDifference(calendar1,calendar2)
    }

    private fun getCalendarInstance(date:String): Calendar {
      val calendar = Calendar.getInstance()
      val dateArray = date.split("-")
      calendar.set(Calendar.YEAR, dateArray[0].toInt())
      calendar.set(Calendar.MONTH, dateArray[1].toInt() - 1) //value==0 represents January
      calendar.set(Calendar.DAY_OF_MONTH, dateArray[2].toInt())
      return calendar
    }

    private fun calculateDaysDifference(calendar1:Calendar, calendar2: Calendar): Int {
      val newStart = Calendar.getInstance()
      newStart.timeInMillis = calendar1.getTimeInMillis()
      newStart[Calendar.HOUR_OF_DAY] = 0
      newStart[Calendar.MINUTE] = 0
      newStart[Calendar.SECOND] = 0
      newStart[Calendar.MILLISECOND] = 0

      val newEnd = Calendar.getInstance()
      newEnd.timeInMillis = calendar2.getTimeInMillis()
      newEnd[Calendar.HOUR_OF_DAY] = 0
      newEnd[Calendar.MINUTE] = 0
      newEnd[Calendar.SECOND] = 0
      newEnd[Calendar.MILLISECOND] = 0

      val end = newEnd.timeInMillis
      val start = newStart.timeInMillis
      return TimeUnit.MILLISECONDS.toDays(abs(end - start)).toInt()
    }
  }
}