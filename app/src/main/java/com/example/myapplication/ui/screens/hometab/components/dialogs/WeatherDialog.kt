package com.example.myapplication.ui.screens.hometab.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.components.ShowDialog

@Composable
fun WeatherDialog(setShowWeatherDialog:(Boolean)->Unit):Unit{
  val title = "How's the weather Today?"
  ShowDialog(
    title = title,
    content = { WeatherDialogContent() },
    setShowDialog = setShowWeatherDialog,
    onConfirmButtonClick = { /*Todo*/ }
  )
}

@Composable
fun WeatherDialogContent():Unit{
  Column(modifier = Modifier.background(MaterialTheme.colors.background)) {
    Text(text = "Dialog working")
    Text(text = "This is Weather Dialog Content")
  }
}