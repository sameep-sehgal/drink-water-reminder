package com.sameep.watertracker.ui.screens.settingstab.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.sameep.watertracker.ui.screens.settingstab.horizontalPaddingSettings
import com.sameep.watertracker.ui.theme.Typography
import com.sameep.watertracker.ui.screens.settingstab.verticalPaddingSettings

@Composable
fun SettingsRowNoValue(
  text:String,
  onSettingsRowClick:() -> Unit,
  enabled: Boolean = true
) {
  val modifier:Modifier =
    if(enabled)
      Modifier.clickable { onSettingsRowClick() }
    else Modifier.alpha(0.3f)
  Row(
    modifier = modifier.fillMaxWidth(),
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