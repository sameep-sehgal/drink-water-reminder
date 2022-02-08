package com.example.myapplication.ui.screens.collectuserdata.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CalculateWaterIntakeButtonsRow(
  scope: CoroutineScope,
  columnAlpha: Animatable<Float, AnimationVector1D>,
  currentScreen: Int,
  setCurrentScreen: (Int) -> Unit,
  onLetsGoButtonClick: () -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(0.dp, 16.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceAround
  ) {
    Button(
      onClick = {
        scope.launch {
          columnAlpha.animateTo(0f)
          setCurrentScreen(currentScreen - 1)
        }
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
        onLetsGoButtonClick()
      },
      shape = CircleShape
    ) {
      Text(
        fontSize = 21.sp,
        text = "Let's Go",
        fontWeight = FontWeight.ExtraBold
      )
    }
  }
}