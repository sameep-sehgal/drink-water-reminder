package com.example.myapplication.ui.screens.hometab.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.data.models.DrinkLogs
import com.example.myapplication.RoomDatabaseViewModel
import com.example.myapplication.data.models.DailyWaterRecord
import com.example.myapplication.ui.screens.hometab.components.dialogs.EditDrinkLogDialog
import com.example.myapplication.ui.theme.Typography
import com.example.myapplication.utils.Beverages
import com.example.myapplication.utils.Container
import com.example.myapplication.utils.TimeString

@Composable
fun DrinkLogsList(
  drinkLogsList: List<DrinkLogs>,
  roomDatabaseViewModel: RoomDatabaseViewModel,
  waterUnit:String,
  dailyWaterRecord: DailyWaterRecord
){
  val (showEditDrinkLogDialog, setShowEditDrinkLogDialog) =  remember { mutableStateOf(false) }
  var selectedDrinkLog by remember { mutableStateOf(DrinkLogs(amount = 0, icon = 0, beverage = Beverages.DEFAULT)) }
  val setSelectedDrinkLog = { drinkLog:DrinkLogs -> selectedDrinkLog = drinkLog }

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