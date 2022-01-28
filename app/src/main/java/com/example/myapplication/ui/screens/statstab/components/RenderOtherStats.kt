package com.example.myapplication.ui.screens.statstab.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.screens.settingstab.components.SettingsRowSelectValue

@Composable
fun RenderOtherStats() {
  Card(
    elevation = 6.dp,
    modifier = Modifier.padding(6.dp)
  ) {
    Column(
      modifier = Modifier.padding(8.dp)
    ) {
      SettingsRowSelectValue(
        text = "Average Consumption",
        value = "2500 ml/day",
        onSettingsRowClick = {}
      )
      SettingsRowSelectValue(
        text = "Average Completion",
        value = "87%",
        onSettingsRowClick = {}
      )
      SettingsRowSelectValue(
        text = "Drink Frequency",
        value = "5 times/day",
        onSettingsRowClick = {}
      )
      SettingsRowSelectValue(
        text = "Goal Completed",
        value = "5/7 days",
        onSettingsRowClick = {}
      )
    }
  }
}