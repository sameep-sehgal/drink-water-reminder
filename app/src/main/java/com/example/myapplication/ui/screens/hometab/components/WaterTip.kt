package com.example.myapplication.ui.screens.hometab.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.static.WaterTips
import com.example.myapplication.ui.theme.SettingsSubheadingBg

@Composable
fun WaterTip() {
  Card (
    modifier = Modifier
      .padding(8.dp)
      .clip(RoundedCornerShape(15.dp)),
    elevation = 0.dp,
    backgroundColor = SettingsSubheadingBg()
  ){
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.padding(4.dp)
    ) {
      Text(
        text = WaterTips.getRandomTip().text,
        fontSize = 12.sp,
        modifier = Modifier.padding(4.dp, 2.dp)
      )
    }
  }
}