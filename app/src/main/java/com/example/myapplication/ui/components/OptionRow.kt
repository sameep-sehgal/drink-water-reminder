package com.example.myapplication.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.theme.Typography

@Composable
fun OptionRow(
  selected: Boolean,
  onClick: () -> Unit,
  text: String
) {
  Row(
    modifier = Modifier.clickable {
      onClick()
    }.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
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