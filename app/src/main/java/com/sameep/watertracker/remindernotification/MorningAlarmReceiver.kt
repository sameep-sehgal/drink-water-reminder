package com.sameep.watertracker.remindernotification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.sameep.watertracker.data.preferencedatastore.PreferenceDataStore
import com.sameep.watertracker.data.preferencedatastore.dataStore
import com.sameep.watertracker.utils.ReminderReceiverUtil
import com.sameep.watertracker.utils.TimeString
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*

class MorningAlarmReceiver: BroadcastReceiver() {
  override fun onReceive(context: Context?, intent: Intent?) {
    var reminderGap: Int? = null
    var reminderPeriodStartTime: String? = null

    GlobalScope.launch {
      Log.d("TAG", "onReceive: MorningAlarmReceiver Notification Received Inside GlobalScope")

      context?.dataStore?.data?.first {
        Log.d(
          "TAG",
          "onReceive: ${it[PreferenceDataStore.PreferencesKeys.REMINDER_PERIOD_START]} inside datastore"
        )
        reminderGap = it[PreferenceDataStore.PreferencesKeys.REMINDER_GAP]
        reminderPeriodStartTime = it[PreferenceDataStore.PreferencesKeys.REMINDER_PERIOD_START]
        true
      }

      if (context != null) {
        val nextReminderTime = TimeString.getCalendarInstance(reminderPeriodStartTime!!)
        val currTime = Calendar.getInstance()
        if(nextReminderTime < currTime)
          nextReminderTime.add(Calendar.DAY_OF_MONTH,1)
        ReminderReceiverUtil.setBothReminderAndAlarm(
          reminderGap = reminderGap!!,
          context = context,
          reminderPeriodStartTime = reminderPeriodStartTime!!,
          addDay = true,
          time = nextReminderTime
        )
      }
    }
  }
}