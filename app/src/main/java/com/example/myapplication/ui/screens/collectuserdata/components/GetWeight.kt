package com.example.myapplication.ui.screens.collectuserdata.components

import android.util.Log
import android.widget.NumberPicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.R
import com.example.myapplication.utils.Gender
import com.example.myapplication.utils.Units
import com.example.myapplication.utils.Weight

@Composable
fun GetWeight(
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  currentScreen:Int,
  setCurrentScreen:(Int) -> Unit
) {
  var selectedWeight by  remember { mutableStateOf(65) }
  fun setSelectedWeight(weight:Int) {
    selectedWeight = weight
  }
  var selectedWeightUnit by  remember { mutableStateOf(Units.KG) }
  fun setSelectedWeightUnit(unit:String) {
    selectedWeightUnit = unit
  }

  val unitPickerChangeListener = NumberPicker.OnValueChangeListener { picker, oldVal, newVal ->
    if (newVal == 0) {
      setSelectedWeightUnit(Units.KG)
      setSelectedWeight(Weight.lbToKg(selectedWeight))
    }
    else {
      setSelectedWeightUnit(Units.LB)
      setSelectedWeight(Weight.kgToLb(selectedWeight))
    }
    Log.d("TAG", "GetWeight: Before -> $selectedWeight $selectedWeightUnit")
  }
  val weightPickerChangeListener = NumberPicker.OnValueChangeListener { picker, oldVal, newVal ->
    setSelectedWeight(newVal)
  }

  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally
  ){
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.weight(1f),
      verticalArrangement = Arrangement.Center
    ) {
      Text(
        modifier = Modifier.padding(0.dp,16.dp),
        text = "Select Weight",
        fontWeight = FontWeight.Bold
      )
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(4.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Image(
          modifier = Modifier.weight(1f),
          painter = painterResource(id = R.drawable.weight_measuring_machine),
          contentDescription = "Weight Measuring Machine"
        )
        Row(
          modifier = Modifier.weight(1f),
          horizontalArrangement = Arrangement.Center
        ) {
          AndroidView(
            factory = { context ->
              val numberPicker = NumberPicker(context)
              numberPicker.maxValue = Weight.MAX_WEIGHT
              numberPicker.minValue = 1
              numberPicker.setOnValueChangedListener(weightPickerChangeListener)
              numberPicker
            },
            update = { view ->
              view.value = selectedWeight //To attach state variable
            }
          )


          AndroidView(
            factory = { context ->
              val numberPicker = NumberPicker(context)
              numberPicker.maxValue = 1
              numberPicker.minValue = 0
              numberPicker.displayedValues = arrayOf(
                Units.KG,
                Units.LB
              )
              numberPicker.setOnValueChangedListener(unitPickerChangeListener)
              numberPicker
            },
            update = { view ->
              view.value = if(selectedWeightUnit == Units.KG) 0 else 1
            }
          )
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
          preferenceDataStoreViewModel.setWeight(selectedWeight)
          preferenceDataStoreViewModel.setWeightUnit(selectedWeightUnit)
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