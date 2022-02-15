package com.sameep.watertracker.ui.screens.collectuserdata.components.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sameep.watertracker.PreferenceDataStoreViewModel
import com.sameep.watertracker.ui.components.OptionRow
import com.sameep.watertracker.utils.ActivityLevel

@Composable
fun GetActivityLevel(
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  activityLevel: String
) {
  val setSelectedActivityLevel = { value:String ->
    if(value != activityLevel) preferenceDataStoreViewModel.setActivityLevel(value)
  }

  LaunchedEffect(key1 = true) {
    //Save default value in DataStore
    if(activityLevel == ActivityLevel.LIGHTLY_ACTIVE)
      setSelectedActivityLevel(ActivityLevel.LIGHTLY_ACTIVE)
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