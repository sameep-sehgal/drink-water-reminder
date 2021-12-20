package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentResolver
import android.content.Context
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.viewpager2.adapter.StatefulAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.data.models.DailyWaterRecord
import com.example.myapplication.remindernotification.CHANNEL_ID
import com.example.myapplication.remindernotification.ReminderReceiver
import com.example.myapplication.ui.components.DisplayTabLayout
import com.example.myapplication.ui.components.Tabs
import com.example.myapplication.ui.screens.collectuserdata.CollectUserData
import com.example.myapplication.ui.screens.hometab.HomeTab
import com.example.myapplication.ui.screens.hometab.HomeTabViewModel
import com.example.myapplication.ui.screens.settingstab.SettingsTab
import com.example.myapplication.ui.theme.ApplicationTheme
import com.example.myapplication.utils.RecommendedWaterIntake
import com.example.myapplication.utils.ReminderGap
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@ExperimentalPagerApi
@AndroidEntryPoint //This annotation gives access to Hilt dependencies in the composables
class MainActivity : ComponentActivity() {
  private val TAG = MainActivity::class.java.simpleName
  private val preferenceDataStoreViewModel: PreferenceDataStoreViewModel by viewModels()
  private val homeTabViewModel: HomeTabViewModel by viewModels()
  private var notificationManager: NotificationManager? = null

  @ExperimentalFoundationApi
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//    notificationManager?.deleteNotificationChannel(CHANNEL_ID)
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
                      0 -> HomeTab()
                      1 -> HistoryTab()
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
    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is new and not in the support library
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + applicationContext.packageName + "/" + R.raw.water_drop)
      val attributes = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
        .build()
      val name = getString(R.string.app_name)
      val descriptionText = getString(R.string.app_name)
      val importance = NotificationManager.IMPORTANCE_HIGH
      val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
        description = descriptionText
        setSound(sound,attributes)
      }

//      channel.setSound(sound,attributes)
      // Register the channel with the system

      notificationManager?.createNotificationChannel(channel)
    }
  }
}
