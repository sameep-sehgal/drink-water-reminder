package com.sameep.watertracker.remindernotification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.sameep.watertracker.data.models.DailyWaterRecord
import com.sameep.watertracker.data.preferencedatastore.PreferenceDataStore
import com.sameep.watertracker.data.preferencedatastore.dataStore
import com.sameep.watertracker.data.roomdatabase.WaterDatabase
import com.sameep.watertracker.utils.ReminderReceiverUtil
import com.sameep.watertracker.utils.TimeString
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import java.util.*

class ReminderReceiver: BroadcastReceiver() {
  override fun onReceive(context: Context?, intent: Intent?) {
    Log.d("TAG", "onReceive: Reminder Receiver Received Before GlobalScope")
    var reminderPeriodStart:String? = null
    var reminderPeriodEnd:String? = null
    var glassCapacity: Int? = null
    var mugCapacity: Int? = null
    var bottleCapacity: Int? = null
    var waterUnit: String? = null
    var remindAfterGoalAchieved: Boolean? = null
    var dailyWaterGoal: Int? = null
    var reminderGap: Int? = null

    GlobalScope.launch {

      context?.dataStore?.data?.first {
        reminderPeriodStart = it[PreferenceDataStore.PreferencesKeys.REMINDER_PERIOD_START]
        reminderPeriodEnd = it[PreferenceDataStore.PreferencesKeys.REMINDER_PERIOD_END]
        glassCapacity = it[PreferenceDataStore.PreferencesKeys.GLASS_CAPACITY]
        mugCapacity = it[PreferenceDataStore.PreferencesKeys.MUG_CAPACITY]
        bottleCapacity = it[PreferenceDataStore.PreferencesKeys.BOTTLE_CAPACITY]
        waterUnit = it[PreferenceDataStore.PreferencesKeys.WATER_UNIT]
        remindAfterGoalAchieved = it[PreferenceDataStore.PreferencesKeys.REMINDER_AFTER_GOAL_ACHIEVED]
        dailyWaterGoal = it[PreferenceDataStore.PreferencesKeys.DAILY_WATER_GOAL]
        reminderGap = it[PreferenceDataStore.PreferencesKeys.REMINDER_GAP]
        Log.d("TAG", "onReceive: $bottleCapacity inside datastore")
        true
      }

      if(context!=null) {
        ReminderReceiverUtil.setReminder(
          reminderGap = reminderGap!!,
          context = context
        )
        val db = WaterDatabase.getInstance(context).waterDatabaseDao()
        var todaysWaterRecord: DailyWaterRecord? =
          withContext(Dispatchers.Default) { db.getDailyWaterRecordWithoutFlow() }
        if (todaysWaterRecord == null) {
          dailyWaterGoal?.let { DailyWaterRecord(goal = it) }
            ?.let { db.insertDailyWaterRecord(it) }
          todaysWaterRecord =
            withContext(Dispatchers.Default) { db.getDailyWaterRecordWithoutFlow() }
        }

        if(ReminderReceiverUtil.shallNotify(
          reminderPeriodEnd = reminderPeriodEnd,
          reminderPeriodStart = reminderPeriodStart,
        )) {
          Log.d("TAG", "onReceive: Shall Notify == true")
          if(!(!remindAfterGoalAchieved!! && todaysWaterRecord.isGoalAchieved)) {
            val builder = ReminderReceiverUtil.buildBasicNotification(
              context = context,
              glassCapacity = glassCapacity,
              mugCapacity = mugCapacity,
              bottleCapacity = bottleCapacity,
              waterUnit = waterUnit,
              dailyWaterGoal = dailyWaterGoal
            )

            if(builder != null) {
              builder
                .setProgress(todaysWaterRecord.goal,todaysWaterRecord.currWaterAmount, false)
                .setContentText("${todaysWaterRecord.currWaterAmount}/${todaysWaterRecord.goal}$waterUnit")
              with(context.let { NotificationManagerCompat.from(it) }) {
                this.notify(NOTIFICATION_ID, builder.build())
              }
            }
          }
        } else {
          val nextReminderTime = TimeString.getCalendarInstance(reminderPeriodStart!!)
          val currTime = Calendar.getInstance()
          if(nextReminderTime < currTime)
            nextReminderTime.add(Calendar.DAY_OF_MONTH,1)
          ReminderReceiverUtil.setBothReminderAndAlarm(
            reminderGap = reminderGap!!,
            context = context,
            time = nextReminderTime,
            reminderPeriodStartTime = reminderPeriodStart!!
          )
          Log.d("TAG", "onReceive: Shall Notify == false")
        }
      }
    }
  }
}