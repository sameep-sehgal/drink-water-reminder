package com.sameep.watertracker.ui.screens.settingstab.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.sameep.watertracker.ui.screens.settingstab.horizontalPaddingSettings
import com.sameep.watertracker.ui.screens.settingstab.verticalPaddingSettings
import com.sameep.watertracker.ui.theme.Typography

@Composable
fun SettingsRowNoValueWithSubtitle(
  text:String,
  subtitle:String,
  onSettingsRowClick:() -> Unit,
  enabled: Boolean = true
) {
  val modifier:Modifier =
    if(enabled)
      Modifier.clickable { onSettingsRowClick() }
    else Modifier.alpha(0.3f)
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(
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