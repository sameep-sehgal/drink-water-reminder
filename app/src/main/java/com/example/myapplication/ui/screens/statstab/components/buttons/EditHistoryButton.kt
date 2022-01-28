package com.example.myapplication.ui.screens.statstab.components.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EditHistoryButton(
  setShowEditHistoryDialog:(Boolean) -> Unit
) {
  Button(
    onClick = { setShowEditHistoryDialog(true) },
    shape = CircleShape,
    contentPadding = PaddingValues(16.dp,2.dp)
  ) {
    Text(
      text = "Edit History",
      fontSize = 16.sp
    )
  }
}