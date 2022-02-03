package com.example.myapplication.remindernotification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.example.myapplication.data.models.DailyWaterRecord
import com.example.myapplication.data.models.DrinkLogs
import com.example.myapplication.data.roomdatabase.WaterDatabase
import com.example.myapplication.utils.Beverages
import kotlinx.coroutines.*

class AddWaterReceiver: BroadcastReceiver() {

  private val TAG = ReminderReceiver::class.java.simpleName

  @DelicateCoroutinesApi
  override fun onReceive(context: Context?, intent: Intent?) {
    if (context != null) {
      NotificationManagerCompat.from(context).cancel(TEST_NOTIFICATION_ID)
    }
    GlobalScope.launch (Dispatchers.Main){
      if (context != null) {
        val amount = intent?.getIntExtra("value", 0)
        val dailyWaterGoal = intent?.getIntExtra("daily_water_goal", 0)
        val container = intent?.getIntExtra("container", 0)
        if(amount != null && amount != 0) {
          val db = WaterDatabase.getInstance(context).waterDatabaseDao()
          var todaysWaterRecord = withContext(Dispatchers.Default) { db.getDailyWaterRecordWithoutFlow() }
          Log.d(TAG, "onAddWaterReceiver: $todaysWaterRecord")
          if(todaysWaterRecord === null){
            //Day changes after notification is shown
            dailyWaterGoal?.let { DailyWaterRecord(goal = it) }
              ?.let { db.insertDailyWaterRecord(it) }
            todaysWaterRecord = withContext(Dispatchers.Default) { db.getDailyWaterRecordWithoutFlow() }
          }
          //TODO("Handle date change here")
          container?.let { DrinkLogs(amount = amount, icon = Beverages.DEFAULT_ICON, beverage = Beverages.DEFAULT) }?.let { db.insertDrinkLog(it) }
          db.updateDailyWaterRecord(DailyWaterRecord(
            date = todaysWaterRecord.date,
            goal = todaysWaterRecord.goal,
            currWaterAmount = todaysWaterRecord.currWaterAmount + amount
          ))
          Log.d(TAG, "onReceive: Successfully added water intake $amount ${todaysWaterRecord.date} ${todaysWaterRecord.goal} ${todaysWaterRecord.currWaterAmount}")
        }
      }
    }
  }

}