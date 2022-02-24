package com.sameep.watertracker.ui.screens.hometab.components.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sameep.watertracker.RoomDatabaseViewModel
import com.sameep.watertracker.data.models.DailyWaterRecord
import com.sameep.watertracker.ui.components.dialogs.ShowDialog
import com.sameep.watertracker.ui.components.selectors.WaterQuantityPicker

@Composable
fun SetWaterGoalDialog(
  setShowSetTodaysGoalDialog:(Boolean)->Unit,
  roomDatabaseViewModel: RoomDatabaseViewModel,
  dailyWaterRecord: DailyWaterRecord,
  waterUnit:String,
  recommendedWaterIntake: Int
){
  val title = "Set Today's Water Goal"
  var amount by remember { mutableStateOf(dailyWaterRecord.goal) }
  val setAmount = {value:Int -> amount = value}

  ShowDialog(
    title = title,
    content = {
      SetTodaysGoalDialogContent(
        amount = amount,
        setAmount = setAmount,
        waterUnit = waterUnit,
        recommendedWaterIntake = recommendedWaterIntake
      )
    },
    setShowDialog = setShowSetTodaysGoalDialog,
    onConfirmButtonClick = {
      roomDatabaseViewModel.updateDailyWaterRecord(
        DailyWaterRecord(
          date = dailyWaterRecord.date,
          goal = amount,
          currWaterAmount = dailyWaterRecord.currWaterAmount
        )
      )
    }
  )
}

@Composable
fun SetTodaysGoalDialogContent(
  amount:Int,
  setAmount:(Int) -> Unit,
  waterUnit: String,
  recommendedWaterIntake: Int
){
  Column {
    Text(
      text = "Reset To Recommended Value($recommendedWaterIntake$waterUnit)",
      color = MaterialTheme.colors.primary,
      textDecoration = TextDecoration.Underline,
      fontSize = 14.sp,
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable {
          setAmount(recommendedWaterIntake)
        },
      textAlign = TextAlign.Center
    )
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center,
      modifier = Modifier.fillMaxWidth().padding(2.dp)
    ) {
      WaterQuantityPicker(
        waterUnit = waterUnit,
        amount = amount,
        setAmount = setAmount,
        waterGoalPicker = true
      )
      Text(
        text = waterUnit,
        modifier = Modifier.padding(8.dp),
        color = MaterialTheme.colors.onSurface
      )
    }
  }
}