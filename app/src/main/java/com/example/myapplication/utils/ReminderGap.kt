package com.example.myapplication.utils

class ReminderGap {
  companion object{
    const val NOT_SET = -1
    const val DONT_REMIND = -2
    const val FIFTEEN_MINUTES = 15*60
    const val THIRTY_MINUTES = 30*60
    const val FORTY_FIVE_MINUTES = 45*60
    const val ONE_HOUR = 1*60*60
    const val TWO_HOURS = 2*ReminderGap.ONE_HOUR
    const val THREE_HOURS = 3*ReminderGap.ONE_HOUR
    const val ONE_AND_HALF_HOUR = ReminderGap.ONE_HOUR + ReminderGap.THIRTY_MINUTES
    const val TWO_AND_HALF_HOURS = ReminderGap.TWO_HOURS + ReminderGap.THIRTY_MINUTES
  }
}