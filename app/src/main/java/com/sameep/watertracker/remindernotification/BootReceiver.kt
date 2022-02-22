package com.sameep.watertracker.remindernotification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
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
    Log.d("TAG", "onReceive: Inside Boot Receiver")
    if (intent != null && intent.action != null && context!=null) {
      Log.d("TAG", "onReceive: Inside Boot Receiver $context")
      if (
        Intent.ACTION_BOOT_COMPLETED == intent.action ||
                intent.action == "android.intent.action.QUICKBOOT_POWERON"
      ) {
        var reminderGap:Int? = null
        var isReminderOn:Boolean? = null
        var reminderPeriodStart: String? = null
        GlobalScope.launch {
          context.dataStore.data.first {
            reminderGap = it[PreferenceDataStore.PreferencesKeys.REMINDER_GAP]
            isReminderOn = it[PreferenceDataStore.PreferencesKeys.IS_REMINDER_ON]
            reminderPeriodStart = it[PreferenceDataStore.PreferencesKeys.REMINDER_PERIOD_START]
            true
          }
          ReminderReceiverUtil.cancelReminder(context)
          if (isReminderOn!!) {
            Log.d("TAG", "onReceive: Inside Boot Receiver Set Reminder")
            ReminderReceiverUtil.setBothReminderAndAlarm(
              context = context,
              reminderGap = reminderGap!!,
              reminderPeriodStartTime = reminderPeriodStart!!
            )
          }
        }
      }
    }
  }
}