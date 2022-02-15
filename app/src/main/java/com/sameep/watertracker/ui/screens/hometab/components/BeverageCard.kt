package com.sameep.watertracker.ui.screens.hometab.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sameep.watertracker.RoomDatabaseViewModel
import com.sameep.watertracker.data.models.Beverage
import com.sameep.watertracker.utils.Beverages

@Composable
fun BeverageCard(
  beverage: Beverage,
  selected:Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  roomDatabaseViewModel: RoomDatabaseViewModel,
  beverageList: List<Beverage>
) {
  val primaryColor = MaterialTheme.colors.primary
  val shape = RoundedCornerShape(15.dp)
  val (showDropDown, setShowDropDown) =  remember { mutableStateOf(false) }

  Card(
    elevation = if(!selected) 6.dp else 0.dp,
    modifier = modifier
      .fillMaxSize()
      .selectable(selected = selected, onClick = onClick)
      .border(
        width = if (selected) 4.dp else (-1).dp,
        color = primaryColor,
        shape = shape
      ),
    shape = shape
  ) {
    Box(modifier = Modifier.fillMaxSize()) {
      if(!selected) {
        BeverageCardSettingsIcon(
          setShowDropDown = setShowDropDown,
          showDropDown = showDropDown,
          modifier = Modifier
            .align(Alignment.TopEnd)
        )
      }

      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.align(Alignment.Center)
      ) {
        Image(
          painter = painterResource(id = Beverages.getBeverageImage(beverage.icon)),
          contentDescription = "${beverage.name} icon",
          modifier = Modifier.size(40.dp)
        )
        Text(
          text = beverage.name,
          fontSize = 11.sp,
          textAlign = TextAlign.Center,
          modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 6.dp, 0.dp, 0.dp),
          color = if(selected) primaryColor else MaterialTheme.colors.onSurface,
          maxLines = 2,
          overflow = TextOverflow.Ellipsis
        )
      }

      if(showDropDown) {
        BeverageCardDropDown(
          setShowDropDown = setShowDropDown,
          showDropDown = showDropDown,
          modifier = Modifier.align(Alignment.TopCenter),
          roomDatabaseViewModel = roomDatabaseViewModel,
          beverage = beverage,
          beverageList = beverageList
        )
      }
    }
  }
}

@Composable
fun BeverageCardDropDown(
  setShowDropDown: (Boolean) -> Unit,
  showDropDown:Boolean,
  modifier:Modifier = Modifier,
  roomDatabaseViewModel: RoomDatabaseViewModel,
  beverage: Beverage,
  beverageList: List<Beverage>
) {
  DropdownMenu(
    expanded = showDropDown,
    onDismissRequest = { setShowDropDown(false) },
    modifier = modifier
  ) {
    DropdownMenuItem(
      onClick = {
        roomDatabaseViewModel.updateBeverageImportanceOnDelete(beverageList, beverage.importance)
        roomDatabaseViewModel.deleteBeverage(beverage)
        setShowDropDown(false)
      }
    ) {
      Text(
        text = "Delete",
        fontSize = 12.sp
      )
    }
    DropdownMenuItem(
      onClick = {
        roomDatabaseViewModel.updateBeverageImportance(beverageList, beverage.importance)
        setShowDropDown(false)
      }
    ) {
      Text(
        text = "Move To Top",
        fontSize = 12.sp
      )
    }
  }
}

@Composable
fun BeverageCardSettingsIcon(
  setShowDropDown: (Boolean) -> Unit,
  showDropDown: Boolean,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .padding(3.dp)
      .size(18.dp)
      .clickable { setShowDropDown(!showDropDown) },
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center
  ) {
    Text(
      text = ":",
      fontWeight = FontWeight.ExtraBold,
      fontSize = 10.sp
    )
  }
}