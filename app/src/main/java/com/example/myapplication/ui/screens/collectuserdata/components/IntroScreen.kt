package com.example.myapplication.ui.screens.collectuserdata.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.Typography

@Composable
fun IntroScreen(
  currentScreen:Int,
  setCurrentScreen:(Int) -> Unit
) {
  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
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
        setCurrentScreen(currentScreen+1)
      }
    ) {
      Text(text = "Get Started")
    }
  }
}