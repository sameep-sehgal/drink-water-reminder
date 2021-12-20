package com.example.myapplication.ui.screens.settingstab.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.screens.settingstab.horizontalPaddingSettings
import com.example.myapplication.ui.theme.Typography
import com.example.myapplication.ui.screens.settingstab.verticalPaddingSettings

@Composable
fun SettingsRowBooleanValue(text: String, value:Boolean, onCheckedChange: (Boolean) -> Unit) {
  Row(
    modifier = Modifier.clickable { onCheckedChange(!value) },
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
    Switch(
      checked = value,
      onCheckedChange = {
        onCheckedChange(it)
      },
      colors = SwitchDefaults.colors(
        uncheckedThumbColor = MaterialTheme.colors.onSurface
      )
    )
  }
}