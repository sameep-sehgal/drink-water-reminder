package com.example.myapplication.remindernotification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.example.myapplication.data.models.DailyWaterRecord
import com.example.myapplication.data.preferencedatastore.PreferenceDataStore
import com.example.myapplication.data.preferencedatastore.dataStore
import com.example.myapplication.data.roomdatabase.WaterDatabase
import com.example.myapplication.utils.ReminderReceiverUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first

class ReminderReceiver: BroadcastReceiver() {
  @DelicateCoroutinesApi
  override fun onReceive(context: Context?, intent: Intent?) {
    Log.d("TAG", "onReceive: Notification Received Before GlobalScope")
    var reminderPeriodStart:String? = null
    var reminderPeriodEnd:String? = null
    var glassCapacity: Int? = null
    var mugCapacity: Int? = null
    var bottleCapacity: Int? = null
    var waterUnit: String? = null
    var remindAfterGoalAchieved: Boolean? = null
    var dailyWaterGoal: Int? = null
    Log.d("TAG", "onReceive: $context ${context?.dataStore?.data} outside datastore")

    GlobalScope.launch {
      Log.d("TAG", "onReceive: Notification Received Inside GlobalScope")

      context?.dataStore?.data?.first {
        Log.d("TAG", "onReceive: $it ${it[PreferenceDataStore.PreferencesKeys.REMINDER_PERIOD_START]} inside datastore")

        reminderPeriodStart = it[PreferenceDataStore.PreferencesKeys.REMINDER_PERIOD_START]
        reminderPeriodEnd = it[PreferenceDataStore.PreferencesKeys.REMINDER_PERIOD_END]
        glassCapacity = it[PreferenceDataStore.PreferencesKeys.GLASS_CAPACITY]
        mugCapacity = it[PreferenceDataStore.PreferencesKeys.MUG_CAPACITY]
        bottleCapacity = it[PreferenceDataStore.PreferencesKeys.BOTTLE_CAPACITY]
        waterUnit = it[PreferenceDataStore.PreferencesKeys.WATER_UNIT]
        remindAfterGoalAchieved = it[PreferenceDataStore.PreferencesKeys.REMINDER_AFTER_GOAL_ACHIEVED]
        dailyWaterGoal = it[PreferenceDataStore.PreferencesKeys.DAILY_WATER_GOAL]
        Log.d("TAG", "onReceive: ${waterUnit} inside datastore")
        true
      }

      Log.d("TAG", "onReceive: Values $reminderPeriodStart - $reminderPeriodEnd,, $waterUnit")
      if(context!=null) {
        val db = WaterDatabase.getInstance(context).waterDatabaseDao()
        var todaysWaterRecord: DailyWaterRecord? =
          withContext(Dispatchers.Default) { db.getDailyWaterRecordWithoutFlow() }
        if (todaysWaterRecord == null) {
          dailyWaterGoal?.let { DailyWaterRecord(goal = it) }
            ?.let { db.insertDailyWaterRecord(it) }
          todaysWaterRecord =
            withContext(Dispatchers.Default) { db.getDailyWaterRecordWithoutFlow() }
        }

        Log.d("TAG", "onReceive: TodaysWaterRecord = $todaysWaterRecord")

        if(ReminderReceiverUtil.shallNotify(
          reminderPeriodEnd = reminderPeriodEnd,
          reminderPeriodStart = reminderPeriodStart,
          remindAfterGoalAchieved = remindAfterGoalAchieved,
          todaysWaterRecord = todaysWaterRecord,
          context = context
        )) {
          Log.d("TAG", "onReceive: Shall Notify == true")
          val builder = ReminderReceiverUtil.buildBasicNotification(
            context = context,
            glassCapacity = glassCapacity,
            mugCapacity = mugCapacity,
            bottleCapacity = bottleCapacity,
            waterUnit = waterUnit,
            dailyWaterGoal = dailyWaterGoal,
            channelId = CHANNEL_ID_4
          )
          Log.d("TAG", "onReceive: Builder $builder")
          if(builder != null) {
            builder
              .setProgress(todaysWaterRecord.goal,todaysWaterRecord.currWaterAmount, false)
              .setContentText("${todaysWaterRecord.currWaterAmount}/${todaysWaterRecord.goal}$waterUnit")
            with(context.let { NotificationManagerCompat.from(it) }) {
              this.notify(TEST_NOTIFICATION_ID, builder.build())
            }
            Log.d("TAG", "onReceive: Notification Set")
          }
        } else {
          Log.d("TAG", "onReceive: Shall Notify == false")
        }
      }
    }
  }
}