package com.example.myapplication.ui.screens.edithistory.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.components.AnimatedWaterAmount

@Composable
fun SelectedHistoryWaterAmount(
  currWaterAmount:Int,
  goal:Int,
  waterUnit:String
) {
  val primaryColor = MaterialTheme.colors.primary
  val fontSize = 20.sp

  Row (
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically
  ){
    AnimatedWaterAmount(
      currWaterAmount = currWaterAmount,
      waterUnit = waterUnit,
      waterAmountTextColor = primaryColor,
      fontSize = fontSize
    )
    Text(
      text = "/",
      fontSize = fontSize,
      color = primaryColor
    )
    AnimatedWaterAmount(
      currWaterAmount = goal,
      waterUnit = waterUnit,
      fontSize = fontSize
    )
  }
}