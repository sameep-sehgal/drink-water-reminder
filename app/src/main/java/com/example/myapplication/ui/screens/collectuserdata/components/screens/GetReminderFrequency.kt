package com.example.myapplication.ui.screens.collectuserdata.components.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.ui.components.OptionRow
import com.example.myapplication.utils.ReminderFrequencyOptions

@Composable
fun GetReminderFrequency(
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  reminderGap: Int
) {
  val setSelectedReminderGap = { gapTime:Int ->
    if(gapTime != reminderGap) preferenceDataStoreViewModel.setReminderGap(gapTime)
  }

  Text(
    modifier = Modifier.padding(0.dp,8.dp),
    text = "Select Reminder Frequency",
    fontWeight = FontWeight.Bold,
    textAlign = TextAlign.Center
  )
  Column {
    ReminderFrequencyOptions.OPTIONS.forEach {
      OptionRow(
        selected = reminderGap == it["gap"].toString().toInt(),
        onClick = { setSelectedReminderGap(it["gap"].toString().toInt()) },
        text = it["text"].toString()
      )
    }
  }


}

