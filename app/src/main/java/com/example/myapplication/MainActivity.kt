package com.example.myapplication

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.remindernotification.CHANNEL_ID
import com.example.myapplication.remindernotification.ReminderReceiver
import com.example.myapplication.ui.components.DisplayTabLayout
import com.example.myapplication.ui.theme.ApplicationTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagerApi
@AndroidEntryPoint //This annotation gives access to Hilt dependencies in the composables
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
//                    createNotificationChannel()
                    DisplayTabLayout()
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