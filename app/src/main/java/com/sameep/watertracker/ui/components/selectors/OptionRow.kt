package com.sameep.watertracker.ui.components.selectors

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sameep.watertracker.ui.theme.Typography

@Composable
fun OptionRow(
  selected: Boolean,
  onClick: () -> Unit,
  text: String,
  center:Boolean = false
) {
  Row(
    modifier = Modifier.clickable {
      onClick()
    }.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = if(center) Arrangement.Center else Arrangement.Start
  ) {
    Checkbox(
      checked = selected,
      onCheckedChange = { onClick() },
      colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colors.primary)
    )
    Text(
      text = text,
      style = Typography.subtitle1
    )
  }
}