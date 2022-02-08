package com.example.myapplication.utils

import android.util.Log
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

object DateString {
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

  fun convertToDateString(calendar: Calendar):String {
    return dateFormat.format(calendar.time)
  }

  fun convertToDateString(year:Int, month:Int, dayOfMonth:Int): String {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.YEAR, year)
    calendar.set(Calendar.MONTH, month)
    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
    return convertToDateString(calendar)
  }

  fun getPrevDate(date:String):String {
    if(date == NOT_SET) return NOT_SET
    val calendar = getCalendarInstance(date)
    calendar.add(Calendar.SECOND,-24*60*60)
    return dateFormat.format(calendar.time)
  }

  fun getNextDate(date:String):String {
    if(date == NOT_SET) return NOT_SET
    val calendar = getCalendarInstance(date)
    calendar.add(Calendar.SECOND,24*60*60)
    return dateFormat.format(calendar.time)
  }

  fun convertToReadableString(date:String):String {
    val dateList = date.split('-')
    return "${dateList[2]} " +
            "${getMonthString(date.split('-')[1].toInt())} " +
            dateList[0]
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

  fun getCalendarInstance(date:String): Calendar {
    val calendar = Calendar.getInstance()
    if(date == "") return calendar
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

  fun getWeekStartDate(todaysDate: String): String {
    val calendar = getCalendarInstance(todaysDate)
    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    calendar.add(Calendar.DAY_OF_MONTH, -(dayOfWeek - 1))

    Log.d("TAG", "getWeekStartDate: ${dayOfWeek} ${convertToDateString(calendar = calendar)} ${calendar.get(Calendar.DAY_OF_WEEK)}")
    assert(calendar.get(Calendar.DAY_OF_WEEK) == 1)

    return convertToDateString(calendar = calendar)
  }

  fun getWeekEndDate(todaysDate: String): String {
    val calendar = getCalendarInstance(todaysDate)
    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    calendar.add(Calendar.DAY_OF_MONTH, 7 - dayOfWeek)

    assert(calendar.get(Calendar.DAY_OF_WEEK) == 7)

    return convertToDateString(calendar = calendar)
  }

  fun getDayOfWeek(dayOfWeek:Int): String {

    assert(dayOfWeek in 1..8)

    val days = listOf("Sun", "Mon", "Tues", "Wed", "Thu", "Fri", "Sat")
    return days[dayOfWeek-1]
  }

  fun getDateIntervalString(startDate:String, endDate:String): String {
    val startDateList = startDate.split("-")
    val endDateList = endDate.split("-")
    return "${startDateList[2]} ${getMonthString(startDateList[1].toInt())}" +
            " - " +
            "${endDateList[2]} ${getMonthString(endDateList[1].toInt())}"
  }

  fun getMonthString(month:Int): String {
    assert(month in 1..13)
    val months = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
    return months[month-1]
  }

  fun getMonthStartDate(date: String): String {
    val calendar = Calendar.getInstance()
    val dateList = date.split("-")
    calendar.set(Calendar.MONTH, dateList[1].toInt()-1)
    calendar.set(Calendar.YEAR, dateList[0].toInt())
    calendar.set(Calendar.DATE, 1)

    return convertToDateString(calendar)
  }

  fun getMonthEndDate(date: String): String {
    val calendar = Calendar.getInstance()
    val dateList = date.split("-")
    calendar.set(Calendar.MONTH, dateList[1].toInt()-1)
    calendar.set(Calendar.YEAR, dateList[0].toInt())
    calendar.add(Calendar.MONTH, 1)
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    calendar.add(Calendar.DAY_OF_MONTH, -1)

    return convertToDateString(calendar)
  }

}