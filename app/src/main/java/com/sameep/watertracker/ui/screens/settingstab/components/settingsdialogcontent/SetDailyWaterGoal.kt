package com.sameep.watertracker.ui.screens.settingstab.components.settingsdialogcontent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sameep.watertracker.PreferenceDataStoreViewModel
import com.sameep.watertracker.RoomDatabaseViewModel
import com.sameep.watertracker.data.models.DailyWaterRecord
import com.sameep.watertracker.ui.components.OptionRow
import com.sameep.watertracker.ui.components.ShowDialog
import com.sameep.watertracker.utils.RecommendedWaterIntake
import com.sameep.watertracker.utils.Settings
import com.sameep.watertracker.utils.Units

@Composable
fun SetDailyWaterGoalSettingDialog(
  dailyWaterGoal: Int,
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  setShowDialog:(Boolean) -> Unit,
  waterUnit:String,
  roomDatabaseViewModel: RoomDatabaseViewModel,
  todaysWaterRecord: DailyWaterRecord
) {
  val selectedDailyWaterGoal = remember { mutableStateOf(dailyWaterGoal) }
  val (editTodaysGoal, setEditTodaysGoal) =  remember { mutableStateOf(true) }

  val maxWaterLevel =
    if(waterUnit == Units.ML)
      RecommendedWaterIntake.MAX_WATER_LEVEL_IN_ML
    else
      RecommendedWaterIntake.MAX_WATER_LEVEL_IN_OZ
  val minWaterLevel =
    if(waterUnit == Units.ML)
      RecommendedWaterIntake.MIN_WATER_LEVEL_IN_ML
    else
      RecommendedWaterIntake.MIN_WATER_LEVEL_IN_OZ

  ShowDialog(
    title = "Set ${Settings.DAILY_WATER_GOAL}",
    content = {
      Column {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "${selectedDailyWaterGoal.value}$waterUnit"
          )
        }
        Slider(
          value = selectedDailyWaterGoal.value.toFloat(),
          onValueChange = {
            selectedDailyWaterGoal.value = RecommendedWaterIntake.roundTo10(it.toInt())
          },
          valueRange = minWaterLevel.toFloat()..maxWaterLevel.toFloat()
        )
        OptionRow(
          selected = editTodaysGoal,
          onClick = { setEditTodaysGoal(!editTodaysGoal) },
          text = "Update Today's Goal Also"
        )
      }
    },
    setShowDialog = setShowDialog,
    onConfirmButtonClick = {
      preferenceDataStoreViewModel.setDailyWaterGoal(selectedDailyWaterGoal.value)
      if(editTodaysGoal) {
        roomDatabaseViewModel.updateDailyWaterRecord(
          DailyWaterRecord(
            date = todaysWaterRecord.date,
            currWaterAmount = todaysWaterRecord.currWaterAmount,
            goal = selectedDailyWaterGoal.value
          )
        )
      }
    }
  )
}