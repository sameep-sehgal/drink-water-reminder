package com.sameep.watertracker

import android.app.NotificationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import com.sameep.watertracker.ui.CollectUserData
import com.sameep.watertracker.ui.MainAppContent
import com.sameep.watertracker.utils.ReminderReceiverUtil

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  private val preferenceDataStoreViewModel: PreferenceDataStoreViewModel by viewModels()
  private val roomDatabaseViewModel: RoomDatabaseViewModel by viewModels()
  private var notificationManager: NotificationManager? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    ReminderReceiverUtil.createNotificationChannel(this)
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
}
