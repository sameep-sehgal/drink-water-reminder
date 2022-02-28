package com.sameep.watertracker.ui.components.selectors

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sameep.watertracker.ui.theme.Typography

@Composable
fun OptionRow(
  selected: Boolean,
  onClick: () -> Unit,
  text: String,
  center:Boolean = false
) {
  Row(
    modifier = Modifier
      .clickable {
        onClick()
      }
      .fillMaxWidth()
      .padding(4.dp, 8.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = if(center) Arrangement.Center else Arrangement.Start
  ) {
    Check(selected = selected)
    Text(
      text = text,
      style = Typography.subtitle1,
      modifier = Modifier.padding(4.dp,0.dp,0.dp,0.dp)
    )
  }
}

@Composable
fun Check(
  selected: Boolean,
) {
  val modifier = Modifier
    .clip(CircleShape)
    .size(22.dp)
    .background(MaterialTheme.colors.onSurface)
    .padding(1.5.dp)
  Box(
    modifier =
    if(selected)
      modifier
        .clip(CircleShape)
        .background(MaterialTheme.colors.primary)
    else modifier
      .clip(CircleShape)
      .background(MaterialTheme.colors.surface),
    contentAlignment = Alignment.Center
  ) {
    if(selected)
      Icon(
        imageVector = Icons.Default.Check,
        contentDescription = "",
        tint = Color.White
      )
  }
}