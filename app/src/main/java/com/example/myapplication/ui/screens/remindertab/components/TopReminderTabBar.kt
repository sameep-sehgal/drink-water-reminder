package com.example.myapplication.ui.screens.remindertab.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopReminderTabBar(
  onCheckedChange:(Boolean) -> Unit
) {
  val onPrimaryColor = MaterialTheme.colors.onPrimary

  TopAppBar (
    backgroundColor = MaterialTheme.colors.primary,
  ){
    Row (
      verticalAlignment = Alignment.CenterVertically
    ) {
      Spacer(modifier = Modifier.size(8.dp))
      Text(
        text = "Reminder",
        fontSize = 18.sp,
        color = onPrimaryColor,
        modifier = Modifier.weight(1f)
      )
      Switch(
        checked = false,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
          checkedThumbColor = onPrimaryColor
        )
      )
      Spacer(modifier = Modifier.size(8.dp))
    }
  }

}