package com.example.myapplication.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.RoomDatabaseViewModel
import com.example.myapplication.data.models.DailyWaterRecord
import com.example.myapplication.ui.components.BottomNavBar
import com.example.myapplication.ui.screens.collectuserdata.CollectUserDataContent
import com.example.myapplication.ui.screens.hometab.HomeTab
import com.example.myapplication.ui.screens.remindertab.ReminderTab
import com.example.myapplication.ui.screens.settingstab.SettingsTab
import com.example.myapplication.ui.screens.statstab.StatsTab
import com.example.myapplication.ui.theme.ApplicationTheme
import com.example.myapplication.utils.RecommendedWaterIntake

@Composable
fun MainAppContent(
  roomDatabaseViewModel: RoomDatabaseViewModel,
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel
) {
  val todaysWaterRecord = roomDatabaseViewModel.todaysWaterRecord.collectAsState()
  val goal = preferenceDataStoreViewModel.dailyWaterGoal.collectAsState(initial = 0)
  var selectedTab by remember { mutableStateOf(0) }
  val setSelectedTab = { it:Int -> selectedTab = it }
  val darkTheme = isSystemInDarkTheme()
  if(todaysWaterRecord.value.goal == RecommendedWaterIntake.NOT_SET && goal.value!=0){
    roomDatabaseViewModel.updateDailyWaterRecord(
      DailyWaterRecord(goal = goal.value)
    )
  }
  ApplicationTheme {
    Surface {
      Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.weight(1f)) {
          when(selectedTab) {
            0 -> HomeTab(
              preferenceDataStoreViewModel = preferenceDataStoreViewModel,
              roomDatabaseViewModel = roomDatabaseViewModel,
              todaysWaterRecord = todaysWaterRecord
            )
            1 -> StatsTab(
              roomDatabaseViewModel = roomDatabaseViewModel,
              preferenceDataStoreViewModel = preferenceDataStoreViewModel,
              darkTheme = darkTheme
            )
            2 -> SettingsTab(
              preferenceDataStoreViewModel = preferenceDataStoreViewModel
            )
            3 -> ReminderTab(
              preferenceDataStoreViewModel = preferenceDataStoreViewModel
            )
          }
        }
        BottomNavBar(
          selectedTab = selectedTab,
          setSelectedTab = setSelectedTab,
          darkTheme = darkTheme
        )
      }
    }
  }
}

@Composable
fun CollectUserData(
  roomDatabaseViewModel: RoomDatabaseViewModel,
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel
) {
  ApplicationTheme {
    Surface {
      CollectUserDataContent(
        preferenceDataStoreViewModel = preferenceDataStoreViewModel,
        roomDatabaseViewModel = roomDatabaseViewModel
      )
    }
  }
}