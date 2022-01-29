package com.example.myapplication.ui.screens.historytab

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
import com.example.myapplication.ui.screens.historytab.components.*
import com.example.myapplication.ui.screens.hometab.components.DrinkLogsList
import com.example.myapplication.ui.screens.hometab.components.dialogs.CustomAddWaterDialog
import com.example.myapplication.utils.DateString
import com.example.myapplication.utils.Units

@Composable
fun HistoryTab(
  roomDatabaseViewModel: RoomDatabaseViewModel,
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel
){
  //TODO(Need to handle case when waterRecord does not exist for a day. i.e selectedWaterRecord.value == null)
  val (showCustomAddWaterDialog, setShowCustomAddWaterDialog) =  remember { mutableStateOf(false) }
  val scrollState = rememberScrollState()
  val todaysDate = DateString.getTodaysDate()
  var selectedDate by remember{ mutableStateOf(todaysDate) }
  val setSelectedDate = {date:String -> selectedDate = date}
  val selectedDrinkLogList = roomDatabaseViewModel.selectedHistoryDrinkLogs.collectAsState()
  val selectedWaterRecord = roomDatabaseViewModel.selectedHistoryWaterRecord.collectAsState()
  val waterUnit = preferenceDataStoreViewModel.waterUnit.collectAsState(initial = Units.OZ)
  val firstWaterDataDate = preferenceDataStoreViewModel.firstWaterDataDate.collectAsState(initial = DateString.NOT_SET)

  LaunchedEffect(
    key1 = selectedDate,
    key2 = selectedDrinkLogList.value,
    key3 = selectedWaterRecord.value
  ){
    roomDatabaseViewModel.getSelectedHistoryDrinkLogs(selectedDate)
    roomDatabaseViewModel.getSelectedHistoryWaterRecord(selectedDate)
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
        SelectedHistoryWaterAmount(
          currWaterAmount = selectedWaterRecord.value.currWaterAmount,
          goal = selectedWaterRecord.value.goal,
          waterUnit = waterUnit.value
        )

        DrinkLogsList(
          drinkLogsList = selectedDrinkLogList.value,
          roomDatabaseViewModel = roomDatabaseViewModel,
          waterUnit = waterUnit.value,
          dailyWaterRecord = selectedWaterRecord.value
        )

        Spacer(modifier = Modifier.height(96.dp))
      }
    }

    if(showCustomAddWaterDialog) {
      CustomAddWaterDialog(
        waterUnit = waterUnit.value,
        setShowCustomAddWaterDialog = setShowCustomAddWaterDialog,
        roomDatabaseViewModel = roomDatabaseViewModel,
        dailyWaterRecord = selectedWaterRecord.value,
        selectedDate = selectedDate
      )
    }
  }
}