package com.example.myapplication.ui.screens.settingstab.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.myapplication.horizontalPaddingSettings
import com.example.myapplication.ui.theme.Typography
import com.example.myapplication.verticalPaddingSettings

@Composable
fun SettingsRowNoValue(text:String, onSettingsRowClick:() -> Unit) {
  Row(
    modifier = Modifier.fillMaxWidth()
      .clickable {
        onSettingsRowClick()
      },
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      modifier = Modifier
        .padding(
          horizontal = horizontalPaddingSettings,
          vertical = verticalPaddingSettings
        ),
      text = text,
      style = Typography.subtitle1
    )
  }
}