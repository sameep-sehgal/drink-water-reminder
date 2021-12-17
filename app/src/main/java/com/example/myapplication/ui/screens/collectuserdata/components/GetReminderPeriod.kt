package com.example.myapplication.ui.screens.collectuserdata

import android.widget.NumberPicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.ui.theme.Typography
import com.example.myapplication.utils.*

@Composable
fun GetReminderPeriod(
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel
) {
  var selectedReminderTimingStart by  remember { mutableStateOf(ReminderPeriod.DEFAULT_REMINDER_PERIOD_START) }
  val setSelectedReminderTimingStart = { time:String ->
    selectedReminderTimingStart = time
  }
  var selectedReminderTimingEnd by  remember { mutableStateOf(ReminderPeriod.DEFAULT_REMINDER_PERIOD_END) }
  val setSelectedReminderTimingEnd = { time:String->
    selectedReminderTimingEnd = time
  }

  val startHourChangeListener = NumberPicker.OnValueChangeListener { picker, oldVal, newVal ->
    val newTime:String =
      TimeString.HHMMIntToString(newVal,selectedReminderTimingStart.split(":")[1].toInt())
    setSelectedReminderTimingStart(newTime)
  }

  val startMinuteChangeListener = NumberPicker.OnValueChangeListener { picker, oldVal, newVal ->
    val newTime:String =
      TimeString.HHMMIntToString(selectedReminderTimingStart.split(":")[0].toInt(),newVal)
    setSelectedReminderTimingStart(newTime)
  }

  val endHourChangeListener = NumberPicker.OnValueChangeListener { picker, oldVal, newVal ->
    val newTime:String =
      TimeString.HHMMIntToString(newVal,selectedReminderTimingEnd.split(":")[1].toInt())
    setSelectedReminderTimingEnd(newTime)
  }

  val endMinuteChangeListener = NumberPicker.OnValueChangeListener { picker, oldVal, newVal ->
    val newTime:String =
      TimeString.HHMMIntToString(selectedReminderTimingEnd.split(":")[0].toInt(),newVal)
    setSelectedReminderTimingEnd(newTime)
  }

  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {

    Text(
      text = "Select Reminder Period",
      fontWeight = FontWeight.Bold
    )
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.weight(1f),
      verticalArrangement = Arrangement.SpaceEvenly
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        Text(text = "Start")
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
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        Text(text = "End")
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

      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        Text(
          text = "You will receive reminders between ",
          style = Typography.subtitle1,
          textAlign = TextAlign.Center
        )
        Text(
          text = "$selectedReminderTimingStart and $selectedReminderTimingEnd",
          style = Typography.subtitle1,
          textAlign = TextAlign.Center,
          fontWeight = FontWeight.Bold
        )
      }
    }
  }
}