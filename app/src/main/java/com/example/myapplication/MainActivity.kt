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
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.myapplication.data.models.DailyWaterRecord
import com.example.myapplication.remindernotification.CHANNEL_ID
import com.example.myapplication.ui.components.DisplayTabLayout
import com.example.myapplication.ui.components.Tabs
import com.example.myapplication.ui.screens.collectuserdata.CollectUserData
import com.example.myapplication.ui.screens.hometab.HomeTab
import com.example.myapplication.ui.screens.settingstab.SettingsTab
import com.example.myapplication.ui.theme.ApplicationTheme
import com.example.myapplication.utils.RecommendedWaterIntake
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.foundation.isSystemInDarkTheme
import com.example.myapplication.remindernotification.CHANNEL_ID_2
import com.example.myapplication.ui.screens.historytab.HistoryTab
import com.example.myapplication.utils.AppTheme


@ExperimentalPagerApi
@AndroidEntryPoint //This annotation gives access to Hilt dependencies in the composables
class MainActivity : ComponentActivity() {
  private val TAG = MainActivity::class.java.simpleName
  private val preferenceDataStoreViewModel: PreferenceDataStoreViewModel by viewModels()
  private val roomDatabaseViewModel: RoomDatabaseViewModel by viewModels()
  private var notificationManager: NotificationManager? = null

  @RequiresApi(Build.VERSION_CODES.O)
  @ExperimentalFoundationApi
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//    notificationManager?.deleteNotificationChannel(CHANNEL_ID)
    createNotificationChannel()
    notificationManager?.notificationChannels?.forEach {
      Log.d(TAG, "onCreate: $it")
    }
    preferenceDataStoreViewModel.isUserInfoCollected.observe(this){
      if(it == true){
        setContent{
          val setGoal = roomDatabaseViewModel.todaysWaterRecord.collectAsState().value.goal
          val goal = preferenceDataStoreViewModel.dailyWaterGoal.collectAsState(initial = 0)
          val appTheme = preferenceDataStoreViewModel.appTheme.collectAsState(initial = AppTheme.DEFAULT)
          var darkTheme = false
          when(appTheme.value) {
            AppTheme.DARK -> darkTheme = true
            AppTheme.LIGHT -> darkTheme = false
            AppTheme.DEFAULT -> darkTheme = isSystemInDarkTheme()
          }
          if(setGoal == RecommendedWaterIntake.NOT_SET){
            roomDatabaseViewModel.updateDailyWaterRecord(
              DailyWaterRecord(goal = goal.value)
            )
          }
          ApplicationTheme(
            darkTheme = darkTheme
          ) {
            Surface {
              val pagerState = rememberPagerState()
              Column {
                DisplayTabLayout(pagerState)
                //Pager used to swipe between different tabs
                HorizontalPager(
                  count = Tabs.values().size,
                  state = pagerState,
                  verticalAlignment = Alignment.Top
                ) { index->
                  Log.d(TAG, "onCreateTabLayout: $index  ${pagerState.currentPage}")
                  Column(modifier = Modifier.fillMaxSize()) {
                    when(index) {
                      0 -> HomeTab(
                        preferenceDataStoreViewModel,
                        roomDatabaseViewModel
                      )
                      1 -> HistoryTab(
                        roomDatabaseViewModel,
                        preferenceDataStoreViewModel
                      )
                      2 -> SettingsTab(preferenceDataStoreViewModel)
                    }
                  }
                }
              }
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

  private fun createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//      val sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + applicationContext.packageName + "/" + "raw/water_drop")
      val sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + applicationContext.packageName + "/" + R.raw.water_drop)
      Log.d(TAG, "createNotificationChannel: ${sound}")
//      val attributes = AudioAttributes.Builder()
//        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
//        .build()
      val attributes = AudioAttributes.Builder()
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .setUsage(AudioAttributes.USAGE_ALARM)
        .build()
      val name = getString(R.string.app_name)
      val descriptionText = getString(R.string.app_name)
      val importance = NotificationManager.IMPORTANCE_HIGH
      val channel = NotificationChannel(CHANNEL_ID_2, name, importance).apply {
        description = descriptionText
        setSound(sound,attributes)
      }
      channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
//      channel.setSound(sound,attributes)

      notificationManager?.createNotificationChannel(channel)
    }
  }
}
