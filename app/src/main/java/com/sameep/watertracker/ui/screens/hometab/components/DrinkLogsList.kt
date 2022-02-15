package com.sameep.watertracker.ui.screens.hometab.components

import androidx.compose.runtime.*
import com.sameep.watertracker.data.models.DrinkLogs
import com.sameep.watertracker.RoomDatabaseViewModel
import com.sameep.watertracker.data.models.DailyWaterRecord
import com.sameep.watertracker.ui.screens.hometab.components.dialogs.EditDrinkLogDialog
import com.sameep.watertracker.utils.Beverages

@Composable
fun DrinkLogsList(
  drinkLogsList: List<DrinkLogs>,
  roomDatabaseViewModel: RoomDatabaseViewModel,
  waterUnit:String,
  dailyWaterRecord: DailyWaterRecord
){
  val (showEditDrinkLogDialog, setShowEditDrinkLogDialog) =  remember { mutableStateOf(false) }
  var selectedDrinkLog by remember { mutableStateOf(DrinkLogs(amount = 0, icon = 0, beverage = Beverages.DEFAULT)) }
  val setSelectedDrinkLog = { drinkLog: DrinkLogs -> selectedDrinkLog = drinkLog }

  if(showEditDrinkLogDialog){
    EditDrinkLogDialog(
      drinkLog = selectedDrinkLog,
      setShowEditDrinkLogDialog = setShowEditDrinkLogDialog,
      roomDatabaseViewModel = roomDatabaseViewModel,
      waterUnit = waterUnit,
      dailyWaterRecord = dailyWaterRecord
    )
  }
  drinkLogsList.forEach {
    DrinkLog(
      drinkLog = it,
      waterUnit = waterUnit,
      setShowEditDrinkLogDialog = setShowEditDrinkLogDialog,
      roomDatabaseViewModel = roomDatabaseViewModel,
      dailyWaterRecord = dailyWaterRecord,
      setSelectedDrinkLog = setSelectedDrinkLog
    )
  }

}