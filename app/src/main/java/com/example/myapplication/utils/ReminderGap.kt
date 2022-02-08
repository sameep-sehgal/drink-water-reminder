package com.example.myapplication.utils

object ReminderGap {
  const val NOT_SET = -1
  const val DONT_REMIND = -2
  const val FIFTEEN_MINUTES = 15*60*1000
  const val THIRTY_MINUTES = 30*60*1000
  const val FORTY_FIVE_MINUTES = 45*60*1000
  const val ONE_HOUR = 1*60*60*1000
  const val TWO_HOURS = 2* ONE_HOUR
  const val THREE_HOURS = 3* ONE_HOUR
  const val ONE_AND_HALF_HOUR = ONE_HOUR + THIRTY_MINUTES
  const val TWO_AND_HALF_HOURS = TWO_HOURS + THIRTY_MINUTES

  val GAP_OPTION_TEXT_MAPPER = hashMapOf<Int,String>(
    DONT_REMIND to ReminderFrequencyOptions.DONT_REMIND,
    FIFTEEN_MINUTES to ReminderFrequencyOptions.FIFTEEN_MINUTES,
    THIRTY_MINUTES to ReminderFrequencyOptions.THIRTY_MINUTES,
    FORTY_FIVE_MINUTES to ReminderFrequencyOptions.FORTY_FIVE_MINUTES,
    ONE_HOUR to ReminderFrequencyOptions.ONE_HOUR,
    TWO_HOURS to ReminderFrequencyOptions.TWO_HOURS,
    THREE_HOURS to ReminderFrequencyOptions.THREE_HOURS,
    ONE_AND_HALF_HOUR to ReminderFrequencyOptions.ONE_AND_HALF_HOURS,
    TWO_AND_HALF_HOURS to ReminderFrequencyOptions.TWO_AND_HALF_HOURS,
  )

}