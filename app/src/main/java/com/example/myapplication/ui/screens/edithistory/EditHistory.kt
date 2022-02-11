package com.example.myapplication.ui.screens.edithistory

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
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.RoomDatabaseViewModel
import com.example.myapplication.data.models.DailyWaterRecord
import com.example.myapplication.ui.screens.edithistory.components.*
import com.example.myapplication.ui.screens.hometab.components.DrinkLogsList
import com.example.myapplication.ui.screens.hometab.components.dialogs.BeverageDialog
import com.example.myapplication.ui.screens.hometab.components.dialogs.CustomAddWaterDialog
import com.example.myapplication.utils.Beverages
import com.example.myapplication.utils.DateString
import com.example.myapplication.utils.RecommendedWaterIntake
import com.example.myapplication.utils.Units

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