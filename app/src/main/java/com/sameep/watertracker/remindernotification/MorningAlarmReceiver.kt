package com.sameep.watertracker.remindernotification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.sameep.watertracker.data.preferencedatastore.PreferenceDataStore
import com.sameep.watertracker.data.preferencedatastore.dataStore
import com.sameep.watertracker.utils.ReminderReceiverUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MorningAlarmReceiver: BroadcastReceiver() {
  override fun onReceive(context: Context?, intent: Intent?) {
    var glassCapacity: Int? = null
    var mugCapacity: Int? = null
    var bottleCapacity: Int? = null
    var waterUnit: String? = null
    var reminderGap: Int? = null
    var dailyWaterGoal: Int? = null
    var reminderPeriodStartTime: String? = null

    GlobalScope.launch {
      Log.d("TAG", "onReceive: Notification Received Inside GlobalScope")

      context?.dataStore?.data?.first {
        Log.d(
          "TAG",
          "onReceive: $it ${it[PreferenceDataStore.PreferencesKeys.REMINDER_PERIOD_START]} inside datastore"
        )

        glassCapacity = it[PreferenceDataStore.PreferencesKeys.GLASS_CAPACITY]
        mugCapacity = it[PreferenceDataStore.PreferencesKeys.MUG_CAPACITY]
        bottleCapacity = it[PreferenceDataStore.PreferencesKeys.BOTTLE_CAPACITY]
        waterUnit = it[PreferenceDataStore.PreferencesKeys.WATER_UNIT]
        reminderGap = it[PreferenceDataStore.PreferencesKeys.REMINDER_GAP]
        dailyWaterGoal = it[PreferenceDataStore.PreferencesKeys.DAILY_WATER_GOAL]
        reminderPeriodStartTime = it[PreferenceDataStore.PreferencesKeys.REMINDER_PERIOD_START]
        Log.d("TAG", "onReceive: $bottleCapacity inside datastore")
        true
      }

      if (context != null) {
        ReminderReceiverUtil.setBothReminderAndAlarm(
          reminderGap = reminderGap!!,
          context = context,
          reminderPeriodStartTime = reminderPeriodStartTime!!
        )
      }

      val builder = ReminderReceiverUtil.buildBasicNotification(
        context = context,
        glassCapacity = glassCapacity,
        mugCapacity = mugCapacity,
        bottleCapacity = bottleCapacity,
        waterUnit = waterUnit,
        dailyWaterGoal = dailyWaterGoal
      )

      if(builder != null) {
        builder.setContentTitle("Rise And Shine!")
        builder.setContentText("Kickstart your metabolism with a big glass of water")
        if(context != null){
          with(context.let { NotificationManagerCompat.from(it) }) {
            this.notify(NOTIFICATION_ID, builder.build())
          }
        }
      }
    }
  }
}