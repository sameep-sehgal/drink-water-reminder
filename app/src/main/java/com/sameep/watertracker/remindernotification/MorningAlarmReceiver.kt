package com.sameep.watertracker.remindernotification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.sameep.watertracker.data.preferencedatastore.PreferenceDataStore
import com.sameep.watertracker.data.preferencedatastore.dataStore
import com.sameep.watertracker.utils.ReminderReceiverUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

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
        ReminderReceiverUtil.setBothReminderAndAlarm(
          reminderGap = reminderGap!!,
          context = context,
          reminderPeriodStartTime = reminderPeriodStartTime!!,
          addDay = true
        )
      }
    }
  }
}