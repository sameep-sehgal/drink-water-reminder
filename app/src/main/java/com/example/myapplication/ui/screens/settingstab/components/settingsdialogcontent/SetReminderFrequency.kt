package com.example.myapplication.ui.screens.settingstab.components.settingsdialogcontent

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.ui.components.OptionRow
import com.example.myapplication.ui.components.ShowDialog
import com.example.myapplication.utils.ReminderFrequencyOptions
import com.example.myapplication.utils.ReminderReceiverUtil
import com.example.myapplication.utils.Settings

@Composable
fun SetReminderFrequencySettingDialog(
  reminderGap: Int,
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  setShowDialog:(Boolean) -> Unit,
  context: Context
) {
  var selectedReminderGap by  remember { mutableStateOf(reminderGap) }
  val setSelectedReminderGap = { timeGap:Int ->
    selectedReminderGap = timeGap
  }
  val scrollState = rememberScrollState()

  ShowDialog(
    title = "Set ${Settings.REMINDER_FREQUENCY}",
    content = {
      Column(
        modifier = Modifier.verticalScroll(scrollState)
      ) {
        ReminderFrequencyOptions.OPTIONS.forEach {
          OptionRow(
            selected = selectedReminderGap == it["gap"].toString().toInt(),
            onClick = { setSelectedReminderGap(it["gap"].toString().toInt()) },
            text = it["text"].toString()
          )
        }
      }
    },
    setShowDialog = setShowDialog,
    onConfirmButtonClick = {
      preferenceDataStoreViewModel.setReminderGap(selectedReminderGap)
      ReminderReceiverUtil.setReminder(
        reminderGap = selectedReminderGap,
        context = context
      )
    }
  )
}