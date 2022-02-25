package com.sameep.watertracker.remindernotification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.flow.first
import androidx.core.app.NotificationManagerCompat
import androidx.datastore.preferences.core.edit
import com.sameep.watertracker.data.preferencedatastore.PreferenceDataStore
import com.sameep.watertracker.data.preferencedatastore.dataStore
import com.sameep.watertracker.utils.ReminderReceiverUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TurnReminderOnReceiver: BroadcastReceiver() {
  override fun onReceive(context: Context?, intent: Intent?) {
    if (context != null) {
      NotificationManagerCompat.from(context).cancel(NOTIFICATION_ID)
      var reminderPeriodStartTime: String? = null
      var reminderGap: Int? = null
      GlobalScope.launch {
        context.dataStore.data.first{
          reminderPeriodStartTime = it[PreferenceDataStore.PreferencesKeys.REMINDER_PERIOD_START]
          reminderGap = it[PreferenceDataStore.PreferencesKeys.REMINDER_GAP]
          true
        }
        ReminderReceiverUtil.setBothReminderAndAlarm(
          context = context,
          reminderGap = reminderGap!!,
          reminderPeriodStartTime = reminderPeriodStartTime!!
        )
        context.dataStore.edit {
          it[PreferenceDataStore.PreferencesKeys.IS_REMINDER_ON] = true
        }
      }
    }
  }
}