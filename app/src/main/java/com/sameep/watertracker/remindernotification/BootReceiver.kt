package com.sameep.watertracker.remindernotification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.sameep.watertracker.data.preferencedatastore.PreferenceDataStore
import com.sameep.watertracker.data.preferencedatastore.dataStore
import com.sameep.watertracker.utils.ReminderReceiverUtil
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BootReceiver: BroadcastReceiver() {
  @DelicateCoroutinesApi
  override fun onReceive(context: Context?, intent: Intent?) {
    if (intent != null && intent.action != null && context!=null) {
      if (
        intent.action == "android.intent.action.BOOT_COMPLETED" ||
                intent.action == "android.intent.action.QUICKBOOT_POWERON"
      ) {
        var reminderGap:Int? = null
        var isReminderOn:Boolean? = null
        GlobalScope.launch {
          context.dataStore.data.first {
            reminderGap = it[PreferenceDataStore.PreferencesKeys.REMINDER_GAP]
            isReminderOn = it[PreferenceDataStore.PreferencesKeys.IS_REMINDER_ON]
            true
          }
          ReminderReceiverUtil.cancelReminder(context)
          if (isReminderOn!!) {
            ReminderReceiverUtil.setReminder(
              context = context,
              reminderGap = reminderGap!!
            )
          }
        }
      }
    }
  }
}