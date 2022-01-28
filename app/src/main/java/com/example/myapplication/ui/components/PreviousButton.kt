package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun PreviousButton(
  onClick: () -> Unit,
  fontSize:TextUnit = 15.sp
) {
  IconButton(onClick = onClick) {
    Text(
      text = "<",
      fontSize = fontSize,
      color = Color.Gray
    )
  }
}