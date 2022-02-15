package com.sameep.watertracker.ui.screens.edithistory

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sameep.watertracker.PreferenceDataStoreViewModel
import com.sameep.watertracker.RoomDatabaseViewModel
import com.sameep.watertracker.data.models.DailyWaterRecord
import com.sameep.watertracker.ui.screens.hometab.components.DrinkLogsList
import com.sameep.watertracker.ui.screens.hometab.components.dialogs.BeverageDialog
import com.sameep.watertracker.ui.screens.hometab.components.dialogs.CustomAddWaterDialog
import com.sameep.watertracker.ui.screens.edithistory.components.EditHistoryFloatingActionButton
import com.sameep.watertracker.ui.screens.edithistory.components.RenderCalendar
import com.sameep.watertracker.ui.screens.edithistory.components.RenderSelectedDate
import com.sameep.watertracker.utils.Beverages
import com.sameep.watertracker.ui.screens.edithistory.components.SelectedHistoryWaterAmount
import com.sameep.watertracker.utils.DateString
import com.sameep.watertracker.utils.RecommendedWaterIntake
import com.sameep.watertracker.utils.Units

@Composable
fun EditHistory(
  roomDatabaseViewModel: RoomDatabaseViewModel,
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel
){
  val (showCustomAddWaterDialog, setShowCustomAddWaterDialog) =  remember { mutableStateOf(false) }
  val (showBeverageDialog, setShowBeverageDialog) =  remember { mutableStateOf(false) }
  val scrollState = rememberScrollState()
  val todaysDate = DateString.getTodaysDate()
  var selectedDate by remember{ mutableStateOf(todaysDate) }
  val setSelectedDate = {date:String -> selectedDate = date}
  val selectedDrinkLogList = roomDatabaseViewModel.selectedHistoryDrinkLogs.collectAsState()
  val selectedWaterRecord = roomDatabaseViewModel.selectedHistoryWaterRecord.collectAsState()
  val waterUnit = preferenceDataStoreViewModel.waterUnit.collectAsState(initial = Units.OZ)
  val firstWaterDataDate = preferenceDataStoreViewModel.firstWaterDataDate.collectAsState(initial = DateString.NOT_SET)
  val dailyWaterGoal = preferenceDataStoreViewModel.dailyWaterGoal.collectAsState(initial = RecommendedWaterIntake.NOT_SET)
  val beverageName = preferenceDataStoreViewModel.beverage.collectAsState(initial = Beverages.DEFAULT)
  val setBeverageName = { it:String -> preferenceDataStoreViewModel.setBeverage(it) }
  val beverage = roomDatabaseViewModel.beverage.collectAsState()

  LaunchedEffect(key1 = beverageName.value) {
    roomDatabaseViewModel.getBeverage(beverageName.value)
  }

  LaunchedEffect(
    key1 = selectedDate
  ){
    roomDatabaseViewModel.getSelectedHistoryWaterRecord(selectedDate)
    roomDatabaseViewModel.getSelectedHistoryDrinkLogs(selectedDate)
  }

  LaunchedEffect(
    key1 = selectedDrinkLogList.value
  ){
    roomDatabaseViewModel.getSelectedHistoryWaterRecord(selectedDate)
  }

  LaunchedEffect(
    key1 = selectedWaterRecord.value
  ){
    if(selectedWaterRecord.value === null) {
      roomDatabaseViewModel.insertDailyWaterRecord(
        DailyWaterRecord(
          date = selectedDate,
          goal = dailyWaterGoal.value
        )
      )
      roomDatabaseViewModel.getSelectedHistoryWaterRecord(selectedDate)
    }
  }

  Scaffold(
    floatingActionButton = {
      EditHistoryFloatingActionButton(
        scrollState = scrollState,
        setShowCustomAddWaterDialog = setShowCustomAddWaterDialog
      )
   },
    floatingActionButtonPosition = FabPosition.Center
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(scrollState)
    ){
      RenderCalendar(
        setSelectedDate = setSelectedDate,
        firstWaterDataDate = firstWaterDataDate.value,
        todaysDate = todaysDate
      )
      RenderSelectedDate(
        selectedDate = selectedDate
      )
      Column(
        modifier = Modifier.animateContentSize()
      ) {
        Log.d("TAG", "HistoryTab: $selectedDate ${selectedWaterRecord.value}")
        if(selectedWaterRecord.value != null) {
          SelectedHistoryWaterAmount(
            currWaterAmount = selectedWaterRecord.value!!.currWaterAmount,
            goal = selectedWaterRecord.value!!.goal,
            waterUnit = waterUnit.value
          )
        }

        if(selectedWaterRecord.value != null && selectedDrinkLogList.value != null) {
          DrinkLogsList(
            drinkLogsList = selectedDrinkLogList.value!!,
            roomDatabaseViewModel = roomDatabaseViewModel,
            waterUnit = waterUnit.value,
            dailyWaterRecord = selectedWaterRecord.value!!
          )
        }

        Spacer(modifier = Modifier.height(96.dp))
      }
    }

    if(showCustomAddWaterDialog) {
      CustomAddWaterDialog(
        waterUnit = waterUnit.value,
        setShowCustomAddWaterDialog = setShowCustomAddWaterDialog,
        roomDatabaseViewModel = roomDatabaseViewModel,
        dailyWaterRecord = selectedWaterRecord.value!!,
        selectedDate = selectedDate,
        beverage = beverage.value,
        showBeverageButton = true,
        setShowBeverageDialog = setShowBeverageDialog
      )
    }
    if(showBeverageDialog){
      BeverageDialog(
        setShowBeverageDialog = setShowBeverageDialog,
        setSelectedBeverage = setBeverageName,
        selectedBeverage = beverageName.value,
        roomDatabaseViewModel = roomDatabaseViewModel
      )
    }
  }
}