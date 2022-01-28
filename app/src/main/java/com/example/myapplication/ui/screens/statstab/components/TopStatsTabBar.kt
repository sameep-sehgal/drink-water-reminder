package com.example.myapplication.ui.screens.statstab.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.components.DropdownSelect
import com.example.myapplication.utils.Statistics.createDropDownOptions

@Composable
fun TopStatsTabBar(
  setSelectedStatsTimeLine: (String) -> Unit
) {
  TopAppBar(
    elevation = 6.dp,
    backgroundColor = MaterialTheme.colors.primary,
    contentColor = MaterialTheme.colors.onSurface
  ) {
    val dropDownOptions = createDropDownOptions()
    var selectedOption by remember { mutableStateOf(dropDownOptions[0]) }
    val setSelectedOption = {
      option:HashMap<String, Any?> ->
        selectedOption = option
        setSelectedStatsTimeLine(option["value"].toString())
    }

    Row {
      Spacer(modifier = Modifier.size(8.dp))
      Text(
        text = "Statistics",
        fontSize = 18.sp,
        color = MaterialTheme.colors.onSurface,
        modifier = Modifier.weight(1f)
      )
      DropdownSelect(
        options = dropDownOptions,
        selectedOption = selectedOption,
        setSelectedOption = setSelectedOption,
        onSelectedColor = MaterialTheme.colors.onPrimary,
        showBorder = false
      )
      Spacer(modifier = Modifier.size(8.dp))
    }
  }
}
