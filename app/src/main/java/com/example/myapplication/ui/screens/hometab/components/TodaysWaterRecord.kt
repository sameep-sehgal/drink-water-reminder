package com.example.myapplication.ui.screens.hometab.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.models.DailyWaterRecord

@Composable
fun TodaysWaterRecord(
  currWaterAmount:Int,
  goal:Int,
  waterUnit:String
) {
  val animatedWaterAmount = animateIntAsState(
    targetValue = currWaterAmount,
    animationSpec = tween(durationMillis = 1000)
  )

  Row(
    modifier = Modifier.padding(12.dp)
  ) {
    Text(
      text = "${animatedWaterAmount.value}",
      color = MaterialTheme.colors.primary
    )
    Text(text = "/${goal}${waterUnit}")
  }
}