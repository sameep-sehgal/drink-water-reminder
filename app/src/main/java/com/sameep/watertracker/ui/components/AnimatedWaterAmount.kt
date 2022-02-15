package com.sameep.watertracker.ui.components

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun AnimatedWaterAmount(
  currWaterAmount:Int,
  waterUnit: String,
  waterAmountTextColor: Color = MaterialTheme.colors.onBackground,
  fontSize:TextUnit,
  fontWeight: FontWeight = FontWeight.Normal
) {
  val animatedWaterAmount = animateIntAsState(
    targetValue = currWaterAmount,
    animationSpec = tween(durationMillis = 600)
  )
  Text(
    text = "${animatedWaterAmount.value}${waterUnit}",
    color = waterAmountTextColor,
    fontSize = fontSize,
    fontWeight = fontWeight,
    overflow = TextOverflow.Clip,
    maxLines = 1
  )
}