package com.sameep.watertracker.ui.screens.hometab.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sameep.watertracker.R
import com.sameep.watertracker.RoomDatabaseViewModel
import com.sameep.watertracker.data.models.DailyWaterRecord
import com.sameep.watertracker.data.models.DrinkLogs
import com.sameep.watertracker.ui.theme.Typography
import com.sameep.watertracker.utils.Beverages
import com.sameep.watertracker.utils.TimeString

@Composable
fun DrinkLog(
  drinkLog: DrinkLogs,
  waterUnit: String,
  setShowEditDrinkLogDialog:(Boolean) -> Unit,
  roomDatabaseViewModel: RoomDatabaseViewModel,
  dailyWaterRecord: DailyWaterRecord,
  setSelectedDrinkLog:(drinkLog: DrinkLogs) -> Unit
) {
  Box(
    modifier = Modifier
      .padding(vertical = 3.dp, horizontal = 16.dp)
      .fillMaxWidth()
  ) {
    Card(
      shape = RoundedCornerShape(15.dp),
      elevation = 6.dp
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically
        ){
          Text(
            text = TimeString.longToString(drinkLog.time),
            style = Typography.subtitle1,
            modifier = Modifier.padding(8.dp,0.dp)
          )
          Text(
            text = drinkLog.beverage,
            style = Typography.subtitle1,
            modifier = Modifier.widthIn(0.dp,80.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
        }
        Row(
          modifier = Modifier.weight(1f),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ){
          Image(
            painter = painterResource(id = Beverages.getBeverageImage(drinkLog.icon)),
            contentDescription = "Selected glass",
            modifier = Modifier
              .size(20.dp)
              .padding(2.dp, 0.dp)
          )
          Text(
            text = "${drinkLog.amount}$waterUnit",
            style = Typography.subtitle1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
        }
        Row(
          verticalAlignment = Alignment.CenterVertically
        ){
          IconButton(
            onClick = {
              setSelectedDrinkLog(drinkLog)
              setShowEditDrinkLogDialog(true)
            }
          ) {
            Icon(
              painter = painterResource(R.drawable.edit_icon),
              contentDescription = "Edit Button"
            )
          }
          IconButton(
            onClick = {
              roomDatabaseViewModel.deleteDrinkLog(drinkLog)
              roomDatabaseViewModel.updateDailyWaterRecord(
                DailyWaterRecord(
                  date = dailyWaterRecord.date,
                  goal = dailyWaterRecord.goal,
                  currWaterAmount = dailyWaterRecord.currWaterAmount - drinkLog.amount
                )
              )
            }
          ) {
            Icon(
              painter = painterResource(R.drawable.delete_icon),
              contentDescription = "Delete Button"
            )
          }
        }
      }
    }
  }
}