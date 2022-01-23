package com.example.myapplication.ui.screens.settingstab.components.settingsdialogcontent

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.remindernotification.ReminderReceiver
import com.example.myapplication.ui.components.ShowDialog
import com.example.myapplication.ui.components.WaterQuantityPicker
import com.example.myapplication.utils.Container
import com.example.myapplication.utils.Settings
import java.util.*


@Composable
fun SetContainerCapacity(
  container:Int,
  capacity:Int,
  waterUnit:String,
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  setShowDialog:(Boolean) -> Unit,
  reminderGap: Int,
  reminderPeriodStart: String,
  reminderPeriodEnd: String,
  glassCapacity: Int,
  mugCapacity: Int,
  bottleCapacity: Int,
  reminderSound: String,
  dailyWaterGoal: Int,
  remindAfterGoalAchieved: Boolean,
  context: Context
) {
  var selectedCapacity by remember { mutableStateOf(capacity) }
  val setCapacity = {value:Int -> selectedCapacity = value}

  var title = "Container"
  when(container){
    Container.GLASS -> title = Settings.GLASS_CAPACITY
    Container.MUG -> title = Settings.MUG_CAPACITY
    Container.BOTTLE -> title = Settings.BOTTLE_CAPACITY
  }

  ShowDialog(
    title = "Set $title",
    content = {
      //Content is like this fix the bug WaterQuantityPicker moves into the title
      Column(
        modifier = Modifier.background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            modifier = Modifier
              .clickable {
              }
              .padding(1.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "",
              fontSize = 2.sp,
              color = MaterialTheme.colors.onSurface
            )
          }
        }
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {

          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
          ) {
            WaterQuantityPicker(
              waterUnit = waterUnit,
              amount = capacity,
              setAmount = setCapacity
            )
            Text(
              text = waterUnit,
              modifier = Modifier.padding(8.dp),
              color = MaterialTheme.colors.onSurface
            )
          }
        }
      }
    },
    setShowDialog = setShowDialog,
    onConfirmButtonClick = {
      val calendar = Calendar.getInstance()
      calendar.add(Calendar.MILLISECOND, 10000) //TODO(Change back to reminder gap)

      if(container == Container.GLASS){
        preferenceDataStoreViewModel.setGlassCapacity(selectedCapacity)
        ReminderReceiver.setReminder(
          time = calendar.timeInMillis,
          reminderPeriodStart = reminderPeriodStart,
          reminderPeriodEnd = reminderPeriodEnd,
          reminderGap = reminderGap,
          glassCapacity = selectedCapacity,
          mugCapacity = mugCapacity,
          bottleCapacity = bottleCapacity,
          channelId = reminderSound,
          waterUnit = waterUnit,
          dailyWaterGoal = dailyWaterGoal,
          remindAfterGoalAchieved = remindAfterGoalAchieved,
          context = context
        )
      }
      if(container == Container.MUG) {
        preferenceDataStoreViewModel.setMugCapacity(selectedCapacity)
        ReminderReceiver.setReminder(
          time = calendar.timeInMillis,
          reminderPeriodStart = reminderPeriodStart,
          reminderPeriodEnd = reminderPeriodEnd,
          reminderGap = reminderGap,
          glassCapacity = glassCapacity,
          mugCapacity = selectedCapacity,
          bottleCapacity = bottleCapacity,
          channelId = reminderSound,
          waterUnit = waterUnit,
          dailyWaterGoal = dailyWaterGoal,
          remindAfterGoalAchieved = remindAfterGoalAchieved,
          context = context
        )
      }
      if(container == Container.BOTTLE) {
        preferenceDataStoreViewModel.setBottleCapacity(selectedCapacity)
        ReminderReceiver.setReminder(
          time = calendar.timeInMillis,
          reminderPeriodStart = reminderPeriodStart,
          reminderPeriodEnd = reminderPeriodEnd,
          reminderGap = reminderGap,
          glassCapacity = glassCapacity,
          mugCapacity = mugCapacity,
          bottleCapacity = selectedCapacity,
          channelId = reminderSound,
          waterUnit = waterUnit,
          dailyWaterGoal = dailyWaterGoal,
          remindAfterGoalAchieved = remindAfterGoalAchieved,
          context = context
        )
      }
    }
  )
}