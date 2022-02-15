package com.sameep.watertracker.ui.screens.collectuserdata.components.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sameep.watertracker.R
import com.sameep.watertracker.ui.theme.Typography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun IntroScreen(
  currentScreen:Int,
  setCurrentScreen:(Int) -> Unit,
  columnAlpha: Animatable<Float, AnimationVector1D>,
  scope: CoroutineScope
) {
  Text(
    modifier = Modifier.padding(16.dp),
    text = "Welcome to ${stringResource(R.string.app_name)}",
    textAlign = TextAlign.Center
  )
  Text(
    modifier = Modifier.padding(8.dp),
    text = "Get started by providing some basic information so that the app can be tailored specifically for you.",
    style = Typography.subtitle1
  )
  Button(
    modifier = Modifier.padding(16.dp),
    onClick = {
      scope.launch {
        columnAlpha.animateTo(0f)
        setCurrentScreen(currentScreen + 1)
      }
    }
  ) {
    Text(text = "Get Started")
  }
}