package com.example.myapplication.ui.screens.settingstab.components.settingsdialogcontent

import android.widget.NumberPicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.ui.components.ShowDialog
import com.example.myapplication.utils.Settings
import com.example.myapplication.utils.TimeString

@Composable
fun SetReminderPeriodSettingDialog(
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  setShowDialog:(Boolean) -> Unit,
  reminderPeriodStart: String,
  reminderPeriodEnd: String,
) {
  var selectedReminderTimingStart by  remember { mutableStateOf(reminderPeriodStart) }
  val setSelectedReminderTimingStart = { time:String ->
    selectedReminderTimingStart = time
  }
  var selectedReminderTimingEnd by  remember { mutableStateOf(reminderPeriodEnd) }
  val setSelectedReminderTimingEnd = { time:String->
    selectedReminderTimingEnd = time
  }

  val startHourChangeListener = NumberPicker.OnValueChangeListener { _, _, newVal ->
    val newTime:String =
      TimeString.HHMMIntToString(newVal,selectedReminderTimingStart.split(":")[1].toInt())
    setSelectedReminderTimingStart(newTime)
  }

  val startMinuteChangeListener = NumberPicker.OnValueChangeListener { _, _, newVal ->
    val newTime:String =
      TimeString.HHMMIntToString(selectedReminderTimingStart.split(":")[0].toInt(),newVal)
    setSelectedReminderTimingStart(newTime)
  }

  val endHourChangeListener = NumberPicker.OnValueChangeListener { _, _, newVal ->
    val newTime:String =
      TimeString.HHMMIntToString(newVal,selectedReminderTimingEnd.split(":")[1].toInt())
    setSelectedReminderTimingEnd(newTime)
  }

  val endMinuteChangeListener = NumberPicker.OnValueChangeListener { _, _, newVal ->
    val newTime:String =
      TimeString.HHMMIntToString(selectedReminderTimingEnd.split(":")[0].toInt(),newVal)
    setSelectedReminderTimingEnd(newTime)
  }

  ShowDialog(
    title = "Set ${Settings.REMINDER_PERIOD}",
    content = {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
      ) {
        Column(
          modifier = Modifier.weight(1f),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
        ) {
          Text(
            text = "Start",
            fontSize = 20.sp
          )
          Row(
            verticalAlignment = Alignment.CenterVertically
          ) {
            AndroidView(
              factory = { context ->
                val numberPicker = NumberPicker(context)
                numberPicker.maxValue = 23
                numberPicker.minValue = 0
                numberPicker.displayedValues = run {
                  val res = Array(24) {""}
                  for (i in 0..23) {
                    if(i < 10) res[i] = "0$i"
                    else res[i] = i.toString()
                  }
                  res
                }
                numberPicker.setOnValueChangedListener(startHourChangeListener)
                numberPicker
              },
              update = { view ->
                view.value = selectedReminderTimingStart.split(":")[0].toInt() //To attach state variable
              }
            )
            Text(text = ":")
            AndroidView(
              factory = { context ->
                val numberPicker = NumberPicker(context)
                numberPicker.maxValue = 59
                numberPicker.minValue = 0
                numberPicker.displayedValues = run {
                  val res = Array(60) {""}
                  for (i in 0..59) {
                    if(i < 10) res[i] = "0$i"
                    else res[i] = i.toString()
                  }
                  res
                }
                numberPicker.setOnValueChangedListener(startMinuteChangeListener)
                numberPicker
              },
              update = { view ->
                view.value = selectedReminderTimingStart.split(":")[1].toInt() //To attach state variable
              }
            )
          }
        }
        Column(
          modifier = Modifier.weight(1f),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
        ) {
          Text(
            text = "End",
            fontSize = 20.sp
          )
          Row(
            verticalAlignment = Alignment.CenterVertically
          ) {
            AndroidView(
              factory = { context ->
                val numberPicker = NumberPicker(context)
                numberPicker.maxValue = 23
                numberPicker.minValue = 0
                numberPicker.displayedValues = run {
                  val res = Array(24) {""}
                  for (i in 0..23) {
                    if(i < 10) res[i] = "0$i"
                    else res[i] = i.toString()
                  }
                  res
                }
                numberPicker.setOnValueChangedListener(endHourChangeListener)
                numberPicker
              },
              update = { view ->
                view.value = selectedReminderTimingEnd.split(":")[0].toInt() //To attach state variable
              }
            )
            Text(text = ":")
            AndroidView(
              factory = { context ->
                val numberPicker = NumberPicker(context)
                numberPicker.maxValue = 59
                numberPicker.minValue = 0
                numberPicker.displayedValues = run {
                  val res = Array(60) {""}
                  for (i in 0..59) {
                    if(i < 10) res[i] = "0$i"
                    else res[i] = i.toString()
                  }
                  res
                }
                numberPicker.setOnValueChangedListener(endMinuteChangeListener)
                numberPicker
              },
              update = { view ->
                view.value = selectedReminderTimingEnd.split(":")[1].toInt() //To attach state variable
              }
            )
          }
        }
      }
    },

    setShowDialog = setShowDialog,

    onConfirmButtonClick = {
      preferenceDataStoreViewModel.setReminderPeriodStart(selectedReminderTimingStart)
      preferenceDataStoreViewModel.setReminderPeriodEnd(selectedReminderTimingEnd)
    }
  )

}