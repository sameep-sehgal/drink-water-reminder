package com.example.myapplication.ui.screens.hometab.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.RoomDatabaseViewModel
import com.example.myapplication.data.models.DailyWaterRecord
import com.example.myapplication.data.models.DrinkLogs
import com.example.myapplication.utils.Container
import com.example.myapplication.utils.Units

@Composable
fun AddWaterButtonsRow(
  waterUnit:String,
  roomDatabaseViewModel: RoomDatabaseViewModel,
  dailyWaterRecord: DailyWaterRecord,
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel
) {
  val glassCapacity = preferenceDataStoreViewModel.glassCapacity.collectAsState(initial = Container.baseGlassCapacity(waterUnit))
  val mugCapacity = preferenceDataStoreViewModel.mugCapacity.collectAsState(initial = Container.baseMugCapacity(waterUnit))
  val bottleCapacity = preferenceDataStoreViewModel.bottleCapacity.collectAsState(initial = Container.baseBottleCapacity(waterUnit))

  val buttons = listOf(
    listOf(Container.GLASS, glassCapacity.value, "Water"),
    listOf(Container.MUG, mugCapacity.value, "Water"),
    listOf(Container.BOTTLE, bottleCapacity.value, "Water"),
  )

  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceEvenly
  ) {
    buttons.forEach {
      Button(
        onClick = {
          roomDatabaseViewModel.insertDrinkLog(
            DrinkLogs(amount = it[1] as Int, icon = it[0] as Int),
          )
          roomDatabaseViewModel.updateDailyWaterRecord(
            DailyWaterRecord(
              goal = dailyWaterRecord.goal,
              currWaterAmount = dailyWaterRecord.currWaterAmount + it[1] as Int
            )
          )
        },
        modifier = Modifier
          .padding(4.dp, 16.dp)
          .clip(CircleShape)
      ) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Container.IMAGE_MAPPER[it[0] as Int]?.let { it1 -> painterResource(id = it1) }?.let { it2 ->
            Icon(
              painter = it2,
              contentDescription = "container",
              modifier = Modifier.size(24.dp)
            )
          }
          Text(
            text = "${it[1] as Int}$waterUnit",
            fontSize = 11.sp
          )
        }
      }
    }
  }
}