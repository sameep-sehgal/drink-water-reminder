package com.example.myapplication.ui.screens.hometab

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.RoomDatabaseViewModel
import com.example.myapplication.ui.screens.hometab.components.*
import com.example.myapplication.ui.screens.hometab.components.buttons.BackToTopButton
import com.example.myapplication.ui.screens.hometab.components.buttons.CustomAddWaterButton
import com.example.myapplication.ui.screens.hometab.components.buttons.FruitButton
import com.example.myapplication.ui.screens.hometab.components.dialogs.CustomAddWaterDialog
import com.example.myapplication.ui.screens.hometab.components.dialogs.FruitDialog
import com.example.myapplication.ui.screens.hometab.components.dialogs.ResetDialog
import com.example.myapplication.ui.screens.hometab.screens.*
import com.example.myapplication.ui.theme.Typography
import com.example.myapplication.utils.Units

@ExperimentalFoundationApi
@Composable
fun HomeTab(
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  roomDatabaseViewModel: RoomDatabaseViewModel
){
  val drinkLogsList = roomDatabaseViewModel.drinkLogs.collectAsState()
  val todaysWaterRecord = roomDatabaseViewModel.todaysWaterRecord.collectAsState()
  val waterUnit = preferenceDataStoreViewModel.waterUnit.collectAsState(initial = Units.ML)
  val (showWorkoutDialog, setShowWorkoutDialog) =  remember { mutableStateOf(false) }
  val (showWeatherDialog, setShowWeatherDialog) =  remember { mutableStateOf(false) }
  val (showResetDialog, setShowResetDialog) =  remember { mutableStateOf(false) }
  val (showFruitDialog, setShowFruitDialog) =  remember { mutableStateOf(false) }
  val (showCustomAddWaterDialog, setShowCustomAddWaterDialog) =  remember { mutableStateOf(false) }
  val scrollState = rememberScrollState()

  Scaffold(
    floatingActionButton = {
      Column(horizontalAlignment = Alignment.CenterHorizontally){
        BackToTopButton(scrollState)
        CustomAddWaterButton(setShowCustomAddWaterDialog)
      }
    },
    floatingActionButtonPosition = FabPosition.Center
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(scrollState)
    ){

      TodaysWaterRecord(
        currWaterAmount = todaysWaterRecord.value.currWaterAmount,
        goal = todaysWaterRecord.value.goal,
        waterUnit = waterUnit.value
      )

      Row(modifier = Modifier.height(200.dp)) {
        Column(
          Modifier.weight(1f),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          WeatherButton(modifier = Modifier.weight(1f), setShowWeatherDialog = setShowWeatherDialog)

          ResetButton(modifier = Modifier.weight(1f), setShowResetDialog = setShowResetDialog)
        }

        AnimatedWaterGlass(
          todaysWaterRecord.value.currWaterAmount,
          todaysWaterRecord.value.goal
        )

        Column(
          Modifier.weight(1f),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          WorkoutButton(modifier = Modifier.weight(1f), setShowWorkoutDialog = setShowWorkoutDialog)

          FruitButton(modifier = Modifier.weight(1f), setShowFruitDialog = setShowFruitDialog)
        }
      }

      AddWaterButtonsRow(
        waterUnit.value,
        roomDatabaseViewModel,
        todaysWaterRecord.value
      )

      Text(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp, 0.dp, 16.dp, 8.dp),
        text = "Today's Records",
        style = Typography.body1,
        textAlign = TextAlign.Center
      )

      DrinkLogsList(
        drinkLogsList = drinkLogsList.value,
        roomDatabaseViewModel = roomDatabaseViewModel,
        waterUnit = waterUnit.value,
        dailyWaterRecord = todaysWaterRecord.value
      )

      Spacer(modifier = Modifier.height(96.dp))

      if(showWorkoutDialog){
        WorkoutDialog(setShowWorkoutDialog = setShowWorkoutDialog)
      }
      if(showWeatherDialog){
        WeatherDialog(setShowWeatherDialog = setShowWeatherDialog)
      }
      if(showResetDialog){
        ResetDialog(
          setShowResetDialog = setShowResetDialog,
          roomDatabaseViewModel = roomDatabaseViewModel,
          drinkLogs = drinkLogsList.value,
          dailyWaterRecord = todaysWaterRecord.value
        )
      }
      if(showFruitDialog){
        FruitDialog(setShowFruitDialog = setShowFruitDialog)
      }
      if(showCustomAddWaterDialog){
        CustomAddWaterDialog(
          waterUnit = waterUnit.value,
          setShowCustomAddWaterDialog = setShowCustomAddWaterDialog,
          dailyWaterRecord = todaysWaterRecord.value,
          roomDatabaseViewModel = roomDatabaseViewModel
        )
      }
    }
  }
}
