package com.example.myapplication.ui.screens.settingstab.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.screens.settingstab.horizontalPaddingSettings
import com.example.myapplication.ui.theme.Typography
import com.example.myapplication.ui.screens.settingstab.verticalPaddingSettings

@Composable
fun SettingsRowSelectValue(
  text: String,
  value:String,
  onSettingsRowClick: () -> Unit
) {
  Row(
    modifier = Modifier
      .clickable {
        onSettingsRowClick()
      },
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      modifier = Modifier
        .weight(1f)
        .padding(
        horizontal = horizontalPaddingSettings,
        vertical = verticalPaddingSettings
      ),
      text = text,
      style = Typography.subtitle1
    )
    Text(
      modifier = Modifier
        .padding(
          horizontal = horizontalPaddingSettings,
          vertical = verticalPaddingSettings
        ),
      text = value,
      style = Typography.subtitle1,
      color = MaterialTheme.colors.primary
    )
  }
}