package com.sameep.watertracker.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationManagerCompat
import com.sameep.watertracker.PreferenceDataStoreViewModel
import com.sameep.watertracker.RoomDatabaseViewModel
import com.sameep.watertracker.data.models.DailyWaterRecord
import com.sameep.watertracker.ui.components.BottomNavBar
import com.sameep.watertracker.ui.screens.collectuserdata.CollectUserDataContent
import com.sameep.watertracker.ui.screens.hometab.HomeTab
import com.sameep.watertracker.ui.screens.remindertab.ReminderTab
import com.sameep.watertracker.ui.screens.settingstab.SettingsTab
import com.sameep.watertracker.ui.screens.statstab.StatsTab
import com.sameep.watertracker.ui.theme.ApplicationTheme
import com.sameep.watertracker.utils.RecommendedWaterIntake
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.sameep.watertracker.ui.components.dialogs.RateAppDialog
import com.sameep.watertracker.ui.components.dialogs.SwitchNotificationOnDialog
import com.sameep.watertracker.utils.DateString
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainAppContent(
  roomDatabaseViewModel: RoomDatabaseViewModel,
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel
) {
  val todaysWaterRecord = roomDatabaseViewModel.todaysWaterRecord.collectAsState()
  val goal = preferenceDataStoreViewModel.dailyWaterGoal.collectAsState(initial = 0)
  val switchNotificationOnDialogLastShownDate = preferenceDataStoreViewModel.switchNotificationOnDialogLastShownDate.collectAsState(initial = DateString.getTodaysDate())
  val isRatingDialogShown = preferenceDataStoreViewModel.isRatingDialogShown.collectAsState(initial = true)
  val firstWaterDataDate = preferenceDataStoreViewModel.firstWaterDataDate.collectAsState(initial = DateString.NOT_SET)
  var selectedTab by remember { mutableStateOf(0) }
  val (showSwitchNotificationOnDialog, setShowSwitchNotificationOnDialog) =  remember { mutableStateOf(false) }
  val (showRateAppDialog, setShowRateAppDialog) =  remember { mutableStateOf(false) }
  val setSelectedTab = { it:Int -> selectedTab = it }
  val darkTheme = isSystemInDarkTheme()
  val scope = rememberCoroutineScope()
  val context = LocalContext.current
  val todaysDate = DateString.getTodaysDate()
  val swipeRefreshState = rememberSwipeRefreshState(false)
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
            0 -> {
              SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                  scope.launch {
                    swipeRefreshState.isRefreshing = true
                    roomDatabaseViewModel.refreshData()
                    delay(1000)
                    swipeRefreshState.isRefreshing = false
                  }
                }
              ) {
                HomeTab(
                  preferenceDataStoreViewModel = preferenceDataStoreViewModel,
                  roomDatabaseViewModel = roomDatabaseViewModel,
                  todaysWaterRecord = todaysWaterRecord
                )
              }
            }
            1 -> StatsTab(
              roomDatabaseViewModel = roomDatabaseViewModel,
              preferenceDataStoreViewModel = preferenceDataStoreViewModel,
              darkTheme = darkTheme
            )
            2 -> SettingsTab(
              preferenceDataStoreViewModel = preferenceDataStoreViewModel,
              roomDatabaseViewModel = roomDatabaseViewModel,
              todaysWaterRecord = todaysWaterRecord.value
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

      LaunchedEffect(
        key1 = true,
      ) {
        scope.launch {
          if(
            !NotificationManagerCompat.from(context).areNotificationsEnabled() &&
               switchNotificationOnDialogLastShownDate.value != todaysDate
          ){
            delay(3000)
            setShowSwitchNotificationOnDialog(true)
            preferenceDataStoreViewModel
              .setSwitchNotificationOnDialogLastShownDate(
                todaysDate
              )
          } else {
            if(
              !isRatingDialogShown.value &&
              DateString.calculateDaysDifference(todaysDate, firstWaterDataDate.value) >= 4
            ) {
              delay(3000)
              setShowRateAppDialog(true)
              preferenceDataStoreViewModel.setIsRatingDialogShown(true)
            }
          }
        }
      }

      if(showSwitchNotificationOnDialog) {
        SwitchNotificationOnDialog(
          setShowDialog = setShowSwitchNotificationOnDialog,
          context = context
        )
      }
      if(showRateAppDialog) {
        RateAppDialog(
          setShowDialog = setShowRateAppDialog,
          context = context
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