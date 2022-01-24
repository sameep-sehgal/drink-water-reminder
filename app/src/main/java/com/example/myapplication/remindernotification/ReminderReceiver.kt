package com.example.myapplication.remindernotification

import android.app.AlarmManager
import android.app.Notification
import android.app.Notification.DEFAULT_SOUND
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.data.models.DailyWaterRecord
import com.example.myapplication.data.roomdatabase.WaterDatabase
import com.example.myapplication.data.roomdatabase.WaterDatabaseDao
import com.example.myapplication.repository.WaterDataRepository
import com.example.myapplication.ui.theme.PersianGreen
import com.example.myapplication.utils.*
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

//Use broadcast Receiver since app wont run when we have to show notifications
//It will automatically show notification at desired time without running the app
@AndroidEntryPoint
class ReminderReceiver: BroadcastReceiver() {

  private val TAG = ReminderReceiver::class.java.simpleName

  @ExperimentalPagerApi
  override fun onReceive(context: Context?, intent: Intent?) {

    val reminderPeriodStart = intent?.getStringExtra("reminder_period_start")
    val reminderPeriodEnd = intent?.getStringExtra("reminder_period_end")
    val reminderGap = intent?.getIntExtra("reminder_gap",ReminderGap.NOT_SET)
    val glassCapacity = intent?.getIntExtra("glass_capacity", 0)
    val mugCapacity = intent?.getIntExtra("mug_capacity",0)
    val bottleCapacity = intent?.getIntExtra("bottle_capacity",0)
    val waterUnit = intent?.getStringExtra("water_unit")
    val remindAfterGoalAchieved = intent?.getBooleanExtra("remind_after_goal_achieved", true)
    val dailyWaterGoal = intent?.getIntExtra("daily_water_goal",RecommendedWaterIntake.NOT_SET)
    val channelId = intent?.getStringExtra("channel_id")

    //Todo(""Will Need to pass Boolean dontRemindToday through intent extras"")
    //Todo(""Will Need to pass Reminder Sound to support devices < 8.0 through intent extras"")

    var reminderPeriodStartTime: Calendar = Calendar.getInstance()
    var reminderPeriodEndTime: Calendar = Calendar.getInstance()
    if(reminderPeriodStart != null) {
      reminderPeriodStartTime = TimeString.getCalendarInstance(reminderPeriodStart)
    }
    if(reminderPeriodEnd != null) {
      reminderPeriodEndTime = TimeString.getCalendarInstance(reminderPeriodEnd)
    }

    if(reminderPeriodStartTime > reminderPeriodEndTime){
      //Case -- reminderPeriodEnd = "02:00" and reminderPeriodStart = "10:00"
      reminderPeriodEndTime.add(Calendar.DAY_OF_MONTH,1)
    }

    val currTime = Calendar.getInstance()

    if(currTime < reminderPeriodEndTime && currTime > reminderPeriodStartTime){
      //Build notification only when we need to show.
      val builder = buildBasicNotification(
        context,
        glassCapacity,
        mugCapacity,
        bottleCapacity,
        waterUnit,
        dailyWaterGoal,
        channelId
      )

      GlobalScope.launch(Dispatchers.Main) {
        if(context!=null) {
          val db = WaterDatabase.getInstance(context).waterDatabaseDao()
          var todaysWaterRecord = withContext(Dispatchers.Default) { db.getDailyWaterRecordWithoutFlow() }
          if(todaysWaterRecord === null){
            //Day changes when new notification is to be shown
            dailyWaterGoal?.let { DailyWaterRecord(goal = it) }
              ?.let { db.insertDailyWaterRecord(it) }
            todaysWaterRecord = withContext(Dispatchers.Default) { db.getDailyWaterRecordWithoutFlow() }
          }
          if (builder != null && !(!remindAfterGoalAchieved!! && todaysWaterRecord.isGoalAchieved)) {
            builder
              .setProgress(todaysWaterRecord.goal,todaysWaterRecord.currWaterAmount, false)
              .setContentText("${todaysWaterRecord.currWaterAmount}/${todaysWaterRecord.goal}")
            with(context.let { NotificationManagerCompat.from(it) }) {
              // notificationId is a unique int for each notification that you must define
              this.notify(TEST_NOTIFICATION_ID, builder.build())
            }
          }
        }
      }
    }else{
      //Set Next Day Repeating Reminder
      reminderPeriodStartTime.add(Calendar.DAY_OF_MONTH,1)
      if (
        reminderPeriodStart != null &&
        reminderPeriodEnd != null &&
        reminderGap != null &&
        context != null &&
        glassCapacity != null &&
        mugCapacity != null &&
        bottleCapacity != null &&
        channelId != null &&
        waterUnit != null &&
        dailyWaterGoal != null &&
        remindAfterGoalAchieved != null
      ) {
        setReminder(
          reminderPeriodStartTime.timeInMillis,
          reminderPeriodStart,
          reminderPeriodEnd,
          reminderGap,
          glassCapacity,
          mugCapacity,
          bottleCapacity,
          channelId,
          waterUnit,
          dailyWaterGoal,
          remindAfterGoalAchieved,
          context
        )
      } else {
        Log.e(TAG, "Failed to build Notification")
      }
    }
  }

  @ExperimentalPagerApi
  fun buildBasicNotification(
    context: Context?,
    glassCapacity: Int?,
    mugCapacity: Int?,
    bottleCapacity: Int?,
    waterUnit:String?,
    dailyWaterGoal: Int?,
    channelId: String?
  ): NotificationCompat.Builder? {
    val mainActivityIntent = Intent(context, MainActivity::class.java).apply {
      flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    //Request codes are used to uniquely identify intents. They must not be the same for different intents
    val pendingIntent = PendingIntent.getActivity(context,0,mainActivityIntent,PendingIntent.FLAG_UPDATE_CURRENT)

    //Build notification only when we need to show.
    val builder = context?.let {
      NotificationCompat.Builder(it, CHANNEL_ID_4)
        //TODO(Surround setSound with if condition to handle device default sound)
        .setSound(
          Uri.parse("android.resource://"
                  + context.packageName + "/" + ReminderSound.NAME_VALUE_MAPPER[channelId]))
        .setSmallIcon(R.drawable.home_icon_glass_white)
        .setContentTitle("It's Time To Drink Water!")
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_MAX)
        .setDefaults(Notification.DEFAULT_VIBRATE)
        .setContentIntent(pendingIntent)
        .setColor(PersianGreen.hashCode())
        .setFullScreenIntent(pendingIntent, true)
    }

    //Add Action Buttons
    if(glassCapacity != null && glassCapacity != 0) {
      val addWaterIntentGlass = Intent(context, AddWaterReceiver::class.java).apply {
        putExtra("value", glassCapacity)
        putExtra("container", Container.GLASS)
        putExtra("daily_water_goal", dailyWaterGoal)
      }
      val pendingActionIntentGlass = PendingIntent.getBroadcast(context,1, addWaterIntentGlass, PendingIntent.FLAG_UPDATE_CURRENT)
      builder?.addAction(R.drawable.glass_2, "${glassCapacity}${waterUnit}", pendingActionIntentGlass)
    }
    if(mugCapacity != null && mugCapacity != 0) {
      val addWaterIntentMug = Intent(context, AddWaterReceiver::class.java).apply {
        putExtra("value", mugCapacity)
        putExtra("container", Container.MUG)
        putExtra("daily_water_goal", dailyWaterGoal)
      }
      val pendingActionIntentMug = PendingIntent.getBroadcast(context,2, addWaterIntentMug, PendingIntent.FLAG_UPDATE_CURRENT)
      builder?.addAction(R.drawable.mug_3, "${mugCapacity}${waterUnit}", pendingActionIntentMug)
    }
    if(bottleCapacity != null && bottleCapacity != 0) {
      val addWaterIntentBottle = Intent(context, AddWaterReceiver::class.java).apply {
        putExtra("value", bottleCapacity)
        putExtra("container", Container.BOTTLE)
        putExtra("daily_water_goal", dailyWaterGoal)
      }
      val pendingActionIntentBottle = PendingIntent.getBroadcast(context,3, addWaterIntentBottle, PendingIntent.FLAG_UPDATE_CURRENT)
      builder?.addAction(R.drawable.bottle_4, "${bottleCapacity}${waterUnit}", pendingActionIntentBottle)
    }

    return builder
  }

  companion object {
    fun setReminder(
      time:Long,
      reminderPeriodStart:String,
      reminderPeriodEnd:String,
      reminderGap:Int,
      glassCapacity:Int,
      mugCapacity:Int,
      bottleCapacity:Int,
      channelId:String,
      waterUnit:String,
      dailyWaterGoal:Int,
      remindAfterGoalAchieved:Boolean,
      context: Context
    ) {
      if(reminderGap == ReminderGap.DONT_REMIND) {
        cancelReminder(context)
        return
      }
      val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
      val intent = Intent(context, ReminderReceiver::class.java)
      //pass data to broadcast receiver using intent extras
      //As broadcast receiver only survives for a short time reading app data from datastore is not feasable
      //Could have used workmanager but that does not guarantee task completion at exact time
      intent.putExtra("reminder_period_start",reminderPeriodStart)
      intent.putExtra("reminder_period_end",reminderPeriodEnd)
      intent.putExtra("reminder_gap",reminderGap)
      intent.putExtra("glass_capacity",glassCapacity)
      intent.putExtra("mug_capacity",mugCapacity)
      intent.putExtra("bottle_capacity",bottleCapacity)
      intent.putExtra("channel_id",channelId)
      intent.putExtra("water_unit",waterUnit)
      intent.putExtra("dail_water_goal",dailyWaterGoal)
      intent.putExtra("remind_after_goal_achieved",remindAfterGoalAchieved)
      val pendingIntent =
        PendingIntent.getBroadcast(context, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT)
      alarmManager.setInexactRepeating(
        AlarmManager.RTC_WAKEUP,
        time,
        reminderGap.toLong(),
        pendingIntent
      )
    }

    private fun cancelReminder(
      context: Context
    ) {
      val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
      val intent = Intent(context, ReminderReceiver::class.java)
      val pendingIntent =
        PendingIntent.getBroadcast(context, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT)
      alarmManager.cancel(pendingIntent)
      Log.d("TAG", "cancelReminder: Reminder Cancelled")
    }
  }
}