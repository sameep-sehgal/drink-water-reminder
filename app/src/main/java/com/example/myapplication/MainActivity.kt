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
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.models.DailyWaterRecord
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
import com.example.myapplication.remindernotification.CHANNEL_ID_4
import com.example.myapplication.ui.screens.historytab.HistoryTab
import com.example.myapplication.ui.screens.statstab.StatsTab
import com.example.myapplication.utils.ReminderSound
import com.google.accompanist.pager.PagerState


@ExperimentalPagerApi
@AndroidEntryPoint //This annotation gives access to Hilt dependencies in the composables
class MainActivity : ComponentActivity() {
  private val TAG = MainActivity::class.java.simpleName
  private val preferenceDataStoreViewModel: PreferenceDataStoreViewModel by viewModels()
  private val roomDatabaseViewModel: RoomDatabaseViewModel by viewModels()
  private var notificationManager: NotificationManager? = null
  private lateinit var pagerState: PagerState

  @RequiresApi(Build.VERSION_CODES.O)
  @ExperimentalFoundationApi
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//    notificationManager?.deleteNotificationChannel(CHANNEL_ID_3)
    createNotificationChannel(CHANNEL_ID_4)
//    notificationManager?.notificationChannels?.forEach {
//      Log.d(TAG, "onCreate: $it")
//    }
    preferenceDataStoreViewModel.isUserInfoCollected.observe(this){
      if(it == true){
        setContent{
          val setGoal = roomDatabaseViewModel.todaysWaterRecord.collectAsState().value.goal
          val goal = preferenceDataStoreViewModel.dailyWaterGoal.collectAsState(initial = 0)
          if(setGoal == RecommendedWaterIntake.NOT_SET && goal.value!=0){
            Log.d(TAG, "onCreate: ${goal.value}")
            roomDatabaseViewModel.updateDailyWaterRecord(
              DailyWaterRecord(goal = goal.value)
            )
          }
          ApplicationTheme {
            Surface {
              pagerState = rememberPagerState()
              Column {
//                DisplayTabLayout(pagerState)
                BottomNavigation(
                  modifier = Modifier.height(56.dp),
                  backgroundColor = MaterialTheme.colors.primary
                ) {
                  Tabs.values().forEachIndexed { i: Int, it: Tabs ->
                    BottomNavigationItem(
                      selected = pagerState.currentPage == i,
                      onClick = { /*TODO*/ },
                      icon = {Icon(painter = painterResource(id = it.icon), contentDescription = null)},
                      label = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                          Text(
                            text = it.title,
                            modifier = Modifier.padding(2.dp)
                          )
                          if(pagerState.currentPage == i) {
                            Divider(
                              thickness = 2.dp,
                              color = Color.White
                            )
                          }
                        }
                      }
                    )
                  }
                }
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
                      1 -> StatsTab(
                        roomDatabaseViewModel
                      )
                      2 -> HistoryTab(
                        roomDatabaseViewModel,
                        preferenceDataStoreViewModel
                      )
                      3 -> SettingsTab(preferenceDataStoreViewModel)
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

  override fun onResume() {
    super.onResume()
    notificationManager?.cancelAll()
    roomDatabaseViewModel.refreshData()

  }

  private fun createNotificationChannel(channelId: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//      Log.d(TAG, "createNotificationChannel: ${sound}")
      val name = "Drink Water Reminder"
      val descriptionText = "Drink Water Reminder"
      val importance = NotificationManager.IMPORTANCE_HIGH
      val channel = NotificationChannel(channelId, name, importance).apply {
        description = descriptionText
        lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        if(channelId != ReminderSound.DEVICE_DEFAULT) {
          val sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + applicationContext.packageName + "/" + ReminderSound.NAME_VALUE_MAPPER[ReminderSound.WATER_DROP])
          val attributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .build()
          setSound(sound,attributes)
        }
      }

      notificationManager?.createNotificationChannel(channel)
    }
  }
}
