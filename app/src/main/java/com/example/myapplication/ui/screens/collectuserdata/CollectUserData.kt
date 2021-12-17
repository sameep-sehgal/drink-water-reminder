package com.example.myapplication.ui.screens.collectuserdata

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.*
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.ui.screens.collectuserdata.components.GetWeight
import com.example.myapplication.utils.Gender
import com.example.myapplication.utils.Units

@Composable
fun CollectUserData(
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel
) {
  var selectedGender by  remember { mutableStateOf(Gender.NOT_SET) }
  fun setSelectedGender(gender:String) {
    selectedGender = gender
  }

  val (selectedReminderTimingStart, setSelectedReminderTimingStart) =  remember { mutableStateOf(Gender.NOT_SET) }
  val (selectedReminderTimingEnd, setSelectedReminderTimingEnd) =  remember { mutableStateOf(Gender.NOT_SET) }
  val (selectedReminderGap, setSelectedReminderGap) =  remember { mutableStateOf(Gender.NOT_SET) }

  Column() {
    Text(text = "Collect User Data")
    IntroScreen()
//    GetGender(
//      selectedGender,
//      setSelectedGender,
//      preferenceDataStoreViewModel
//    )
    GetWeight(preferenceDataStoreViewModel)
//    GetReminderPeriod(preferenceDataStoreViewModel)
//    GetReminderFrequency(preferenceDataStoreViewModel)
  }
}