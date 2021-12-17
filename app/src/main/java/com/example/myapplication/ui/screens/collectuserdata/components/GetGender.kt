package com.example.myapplication.ui.screens.collectuserdata

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.R
import com.example.myapplication.ui.theme.LightGray
import com.example.myapplication.ui.theme.Typography
import com.example.myapplication.ui.theme.VeryLightGray
import com.example.myapplication.utils.Gender

@Composable
fun GetGender(
  selectedGender: String,
  setSelectedGender:(String)->Unit,
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel
) {

  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = "Select Gender",
      fontWeight = FontWeight.Bold
    )
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.weight(1f)
    ) {
      FemaleButton(selectedGender = selectedGender, setSelectedGender = setSelectedGender)
      MaleButton(selectedGender = selectedGender, setSelectedGender = setSelectedGender)
    }
    Row(
      verticalAlignment = Alignment.CenterVertically,
    ) {
      IconButton(onClick = { /*TODO*/ }) {
       Text(
         text = "<",
         fontWeight = FontWeight.ExtraBold
       )
      }
      IconButton(onClick = { /*TODO*/ }) {
        Text(
          text = ">",
          fontWeight = FontWeight.ExtraBold
        )
      }
    }
  }
}

@Composable
fun FemaleButton(
  selectedGender: String,
  setSelectedGender:(String)->Unit
) {
  Column(
    modifier = Modifier.padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Image(
      modifier = Modifier
        .selectable(
          selected = selectedGender == Gender.MALE,
          enabled = true,
          onClick = {
            if (selectedGender != Gender.FEMALE)
              setSelectedGender(Gender.FEMALE)
            else setSelectedGender(Gender.NOT_SET)
          })
        .padding(4.dp)
        .shadow(elevation = 0.dp, CircleShape, clip = true)
        .background(
          if (selectedGender == Gender.FEMALE) LightGray else VeryLightGray,
          CircleShape
        ),
      painter = painterResource(id = R.drawable.female_icon),
      contentDescription = "Female",
      alpha = if(selectedGender == Gender.FEMALE) 1f else 0.2f
    )
    Text(
      text = "Female",
      color = if (selectedGender == Gender.FEMALE)
        MaterialTheme.colors.primary
      else MaterialTheme.colors.onBackground,
      fontSize = 20.sp
    )
  }
}

@Composable
fun MaleButton(selectedGender: String, setSelectedGender:(String)->Unit) {
  Column(
    modifier = Modifier.padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Image(
      modifier = Modifier
        .selectable(
          selected = selectedGender == Gender.MALE,
          enabled = true,
          onClick = {
            if (selectedGender != Gender.MALE)
              setSelectedGender(Gender.MALE)
            else setSelectedGender(Gender.NOT_SET)
          })
        .padding(4.dp)
        .shadow(elevation = 0.dp, CircleShape, clip = true)
        .background(
          if (selectedGender == Gender.MALE) LightGray else VeryLightGray,
          CircleShape
        ),
      painter = painterResource(id = R.drawable.male_icon),
      contentDescription = "Male",
      alpha = if(selectedGender == Gender.MALE) 1f else 0.2f
    )
    Text(
      text = "Male",
      color = if (selectedGender == Gender.MALE)
        MaterialTheme.colors.primary
      else MaterialTheme.colors.onBackground,
      fontSize = 20.sp
    )
  }
}