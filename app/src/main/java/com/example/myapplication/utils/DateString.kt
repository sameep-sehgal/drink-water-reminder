package com.example.myapplication.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

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
      val calendar = Calendar.getInstance()
      val time = date.split("-")
      calendar.set(Calendar.YEAR, time[0].toInt())
      calendar.set(Calendar.MONTH, time[1].toInt() - 1) //value==0 represents January
      calendar.set(Calendar.DAY_OF_MONTH, time[2].toInt())
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
      val calendar = Calendar.getInstance()
      val time = date.split("-")
      calendar.set(Calendar.YEAR, time[0].toInt())
      calendar.set(Calendar.MONTH, time[1].toInt() - 1) //value==0 represents January
      calendar.set(Calendar.DAY_OF_MONTH, time[2].toInt())
      calendar.add(Calendar.SECOND,-days*24*60*60)
      return dateFormat.format(calendar.time)
    }
  }
}