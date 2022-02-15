package com.sameep.watertracker.ui.screens.collectuserdata.components.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sameep.watertracker.PreferenceDataStoreViewModel
import com.sameep.watertracker.R
import com.sameep.watertracker.ui.theme.LightGray
import com.sameep.watertracker.ui.theme.VeryLightGray
import com.sameep.watertracker.utils.Gender

@Composable
fun GetGender(
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  gender: String
) {
  val setSelectedGender = {value:String ->
    if(value != gender) preferenceDataStoreViewModel.setGender(value)
  }

  LaunchedEffect(key1 = true) {
    //Save default value in DataStore
    if(gender == Gender.MALE)
      setSelectedGender(Gender.MALE)
  }

  Text(
    modifier = Modifier.padding(0.dp,16.dp),
    text = "Select Gender",
    fontWeight = FontWeight.Bold
  )
  Row(
    verticalAlignment = Alignment.CenterVertically
  ) {
    GenderSelectButton(
      selectedGender = gender,
      setSelectedGender = setSelectedGender,
      gender = Gender.FEMALE,
      icon = R.drawable.female_icon
    )
    GenderSelectButton(
      selectedGender = gender,
      setSelectedGender = setSelectedGender,
      gender = Gender.MALE,
      icon = R.drawable.male_icon
    )
  }
}

@Composable
fun GenderSelectButton(
  selectedGender: String,
  setSelectedGender:(String)->Unit,
  gender: String,
  icon: Int
) {
  Column(
    modifier = Modifier.padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Image(
      modifier = Modifier
        .selectable(
          selected = selectedGender == gender,
          enabled = true,
          onClick = {
            setSelectedGender(gender)
          })
        .padding(4.dp)
        .shadow(elevation = 0.dp, CircleShape, clip = true)
        .background(
          if (selectedGender == gender) LightGray else VeryLightGray,
          CircleShape
        ),
      painter = painterResource(id = icon),
      contentDescription = gender,
      alpha = if(selectedGender == gender) 1f else 0.2f
    )
    Text(
      text = gender,
      color = if (selectedGender == gender)
        MaterialTheme.colors.primary
      else MaterialTheme.colors.onBackground,
      fontSize = 20.sp
    )
  }
}