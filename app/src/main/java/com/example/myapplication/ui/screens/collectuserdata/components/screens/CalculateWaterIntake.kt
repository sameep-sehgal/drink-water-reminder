package com.example.myapplication.ui.screens.collectuserdata.components.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalculateWaterIntake(
  recommendedWaterIntake: Int,
  waterUnit: String
) {
  Text(
    modifier = Modifier.padding(0.dp, 16.dp),
    text = "Your Hydration Plan",
    fontWeight = FontWeight.Bold,
    textAlign = TextAlign.Center
  )

  Text(
    modifier = Modifier.padding(0.dp, 16.dp),
    text = "Your Recommended Water Intake is",
    textAlign = TextAlign.Center,
    fontSize = 18.sp
  )

  Text(
    modifier = Modifier.padding(0.dp, 16.dp),
    text = "$recommendedWaterIntake${waterUnit}",
    fontWeight = FontWeight.Bold,
    color = MaterialTheme.colors.primary,
    textAlign = TextAlign.Center
  )

  Text(
    modifier = Modifier.padding(0.dp, 16.dp),
    text = "Try to divide your water intake in 8 - 12 servings throughout the day",
    textAlign = TextAlign.Center,
    fontSize = 16.sp
  )
}