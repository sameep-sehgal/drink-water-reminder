package com.sameep.watertracker.ui.screens.edithistory.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sameep.watertracker.R
import com.sameep.watertracker.ui.theme.SettingsSubheadingBg
import com.sameep.watertracker.utils.DateString

@Composable
fun RenderSelectedDate(
  selectedDate:String
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .background(color = SettingsSubheadingBg()),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
      painter = painterResource(R.drawable.history_icon_white),
      contentDescription = "History Icon",
      modifier = Modifier.padding(8.dp, 12.dp)
    )
    Text(
      text = "Record (${DateString.convertToReadableString(selectedDate)})",
      fontSize = 21.sp
    )
  }
}