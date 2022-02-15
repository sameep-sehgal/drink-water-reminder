package com.sameep.watertracker.ui.screens.settingstab.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.sameep.watertracker.ui.components.IconText
import com.sameep.watertracker.ui.screens.settingstab.horizontalPaddingSettings
import com.sameep.watertracker.ui.screens.settingstab.verticalPaddingSettings

@Composable
fun SettingsRowSelectValue(
  text: String,
  value:String,
  onSettingsRowClick: () -> Unit,
  enabled:Boolean = true,
  icon:Int? = null,
  valueFontSize:TextUnit = 13.sp,
  textFontSize:TextUnit = 15.sp
) {
  val modifier:Modifier =
    if(enabled)
      Modifier.clickable { onSettingsRowClick() }
    else Modifier.alpha(0.3f)

  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    if(icon == null) {
      Text(
        modifier = Modifier
          .weight(1f)
          .padding(
            horizontal = horizontalPaddingSettings,
            vertical = verticalPaddingSettings
          ),
        text = text,
        fontSize = textFontSize
      )
    } else {
      IconText(
        text = text,
        fontSize = textFontSize,
        icon = icon,
        modifier = Modifier
          .weight(1f)
          .padding(
            horizontal = horizontalPaddingSettings,
            vertical = verticalPaddingSettings
          )
      )
    }
    Text(
      modifier = Modifier
        .padding(
          horizontal = horizontalPaddingSettings,
          vertical = verticalPaddingSettings
        ),
      text = value,
      color = MaterialTheme.colors.primary,
      fontSize = valueFontSize
    )
  }
}