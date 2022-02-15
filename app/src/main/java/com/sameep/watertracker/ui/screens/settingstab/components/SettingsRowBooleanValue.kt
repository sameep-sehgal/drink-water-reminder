package com.sameep.watertracker.ui.screens.settingstab.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.sameep.watertracker.ui.screens.settingstab.horizontalPaddingSettings
import com.sameep.watertracker.ui.theme.Typography
import com.sameep.watertracker.ui.screens.settingstab.verticalPaddingSettings

@Composable
fun SettingsRowBooleanValue(
  text: String,
  value:Boolean,
  onCheckedChange: (Boolean) -> Unit,
  enabled:Boolean = true
) {
  val modifier:Modifier =
    if(enabled)
      Modifier.clickable { onCheckedChange(!value) }
    else Modifier.alpha(0.3f)
  Row(
    modifier = modifier,
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
        uncheckedThumbColor = MaterialTheme.colors.onSurface,
        checkedThumbColor = MaterialTheme.colors.primary
      )
    )
  }
}