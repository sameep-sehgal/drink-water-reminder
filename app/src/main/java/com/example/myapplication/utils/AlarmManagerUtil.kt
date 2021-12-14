package com.example.myapplication.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.myapplication.data.preferencedatastore.PreferenceDataStore
import com.example.myapplication.remindernotification.ReminderReceiver
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.*
import javax.inject.Inject

class AlarmManagerUtil @Inject constructor(
  private val preferenceDataStore: PreferenceDataStore
){
  suspend fun tomorrowsRepeatingAlarm(context: Context) {
    val calendar = Calendar.getInstance()
    var hours = 8
    var minutes = 0
    var gap = 0
    preferenceDataStore.reminderPeriodStart().distinctUntilChanged().collect {
      hours = it.split(":")[0].toInt()
      minutes = it.split(":")[1].toInt()
    }
    preferenceDataStore.reminderGap().distinctUntilChanged().collect {
      gap = it
    }
    calendar.add(Calendar.DATE,1)
    calendar.set(Calendar.HOUR_OF_DAY, hours)
    calendar.set(Calendar.MINUTE, minutes)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    val alarmManager =
      context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
    val intent = Intent(context, ReminderReceiver::class.java)
    val pendingIntent =
      PendingIntent.getBroadcast(context, 0, intent,0)
    alarmManager?.setInexactRepeating(
      AlarmManager.RTC_WAKEUP,
      calendar.timeInMillis,
      gap.toLong(),
      pendingIntent
    )
  }

  suspend fun startRepeatingAlarm(context: Context) {
    //TODO("COMPARE CURRENT TIME WITH REMINDER_PERIOD_END")
    val calendar = Calendar.getInstance()
    var gap = 0
    preferenceDataStore.reminderGap().distinctUntilChanged().collect {
      gap = it
    }
    calendar.add(Calendar.SECOND,gap)
    val alarmManager =
      context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
    val intent = Intent(context, ReminderReceiver::class.java)
    val pendingIntent =
      PendingIntent.getBroadcast(context, 0, intent,0)
    alarmManager?.setInexactRepeating(
      AlarmManager.RTC_WAKEUP,
      calendar.timeInMillis,
      gap.toLong(),
      pendingIntent
    )
  }

  fun cancelAlarm(context: Context) {
    val alarmManager =
      context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
    val intent = Intent(context, ReminderReceiver::class.java)
    val pendingIntent =
      PendingIntent.getBroadcast(context, 0, intent,0)
    alarmManager?.cancel(pendingIntent)
  }
}