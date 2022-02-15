package com.sameep.watertracker.utils

object ReminderFrequencyOptions {
  const val FIFTEEN_MINUTES = "Every 15 Minutes"
  const val THIRTY_MINUTES = "Every 30 Minutes"
  const val FORTY_FIVE_MINUTES = "Every 45 Minutes"
  const val ONE_HOUR = "Every 1 Hour"
  const val ONE_AND_HALF_HOURS = "Every 1.5 Hours"
  const val TWO_HOURS = "Every 2 Hours"
  const val TWO_AND_HALF_HOURS = "Every 2.5 Hours"
  const val THREE_HOURS = "Every 3 Hours"
  const val DONT_REMIND = "Don't Remind"

  val OPTIONS = listOf(
    hashMapOf<String,Any> ("text" to FIFTEEN_MINUTES,
    "gap" to ReminderGap.FIFTEEN_MINUTES
    ),
    hashMapOf<String,Any>("text" to THIRTY_MINUTES,
          "gap" to ReminderGap.THIRTY_MINUTES
    ),
    hashMapOf<String,Any>("text" to FORTY_FIVE_MINUTES,
          "gap" to ReminderGap.FORTY_FIVE_MINUTES
    ),
    hashMapOf<String,Any>("text" to ONE_HOUR,
          "gap" to ReminderGap.ONE_HOUR
    ),
    hashMapOf<String,Any>("text" to ONE_AND_HALF_HOURS,
          "gap" to ReminderGap.ONE_AND_HALF_HOUR
    ),
    hashMapOf<String,Any>("text" to TWO_HOURS,
          "gap" to ReminderGap.TWO_HOURS
    ),
    hashMapOf<String,Any>("text" to TWO_AND_HALF_HOURS,
          "gap" to ReminderGap.TWO_AND_HALF_HOURS
    ),
    hashMapOf<String,Any>("text" to THREE_HOURS,
          "gap" to ReminderGap.THREE_HOURS
    ),
  )

}