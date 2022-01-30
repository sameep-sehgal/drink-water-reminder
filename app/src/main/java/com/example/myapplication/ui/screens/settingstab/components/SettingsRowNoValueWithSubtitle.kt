package com.example.myapplication.ui.screens.settingstab.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.screens.settingstab.horizontalPaddingSettings
import com.example.myapplication.ui.screens.settingstab.verticalPaddingSettings
import com.example.myapplication.ui.theme.Typography

@Composable
fun SettingsRowNoValueWithSubtitle(
  text:String,
  subtitle:String,
  onSettingsRowClick:() -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .clickable {
        onSettingsRowClick()
      }.padding(
        horizontal = horizontalPaddingSettings,
        vertical = verticalPaddingSettings
      )
  ) {
    Text(
      modifier = Modifier,
      text = text,
      style = Typography.subtitle1
    )
    Text(
      text = subtitle,
      color = Color.Gray,
      fontSize = 11.sp
    )
  }
}