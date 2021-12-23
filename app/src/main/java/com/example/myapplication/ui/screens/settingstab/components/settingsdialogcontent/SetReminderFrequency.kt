package com.example.myapplication.ui.screens.settingstab.components.settingsdialogcontent

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.remindernotification.ReminderReceiver
import com.example.myapplication.ui.components.ShowDialog
import com.example.myapplication.ui.theme.Typography
import com.example.myapplication.utils.ReminderFrequencyOptions
import com.example.myapplication.utils.Settings
import java.util.*

@Composable
fun SetReminderFrequencySettingDialog(
  reminderGap: Int,
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  setShowDialog:(Boolean) -> Unit,
  reminderPeriodStart: String,
  reminderPeriodEnd: String
) {
  var selectedReminderGap by  remember { mutableStateOf(reminderGap) }
  val setSelectedReminderGap = { timeGap:Int ->
    selectedReminderGap = timeGap
  }
  val scrollState = rememberScrollState()
  val context = LocalContext.current

  ShowDialog(
    title = "Set ${Settings.REMINDER_FREQUENCY}",
    content = {
      Column(
        modifier = Modifier.verticalScroll(scrollState)
      ) {
        ReminderFrequencyOptions.OPTIONS.forEach {
          Row(
            modifier = Modifier.clickable {
              setSelectedReminderGap(it["gap"].toString().toInt())
              Log.d("TAG", "GetReminderFrequency: $selectedReminderGap")
            }.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Checkbox(
              checked = selectedReminderGap == it["gap"].toString().toInt(),
              onCheckedChange = { value ->
                setSelectedReminderGap(it["gap"].toString().toInt())
              }
            )
            Text(
              text = it["text"].toString(),
              style = Typography.subtitle1
            )
          }
        }
      }
    },
    setShowDialog = setShowDialog,
    onConfirmButtonClick = {
      preferenceDataStoreViewModel.setReminderGap(selectedReminderGap)
      val calendar = Calendar.getInstance()
      calendar.add(Calendar.MILLISECOND, selectedReminderGap)
      ReminderReceiver.setReminder(
        time = calendar.timeInMillis,
        reminderPeriodStart = reminderPeriodStart,
        reminderPeriodEnd = reminderPeriodEnd,
        reminderGap = selectedReminderGap,
        context = context
      )
    }
  )
}