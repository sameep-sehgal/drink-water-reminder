package com.example.myapplication.ui.screens.hometab.components

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TodaysWaterRecord(
  currWaterAmount:Int,
  goal:Int,
  waterUnit:String,
  setShowSetTodaysGoalDialog:(Boolean) -> Unit
) {
  val animatedWaterAmount = animateIntAsState(
    targetValue = currWaterAmount,
    animationSpec = tween(durationMillis = 1000)
  )

  Row(
    modifier = Modifier.padding(12.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    WaterAmountCard(
      waterUnit = waterUnit,
      waterAmount = animatedWaterAmount.value,
      backgroundColor = MaterialTheme.colors.secondary,
      waterAmountTextColor = MaterialTheme.colors.primary,
      text = "You've had",
      modifier = Modifier.weight(1f)
    )
    Column(
      modifier = Modifier.padding(6.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = "out",
        fontSize = 14.sp,
        fontStyle = FontStyle.Italic
      )
      Text(
        text = "of",
        fontSize = 14.sp,
        fontStyle = FontStyle.Italic
      )
    }
    WaterAmountCard(
      waterUnit = waterUnit,
      waterAmount = goal,
      backgroundColor = MaterialTheme.colors.primary,
      waterAmountTextColor = MaterialTheme.colors.secondary,
      text = "Today's Goal",
      modifier = Modifier
        .weight(1f)
        .clickable { setShowSetTodaysGoalDialog(true) }
    )
  }
}

@Composable
fun WaterAmountCard(
  waterUnit: String,
  waterAmount:Int,
  backgroundColor:Color,
  waterAmountTextColor: Color,
  text:String,
  modifier:Modifier = Modifier
) {
  Card (
    shape = RoundedCornerShape(15.dp),
    backgroundColor = backgroundColor,
    modifier = modifier
  ){
    Column(
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.padding(4.dp)
    ) {
      Text(
        text = text,
        fontSize = 12.sp,
        color = Color.DarkGray
      )
      Text(
        text = "${waterAmount}${waterUnit}",
        color = waterAmountTextColor,
        fontSize = 21.sp,
        fontWeight = FontWeight.Bold,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
      )
    }
  }
}