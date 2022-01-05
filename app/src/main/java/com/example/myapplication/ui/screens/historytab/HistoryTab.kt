package com.example.myapplication.ui.screens.historytab

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.R
import com.example.myapplication.RoomDatabaseViewModel
import com.example.myapplication.ui.screens.historytab.components.DataGraph
import com.example.myapplication.ui.screens.hometab.components.DrinkLogsList
import com.example.myapplication.ui.screens.hometab.components.TodaysWaterRecord
import com.example.myapplication.ui.screens.hometab.components.buttons.BackToTopButton
import com.example.myapplication.ui.screens.hometab.components.buttons.CustomAddWaterButton
import com.example.myapplication.ui.theme.SelectedItemColor
import com.example.myapplication.ui.theme.SettingsSubheadingBg
import com.example.myapplication.utils.DateString
import com.example.myapplication.utils.Units

@ExperimentalFoundationApi
@Composable
fun HistoryTab(
  roomDatabaseViewModel: RoomDatabaseViewModel,
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel
){
  val (showCustomAddWaterDialog, setShowCustomAddWaterDialog) =  remember { mutableStateOf(false) }
  val scrollState = rememberScrollState()
  var selectedDate by remember{ mutableStateOf(DateString.getTodaysDate()) }
  val setSelectedDate = {date:String -> selectedDate = date}
  val selectedDrinkLogList = roomDatabaseViewModel.selectedHistoryDrinkLogs.collectAsState()
  val selectedWaterRecord = roomDatabaseViewModel.selectedHistoryWaterRecord.collectAsState()
  LaunchedEffect(key1 = selectedDate){
    roomDatabaseViewModel.getSelectedHistoryDrinkLogs(selectedDate)
    roomDatabaseViewModel.getSelectedHistoryWaterRecord(selectedDate)
  }
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
      DataGraph(
        roomDatabaseViewModel,
        preferenceDataStoreViewModel,
        setSelectedDate,
        selectedDate
      )
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .background(color = SettingsSubheadingBg()),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          painter = painterResource(R.drawable.history_icon_white),
          contentDescription = "History Icon",
          modifier = Modifier.padding(8.dp)
        )
        Text(text = "${DateString.clipToMMDD(selectedDate)} Record")
      }
      Column {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ){
          TodaysWaterRecord(
            currWaterAmount = selectedWaterRecord.value.currWaterAmount,
            goal = selectedWaterRecord.value.goal,
            waterUnit = Units.ML
          )
        }
        DrinkLogsList(
          drinkLogsList = selectedDrinkLogList.value,
          roomDatabaseViewModel = roomDatabaseViewModel,
          waterUnit = Units.ML,
          dailyWaterRecord = selectedWaterRecord.value
        )

        Spacer(modifier = Modifier.height(96.dp))
      }
    }
  }
}