package com.example.myapplication.ui.screens.collectuserdata.components.screens

import android.widget.NumberPicker
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.ui.components.OptionRow
import com.example.myapplication.ui.theme.Typography
import com.example.myapplication.utils.*

@Composable
fun GetReminderPeriod(
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  reminderPeriodStart: String,
  reminderPeriodEnd: String,
  isReminderOn:Boolean
) {
  val setSelectedReminderTimingStart = { time:String ->
    preferenceDataStoreViewModel.setReminderPeriodStart(time)
  }
  val setSelectedReminderTimingEnd = { time:String->
    preferenceDataStoreViewModel.setReminderPeriodEnd(time)
  }

  val startHourChangeListener = NumberPicker.OnValueChangeListener { _, _, newVal ->
    val newTime:String =
      TimeString.HHMMIntToString(newVal,reminderPeriodStart.split(":")[1].toInt())
    setSelectedReminderTimingStart(newTime)
  }

  val startMinuteChangeListener = NumberPicker.OnValueChangeListener { _, _, newVal ->
    val newTime:String =
      TimeString.HHMMIntToString(reminderPeriodStart.split(":")[0].toInt(),newVal)
    setSelectedReminderTimingStart(newTime)
  }

  val endHourChangeListener = NumberPicker.OnValueChangeListener { _, _, newVal ->
    val newTime:String =
      TimeString.HHMMIntToString(newVal,reminderPeriodEnd.split(":")[1].toInt())
    setSelectedReminderTimingEnd(newTime)
  }

  val endMinuteChangeListener = NumberPicker.OnValueChangeListener { _, _, newVal ->
    val newTime:String =
      TimeString.HHMMIntToString(reminderPeriodEnd.split(":")[0].toInt(),newVal)
    setSelectedReminderTimingEnd(newTime)
  }

  Text(
    modifier = Modifier.padding(0.dp,16.dp),
    text = "Select Reminder Period",
    fontWeight = FontWeight.Bold,
    textAlign = TextAlign.Center
  )
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceEvenly,
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
            view.value = reminderPeriodStart.split(":")[0].toInt()
            view.isEnabled = isReminderOn
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
            view.value = reminderPeriodStart.split(":")[1].toInt()
            view.isEnabled = isReminderOn
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
            view.value = reminderPeriodEnd.split(":")[0].toInt()
            view.isEnabled = isReminderOn
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
            view.value = reminderPeriodEnd.split(":")[1].toInt()
            view.isEnabled = isReminderOn
          }
        )
      }
    }
  }

  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    if(isReminderOn) {
      Text(
        text = "You will receive reminders between ",
        style = Typography.subtitle1,
        textAlign = TextAlign.Center
      )
      Text(
        text = "$reminderPeriodStart and $reminderPeriodEnd",
        style = Typography.subtitle1,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
      )
    }else {
      Text(
        text = "You will NOT receive reminders",
        style = Typography.subtitle1,
        textAlign = TextAlign.Center
      )
      Text(
        text = "",
        style = Typography.subtitle1,
      )
    }
  }

  Spacer(modifier = Modifier.size(16.dp))

  OptionRow(
    selected = !isReminderOn,
    onClick = {
      preferenceDataStoreViewModel.setIsReminderOn(!isReminderOn)
    },
    text = "Don't Remind"
  )
}