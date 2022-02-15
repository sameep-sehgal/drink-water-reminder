package com.sameep.watertracker.ui.screens.remindertab.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReminderSettingsCard(
  text:String,
  onClick:() -> Unit,
  content: @Composable()()->Unit
) {
  Card(
    elevation = 6.dp,
    modifier = Modifier
      .padding(6.dp)
      .fillMaxWidth()
      .clickable { onClick() }
  ) {
    Column(
      modifier = Modifier.padding(8.dp)
    ) {
      Text(
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
      )
      Spacer(modifier = Modifier.size(16.dp))
      content()
    }
  }
}