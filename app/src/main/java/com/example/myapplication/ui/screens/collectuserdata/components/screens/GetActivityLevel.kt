package com.example.myapplication.ui.screens.collectuserdata.components.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.ui.components.OptionRow
import com.example.myapplication.utils.ActivityLevel

@Composable
fun GetActivityLevel(
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  activityLevel: String
) {
  val setSelectedActivityLevel = { value:String ->
    if(value != activityLevel) preferenceDataStoreViewModel.setActivityLevel(value)
  }

  Text(
    modifier = Modifier.padding(0.dp,8.dp),
    text = "Select Your Activity Level",
    fontWeight = FontWeight.Bold,
    textAlign = TextAlign.Center
  )
  Column {
    ActivityLevel.OPTIONS.forEach {
      OptionRow(
        selected = activityLevel == it,
        onClick = { setSelectedActivityLevel(it) },
        text = it
      )
    }
  }
}