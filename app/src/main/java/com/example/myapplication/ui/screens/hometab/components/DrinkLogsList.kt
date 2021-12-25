package com.example.myapplication.ui.screens.hometab.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.data.models.DrinkLogs
import com.example.myapplication.RoomDatabaseViewModel
import com.example.myapplication.data.models.DailyWaterRecord
import com.example.myapplication.ui.screens.hometab.components.dialogs.EditDrinkLogDialog
import com.example.myapplication.ui.theme.Typography
import com.example.myapplication.utils.Container
import com.example.myapplication.utils.TimeString

@ExperimentalFoundationApi
@Composable
fun DrinkLogsList(
  drinkLogsList: List<DrinkLogs>,
  roomDatabaseViewModel: RoomDatabaseViewModel,
  waterUnit:String,
  dailyWaterRecord: DailyWaterRecord
){
  val (showEditDrinkLogDialog, setShowEditDrinkLogDialog) =  remember { mutableStateOf(false) }
  val selectedDrinkLog =  remember { mutableStateOf(DrinkLogs(amount = 0, icon = 0)) }

  Column(
    modifier = Modifier.fillMaxWidth(),
  ){
    if(showEditDrinkLogDialog){
      EditDrinkLogDialog(
        drinkLog = selectedDrinkLog.value,
        setShowEditDrinkLogDialog = setShowEditDrinkLogDialog
      )
    }
    drinkLogsList.forEach {
      Box(
        modifier = Modifier
          .padding(vertical = 3.dp, horizontal = 16.dp)
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
              IconButton(
                onClick = { /*TODO*/ }
              ) {
                Icon(
                  painter = painterResource(R.drawable.clock_icon),
                  contentDescription = "Clock Icon"
                )
              }
              Text(
                text = TimeString.longToString(it.time),
                style = Typography.subtitle1
              )
            }
            Row(
              modifier = Modifier.weight(1f),
              horizontalArrangement = Arrangement.Center,
              verticalAlignment = Alignment.CenterVertically
            ){
              Container.IMAGE_MAPPER[it.icon]?.let { it1 -> painterResource(it1) }
                ?.let { it2 ->
                  Icon(
                    painter = it2,
                    contentDescription = "Selected glass",
                    modifier = Modifier
                      .size(20.dp)
                      .padding(2.dp, 0.dp)
                  ) }
              Text(text = "${it.amount}$waterUnit", style = Typography.subtitle1)
            }
            Row(
              verticalAlignment = Alignment.CenterVertically
            ){
              IconButton(
                onClick = {
                  selectedDrinkLog.value = it
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
                  roomDatabaseViewModel.deleteDrinkLog(it)
                  roomDatabaseViewModel.updateDailyWaterRecord(
                    DailyWaterRecord(
                      goal = dailyWaterRecord.goal,
                      currWaterAmount = dailyWaterRecord.currWaterAmount - it.amount
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
  }
}