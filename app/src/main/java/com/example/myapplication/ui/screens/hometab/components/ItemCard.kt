package com.example.myapplication.ui.screens.hometab.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ItemCard(
  name:String,
  waterContent:Int,
  icon:Int
) {
  Card(
    elevation = 6.dp,
    modifier = Modifier.clickable {  }
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Image(
        painter = painterResource(id = icon),
        contentDescription = "$name icon"
      )
      Text(
        text = "$name($waterContent)"
      )
    }
  }
}