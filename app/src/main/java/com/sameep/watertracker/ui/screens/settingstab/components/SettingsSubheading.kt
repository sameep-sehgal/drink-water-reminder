package com.sameep.watertracker.ui.screens.settingstab.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sameep.watertracker.ui.theme.SettingsSubheadingBg

@Composable
fun SettingsSubheading(text:String){
  Row(
    modifier = Modifier
      .fillMaxWidth(),
    horizontalArrangement = Arrangement.Center
  ) {
    Text(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(15.dp))
        .background(SettingsSubheadingBg())
        .padding(6.dp)
      ,
      text = text,
      fontSize = 16.sp,
      textAlign = TextAlign.Center
    )

  }
}