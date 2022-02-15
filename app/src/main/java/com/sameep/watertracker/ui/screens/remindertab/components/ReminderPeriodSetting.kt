package com.sameep.watertracker.ui.screens.remindertab.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sameep.watertracker.utils.Settings

@Composable
fun ReminderPeriodSetting(
  reminderPeriodStart:String,
  reminderPeriodEnd:String,
  setShowDialog :(Boolean) -> Unit,
  setDialogToShow: (String) -> Unit
) {
  val primaryColor = MaterialTheme.colors.primary

  ReminderSettingsCard(
    text = Settings.REMINDER_PERIOD,
    onClick = {
      setShowDialog(true)
      setDialogToShow(Settings.REMINDER_PERIOD)
    }
  ){
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.padding(12.dp,0.dp,8.dp,6.dp)
    ) {
      Text(
        text = reminderPeriodStart,
        color = primaryColor,
        fontSize = 25.sp
      )
      Text(
        text = "-",
        fontSize = 18.sp,
        modifier = Modifier.padding(8.dp,4.dp)
      )
      Text(
        text = reminderPeriodEnd,
        color = primaryColor,
        fontSize = 25.sp
      )
    }
  }
}