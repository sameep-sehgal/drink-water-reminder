package com.example.myapplication.ui.screens.collectuserdata

import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.R
import com.example.myapplication.ui.theme.LightGray
import com.example.myapplication.ui.theme.VeryLightGray
import com.example.myapplication.utils.Gender

@Composable
fun GetGender(
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  currentScreen:Int,
  setCurrentScreen:(Int) -> Unit
) {
  var selectedGender by  remember { mutableStateOf(Gender.NOT_SET) }
  val setSelectedGender = {gender:String ->
    selectedGender = gender
  }
  val context = LocalContext.current

  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.weight(1f),
      verticalArrangement = Arrangement.Center
    ) {
      Text(
        modifier = Modifier.padding(0.dp,16.dp),
        text = "Select Gender",
        fontWeight = FontWeight.Bold
      )
      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        FemaleButton(selectedGender = selectedGender, setSelectedGender = setSelectedGender)
        MaleButton(selectedGender = selectedGender, setSelectedGender = setSelectedGender)
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
          if(selectedGender == Gender.NOT_SET){
            val toast = Toast(context)
            toast.setText("Please Select your gender")
            toast.duration = Toast.LENGTH_SHORT
            toast.show()
          }
          else {
            preferenceDataStoreViewModel.setGender(selectedGender)
            setCurrentScreen(currentScreen + 1)
          }
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
            Log.d("TAG", "FemaleButton: $selectedGender")
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