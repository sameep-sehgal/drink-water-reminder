package com.sameep.watertracker.ui.screens.statstab.components.topstatstabbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sameep.watertracker.ui.components.DropdownSelect
import com.sameep.watertracker.utils.Statistics.createDropDownOptions

@Composable
fun TopStatsTabBar(
  setSelectedStatsTimeLine: (String) -> Unit
) {
  TopAppBar(
    elevation = 6.dp,
    backgroundColor = MaterialTheme.colors.primary
  ) {
    val dropDownOptions = createDropDownOptions()
    var selectedOption by remember { mutableStateOf(dropDownOptions[0]) }
    val setSelectedOption = {
      option:HashMap<String, Any?> ->
        selectedOption = option
        setSelectedStatsTimeLine(option["value"].toString())
    }

    Row (
      verticalAlignment = Alignment.CenterVertically
    ){
      Spacer(modifier = Modifier.size(8.dp))
      Text(
        text = "Statistics",
        fontSize = 18.sp,
        color = MaterialTheme.colors.onPrimary,
        modifier = Modifier.weight(1f)
      )
      DropdownSelect(
        options = dropDownOptions,
        selectedOption = selectedOption,
        setSelectedOption = setSelectedOption,
        onSelectedColor = MaterialTheme.colors.onPrimary,
        showBorder = false,
        textColor = MaterialTheme.colors.onPrimary
      )
      Spacer(modifier = Modifier.size(8.dp))
    }
  }
}
