package com.sameep.watertracker.remindernotification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import androidx.datastore.preferences.core.edit
import com.sameep.watertracker.data.preferencedatastore.PreferenceDataStore
import com.sameep.watertracker.data.preferencedatastore.dataStore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TurnReminderOnReceiver: BroadcastReceiver() {
  override fun onReceive(context: Context?, intent: Intent?) {
    if (context != null) {
      NotificationManagerCompat.from(context).cancel(NOTIFICATION_ID)
    }
    GlobalScope.launch {
      context?.dataStore?.edit {
        it[PreferenceDataStore.PreferencesKeys.IS_REMINDER_ON] = true
      }
    }
  }
}