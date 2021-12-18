package com.example.myapplication

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.data.models.DailyWaterRecord
import com.example.myapplication.remindernotification.CHANNEL_ID
import com.example.myapplication.remindernotification.ReminderReceiver
import com.example.myapplication.ui.components.DisplayTabLayout
import com.example.myapplication.ui.screens.collectuserdata.CollectUserData
import com.example.myapplication.ui.screens.hometab.HomeTabViewModel
import com.example.myapplication.ui.theme.ApplicationTheme
import com.example.myapplication.utils.RecommendedWaterIntake
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@ExperimentalPagerApi
@AndroidEntryPoint //This annotation gives access to Hilt dependencies in the composables
class MainActivity : ComponentActivity() {
    private val TAG = MainActivity::class.java.simpleName
    private val preferenceDataStoreViewModel: PreferenceDataStoreViewModel by viewModels()
    private val homeTabViewModel: HomeTabViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      createNotificationChannel()
      preferenceDataStoreViewModel.isUserInfoCollected.observe(this){
        if(it == true){
          setContent{
            val setGoal = homeTabViewModel.waterRecord.collectAsState().value.goal
            val goal = preferenceDataStoreViewModel.dailyWaterGoal.collectAsState(initial = 0)
            if(setGoal == RecommendedWaterIntake.NOT_SET){
              homeTabViewModel.updateDailyWaterRecord(
                DailyWaterRecord(goal = goal.value)
              )
            }
            ApplicationTheme {
              Surface() {
                DisplayTabLayout()
              }
            }
          }
        }else{
          setContent {
            ApplicationTheme {
              Surface {
                Log.d(TAG, "onCreate: $TAG")
                CollectUserData(preferenceDataStoreViewModel)
              }
            }
          }
        }
      }
    }

    private fun setReminder(time:Long) {
        val alarmManager =
            getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val intent = Intent(this, ReminderReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(this, 0, intent,0)
        alarmManager?.setInexactRepeating(AlarmManager.RTC_WAKEUP,time,
            AlarmManager.INTERVAL_DAY,pendingIntent)
    }


    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.app_name)
            val descriptionText = getString(R.string.app_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}



@ExperimentalPagerApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ApplicationTheme {
        DisplayTabLayout()
    }
}