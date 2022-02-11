package com.example.myapplication

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentResolver
import android.content.Context
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import dagger.hilt.android.AndroidEntryPoint
import com.example.myapplication.remindernotification.NOTIFICATION_CHANNEL
import com.example.myapplication.ui.CollectUserData
import com.example.myapplication.ui.MainAppContent

@AndroidEntryPoint //This annotation gives access to Hilt dependencies in the composables
class MainActivity : ComponentActivity() {
  private val preferenceDataStoreViewModel: PreferenceDataStoreViewModel by viewModels()
  private val roomDatabaseViewModel: RoomDatabaseViewModel by viewModels()
  private var notificationManager: NotificationManager? = null

  @RequiresApi(Build.VERSION_CODES.O)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//    notificationManager?.deleteNotificationChannel(CHANNEL_ID_4)
//    notificationManager?.deleteNotificationChannel(CHANNEL_ID_5)
    notificationManager?.notificationChannels?.forEach {
      notificationManager?.deleteNotificationChannel(it.id)
    }
    createNotificationChannel()
    preferenceDataStoreViewModel.isUserInfoCollected.observe(this){
      if(it == true) {
        setContent {
          MainAppContent(
            roomDatabaseViewModel = roomDatabaseViewModel,
            preferenceDataStoreViewModel = preferenceDataStoreViewModel
          )
        }
      }else{
        setContent {
          CollectUserData(
            roomDatabaseViewModel = roomDatabaseViewModel,
            preferenceDataStoreViewModel = preferenceDataStoreViewModel
          )
        }
      }
    }
  }

  override fun onResume() {
    super.onResume()
    notificationManager?.cancelAll()
    roomDatabaseViewModel.refreshData()
  }

  private fun createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val name = "Drink Water Reminder"
      val descriptionText = "Drink Water Reminder"
      val importance = NotificationManager.IMPORTANCE_HIGH
      val channel = NotificationChannel(NOTIFICATION_CHANNEL, name, importance).apply {
        description = descriptionText
        lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        val sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + applicationContext.packageName + "/" + R.raw.water)
        val attributes = AudioAttributes.Builder()
          .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
          .setUsage(AudioAttributes.USAGE_NOTIFICATION)
          .build()
        setSound(sound,attributes)
      }

      notificationManager?.createNotificationChannel(channel)
    }
  }
}
