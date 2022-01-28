package com.example.myapplication.ui.screens.statstab.components.selectors

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.components.NextButton
import com.example.myapplication.ui.components.PreviousButton

@Composable
fun WeekSelector() {
  Row(
    horizontalArrangement = Arrangement.Center,
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    PreviousButton(onClick = {})

    Spacer(modifier = Modifier.size(16.dp))

    Text(
      text = "Week",
      fontSize = 16.sp
    )

    Spacer(modifier = Modifier.size(16.dp))

    NextButton(onClick = {})
  }
}