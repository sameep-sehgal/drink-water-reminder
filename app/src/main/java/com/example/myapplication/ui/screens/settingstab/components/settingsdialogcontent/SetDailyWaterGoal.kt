package com.example.myapplication.ui.screens.settingstab.components.settingsdialogcontent

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.R
import com.example.myapplication.remindernotification.ReminderReceiver
import com.example.myapplication.ui.components.ShowDialog
import com.example.myapplication.utils.RecommendedWaterIntake
import com.example.myapplication.utils.Settings
import com.example.myapplication.utils.Units
import java.util.*

@Composable
fun SetDailyWaterGoalSettingDialog(
  dailyWaterGoal: Int,
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  setShowDialog:(Boolean) -> Unit,
  recommendedWaterIntake: Int,
  waterUnit:String,
  reminderGap: Int,
  reminderPeriodStart: String,
  reminderPeriodEnd: String,
  glassCapacity: Int,
  mugCapacity: Int,
  bottleCapacity: Int,
  reminderSound: String,
  remindAfterGoalAchieved: Boolean,
  context:Context
) {
  val selectedDailyWaterGoal = remember { mutableStateOf(dailyWaterGoal) }

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
          IconButton(onClick = { selectedDailyWaterGoal.value = recommendedWaterIntake }) {
            Icon(
              painter = painterResource(id = R.drawable.history_icon_white),
              contentDescription = "Reset To Recommended"
            )
          }
        }
        Slider(
          value = selectedDailyWaterGoal.value.toFloat(),
          onValueChange = {
            selectedDailyWaterGoal.value = RecommendedWaterIntake.roundTo10(it.toInt())
          },
          valueRange = minWaterLevel.toFloat()..maxWaterLevel.toFloat()
        )
      }
    },
    setShowDialog = setShowDialog,
    onConfirmButtonClick = {
      val calendar = Calendar.getInstance()
      calendar.add(Calendar.MILLISECOND, reminderGap)
      preferenceDataStoreViewModel.setDailyWaterGoal(selectedDailyWaterGoal.value)
      ReminderReceiver.setReminder(
        time = calendar.timeInMillis,
        reminderPeriodStart = reminderPeriodStart,
        reminderPeriodEnd = reminderPeriodEnd,
        reminderGap = reminderGap,
        glassCapacity = glassCapacity,
        mugCapacity = mugCapacity,
        bottleCapacity = bottleCapacity,
        channelId = reminderSound,
        waterUnit = waterUnit,
        dailyWaterGoal = dailyWaterGoal,
        remindAfterGoalAchieved = remindAfterGoalAchieved,
        context = context
      )
      //TODO("Also Edit today's entry of database")
    }
  )
}