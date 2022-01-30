package com.example.myapplication.ui.screens.hometab.components.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R


@Composable
fun FruitButton(
  setShowFruitDialog:(Boolean)->Unit
){
  Card(
    shape = RoundedCornerShape(15.dp),
    elevation = 6.dp
  ) {
    Row(
      modifier = Modifier
        .clip(RoundedCornerShape(15.dp))
        .clickable { setShowFruitDialog(true) },
    ) {
      Text(
        text = "+Veg/Fruit",
        fontSize = 14.sp,
        modifier = Modifier.padding(8.dp, 2.dp)
      )
    }
  }
}
