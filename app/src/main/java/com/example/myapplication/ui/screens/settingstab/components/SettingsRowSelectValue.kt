package com.example.myapplication.ui.screens.settingstab.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.components.IconText
import com.example.myapplication.ui.screens.settingstab.horizontalPaddingSettings
import com.example.myapplication.ui.theme.Typography
import com.example.myapplication.ui.screens.settingstab.verticalPaddingSettings

@Composable
fun SettingsRowSelectValue(
  text: String,
  value:String,
  onSettingsRowClick: () -> Unit,
  enabled:Boolean = true,
  icon:Int? = null
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
        style = Typography.subtitle1
      )
    } else {
      IconText(
        text = text,
        fontSize = 15.sp,
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
      style = Typography.subtitle1,
      color = MaterialTheme.colors.primary
    )
  }
}