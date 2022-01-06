package com.example.myapplication.ui.screens.settingstab.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.SettingsSubheadingBg
import com.example.myapplication.ui.theme.Typography

@Composable
fun SettingsSubheading(text:String){
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .background(SettingsSubheadingBg())
  ) {
    Text(
      modifier = Modifier
        .padding(6.dp),
      text = text,
      fontSize = 19.sp
    )
  }
}