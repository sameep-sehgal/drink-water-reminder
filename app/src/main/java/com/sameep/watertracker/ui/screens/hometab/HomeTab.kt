package com.sameep.watertracker.ui.screens.hometab

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sameep.watertracker.PreferenceDataStoreViewModel
import com.sameep.watertracker.RoomDatabaseViewModel
import com.sameep.watertracker.data.models.DailyWaterRecord
import com.sameep.watertracker.ui.screens.hometab.components.buttons.BeverageButton
import com.sameep.watertracker.utils.RecommendedWaterIntake
import com.sameep.watertracker.utils.Beverages
import com.sameep.watertracker.utils.Units
import com.sameep.watertracker.ui.screens.hometab.components.AddWaterButtonsRow
import com.sameep.watertracker.ui.screens.hometab.components.AnimatedHeartBrain
import com.sameep.watertracker.ui.screens.hometab.components.DrinkLogsList
import com.sameep.watertracker.ui.screens.hometab.components.TodaysWaterRecord
import com.sameep.watertracker.ui.screens.hometab.components.dialogs.AppUpdateDialog
import com.sameep.watertracker.ui.screens.hometab.components.dialogs.BeverageDialog
import com.sameep.watertracker.ui.screens.hometab.components.dialogs.CustomAddWaterDialog
import com.sameep.watertracker.ui.screens.hometab.components.dialogs.SetWaterGoalDialog

@Composable
fun HomeTab(
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  roomDatabaseViewModel: RoomDatabaseViewModel,
  todaysWaterRecord: State<DailyWaterRecord>
){
  val context = LocalContext.current
  val drinkLogsList = roomDatabaseViewModel.drinkLogs.collectAsState()
  val waterUnit = preferenceDataStoreViewModel.waterUnit.collectAsState(initial = Units.ML)
  val recommendedWaterIntake = preferenceDataStoreViewModel.recommendedWaterIntake.collectAsState(initial = RecommendedWaterIntake.NOT_SET)
  val beverageName = preferenceDataStoreViewModel.beverage.collectAsState(initial = Beverages.DEFAULT)
  val (showBeverageDialog, setShowBeverageDialog) =  remember { mutableStateOf(false) }
  val (showAppUpdateDialog, setShowAppUpdateDialog) =  remember { mutableStateOf(false) }
  val (showSetTodaysGoalDialog, setShowSetTodaysGoalDialog) =  remember { mutableStateOf(false) }
  val (showCustomAddWaterDialog, setShowCustomAddWaterDialog) =  remember { mutableStateOf(false) }
  val scrollState = rememberScrollState()
  var showAllDrinkLogs by remember{ mutableStateOf(false) }
  val beverage = roomDatabaseViewModel.beverage.collectAsState()
  val setBeverageName = { it:String -> preferenceDataStoreViewModel.setBeverage(it) }

  LaunchedEffect(key1 = beverageName.value) {
    roomDatabaseViewModel.getBeverage(beverageName.value)
  }

  Column(horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(scrollState),
    verticalArrangement = Arrangement.Center
  ){

    TodaysWaterRecord(
      currWaterAmount = todaysWaterRecord.value.currWaterAmount,
      goal = todaysWaterRecord.value.goal,
      waterUnit = waterUnit.value,
      setShowSetTodaysGoalDialog = setShowSetTodaysGoalDialog
    )

    Spacer(modifier = Modifier.size(16.dp))

    AnimatedHeartBrain(
      todaysWaterRecord.value.currWaterAmount,
      todaysWaterRecord.value.goal
    )

    Spacer(modifier = Modifier.size(20.dp))

    var columnModifier = Modifier.animateContentSize()

    if(showAllDrinkLogs) columnModifier = columnModifier.height(0.dp)

    Column(
      modifier = columnModifier,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {

      BeverageButton(
        setShowBeverageDialog = setShowBeverageDialog,
        beverage = beverage.value
      )

      Spacer(modifier = Modifier.size(20.dp))

      AddWaterButtonsRow(
        waterUnit = waterUnit.value,
        roomDatabaseViewModel = roomDatabaseViewModel,
        dailyWaterRecord = todaysWaterRecord.value,
        preferenceDataStoreViewModel = preferenceDataStoreViewModel,
        setShowCustomAddWaterDialog = setShowCustomAddWaterDialog,
        beverage = beverage.value,
        mostRecentDrinkLog = if(drinkLogsList.value.isNotEmpty()) drinkLogsList.value[0] else null,
        setShowAppUpdateDialog = setShowAppUpdateDialog,
        context = context
      )

      Spacer(modifier = Modifier.size(12.dp))

    }

    var columnModifier2 = Modifier.animateContentSize()
    val scrollState2 = rememberScrollState()

    if(showAllDrinkLogs)
      columnModifier2 = columnModifier2
        .height(200.dp)
        .verticalScroll(scrollState2)

    /*TODO(Fix this columns initial height equal to 2 drinkLogs)*/
    Column(
      modifier = columnModifier2
    ) {
      if(showAllDrinkLogs) {
        Text(
          modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
          text = "Today's Logs",
          textAlign = TextAlign.Center,
          fontSize = 18.sp
        )
        DrinkLogsList(
          drinkLogsList = drinkLogsList.value,
          roomDatabaseViewModel = roomDatabaseViewModel,
          waterUnit = waterUnit.value,
          dailyWaterRecord = todaysWaterRecord.value
        )
      } else {
        Spacer(modifier = Modifier.size(8.dp))
      }
    }

    Text(
      text =
      if(!showAllDrinkLogs)
        "View Today's Logs"
      else "Collapse",
      color = MaterialTheme.colors.primary,
      fontSize = 13.sp,
      textAlign = TextAlign.Center,
      modifier = Modifier
        .clickable{
          showAllDrinkLogs = !showAllDrinkLogs
        }
    )

    Spacer(modifier = Modifier.size(16.dp))

    //Dialogs Code
    if(showBeverageDialog){
      BeverageDialog(
        setShowBeverageDialog = setShowBeverageDialog,
        setSelectedBeverage = setBeverageName,
        selectedBeverage = beverageName.value,
        roomDatabaseViewModel = roomDatabaseViewModel
      )
    }
    if(showSetTodaysGoalDialog){
      SetWaterGoalDialog(
        setShowSetTodaysGoalDialog = setShowSetTodaysGoalDialog,
        roomDatabaseViewModel = roomDatabaseViewModel,
        dailyWaterRecord = todaysWaterRecord.value,
        waterUnit = waterUnit.value,
        recommendedWaterIntake = recommendedWaterIntake.value
      )
    }
    if(showCustomAddWaterDialog){
      CustomAddWaterDialog(
        waterUnit = waterUnit.value,
        setShowCustomAddWaterDialog = setShowCustomAddWaterDialog,
        dailyWaterRecord = todaysWaterRecord.value,
        roomDatabaseViewModel = roomDatabaseViewModel,
        beverage = beverage.value
      )
    }

    if(showAppUpdateDialog) {
      AppUpdateDialog(
        setShowDialog = setShowAppUpdateDialog,
        context = context
      )
    }
  }
}

