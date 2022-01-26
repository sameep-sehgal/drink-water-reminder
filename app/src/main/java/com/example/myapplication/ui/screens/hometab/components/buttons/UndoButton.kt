package com.example.myapplication.ui.screens.hometab.components.buttons

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.myapplication.R

@Composable
fun UndoButton(onClick:()->Unit){
  IconButton(
    onClick = { onClick() }
  ) {
    Icon(
      painter = painterResource(R.drawable.undo_icon),
      contentDescription = "Reset Button"
    )
  }
}