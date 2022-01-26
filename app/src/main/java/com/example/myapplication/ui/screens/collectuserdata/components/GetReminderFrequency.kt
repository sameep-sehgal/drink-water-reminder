package com.example.myapplication.ui.screens.collectuserdata.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.ui.theme.Typography
import com.example.myapplication.utils.ReminderFrequencyOptions
import com.example.myapplication.utils.ReminderGap

@Composable
fun GetReminderFrequency(
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  currentScreen:Int,
  setCurrentScreen:(Int) -> Unit
) {
  var selectedReminderGap by  remember { mutableStateOf(ReminderGap.ONE_HOUR) }
  val setSelectedReminderGap = { timeGap:Int ->
    selectedReminderGap = timeGap
  }
  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .weight(1f)
        .verticalScroll(scrollState),
      verticalArrangement = Arrangement.Center
    ) {
      Text(
        modifier = Modifier.padding(0.dp,8.dp),
        text = "Select Reminder Frequency",
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
      )
      Column {
        ReminderFrequencyOptions.OPTIONS.forEach {
          Row(
            modifier = Modifier.clickable {
              setSelectedReminderGap(it["gap"].toString().toInt())
              Log.d("TAG", "GetReminderFrequency: $selectedReminderGap")
            },
            verticalAlignment = Alignment.CenterVertically
          ) {
            Checkbox(
              checked = selectedReminderGap == it["gap"].toString().toInt(),
              onCheckedChange = { _ ->
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
    }

    LinearProgressIndicator(currentScreen/6f)
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp, 16.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceAround
    ) {
      Button(
        onClick = {
          setCurrentScreen(currentScreen - 1)
        },
        shape = CircleShape
      ) {
        Text(
          text = "Back",
          fontWeight = FontWeight.ExtraBold,
          fontSize = 21.sp
        )
      }
      Button(
        onClick = {
          preferenceDataStoreViewModel.setReminderGap(selectedReminderGap)
          setCurrentScreen(currentScreen + 1)
        },
        shape = CircleShape
      ) {
        Text(
          fontSize = 21.sp,
          text = "Next",
          fontWeight = FontWeight.ExtraBold
        )
      }
    }
  }
}

