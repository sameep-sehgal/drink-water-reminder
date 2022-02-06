package com.example.myapplication.ui.screens.hometab.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.RoomDatabaseViewModel
import com.example.myapplication.data.models.Beverage
import com.example.myapplication.data.models.DailyWaterRecord
import com.example.myapplication.data.models.DrinkLogs
import com.example.myapplication.ui.components.IconText
import com.example.myapplication.ui.screens.hometab.components.buttons.CustomAddWaterButton
import com.example.myapplication.ui.screens.hometab.components.buttons.UndoButton
import com.example.myapplication.utils.Container

@Composable
fun AddWaterButtonsRow(
  waterUnit:String,
  roomDatabaseViewModel: RoomDatabaseViewModel,
  dailyWaterRecord: DailyWaterRecord,
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  setShowCustomAddWaterDialog:(Boolean) -> Unit,
  setShowFruitDialog:(Boolean) -> Unit,
  beverage: Beverage,
  mostRecentDrinkLog: DrinkLogs?,
) {
  val glassCapacity = preferenceDataStoreViewModel.glassCapacity.collectAsState(initial = Container.baseGlassCapacity(waterUnit))
  val mugCapacity = preferenceDataStoreViewModel.mugCapacity.collectAsState(initial = Container.baseMugCapacity(waterUnit))
  val bottleCapacity = preferenceDataStoreViewModel.bottleCapacity.collectAsState(initial = Container.baseBottleCapacity(waterUnit))
  var showAddWaterButtons by remember{ mutableStateOf(false) }
  val currentPercentage = remember { Animatable(0f) }
  val addIconSize = 48.dp
  LaunchedEffect(showAddWaterButtons) {
    currentPercentage.animateTo(
      if(showAddWaterButtons) 1f else 0f,
      animationSpec = tween(durationMillis = 500)
    )
  }

  val fontSize = 12.sp

  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceEvenly,
    verticalAlignment = Alignment.CenterVertically
  ) {
    if(currentPercentage.value < 0.6f){
      Row(
        modifier = Modifier.weight(1f),
        horizontalArrangement = Arrangement.Center
      ) {
        UndoButton(
          onClick = {
            if(mostRecentDrinkLog != null) {
              roomDatabaseViewModel.updateDailyWaterRecord(
                DailyWaterRecord(
                  date = dailyWaterRecord.date,
                  goal = dailyWaterRecord.goal,
                  currWaterAmount = dailyWaterRecord.currWaterAmount - mostRecentDrinkLog.amount
                )
              )
              roomDatabaseViewModel.deleteDrinkLog(mostRecentDrinkLog)
            }
          }
        )
      }
    }

    Box(
      modifier = Modifier,
      contentAlignment = Alignment.TopCenter
    ) {
      val rowModifier = Modifier
        .height(addIconSize)
        .fillMaxWidth(currentPercentage.value)

      Card(
        shape = CircleShape,
        elevation = 6.dp,
        modifier = rowModifier
      ){
        Row(
          horizontalArrangement = Arrangement.SpaceEvenly,
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.fillMaxSize()
        ) {
          if(currentPercentage.value>0.98f){
            AddWaterContainerButton(
              container = Container.GLASS,
              waterAmount = glassCapacity.value,
              waterUnit = waterUnit,
              fontSize = fontSize,
              dailyWaterRecord = dailyWaterRecord,
              roomDatabaseViewModel = roomDatabaseViewModel,
              beverage = beverage
            )

            AddWaterContainerButton(
              container = Container.MUG,
              waterAmount = mugCapacity.value,
              waterUnit = waterUnit,
              fontSize = fontSize,
              dailyWaterRecord = dailyWaterRecord,
              roomDatabaseViewModel = roomDatabaseViewModel,
              beverage = beverage
            )

            Spacer(modifier = Modifier.width(addIconSize))

            AddWaterContainerButton(
              container = Container.BOTTLE,
              waterAmount = bottleCapacity.value,
              waterUnit = waterUnit,
              fontSize = fontSize,
              dailyWaterRecord = dailyWaterRecord,
              roomDatabaseViewModel = roomDatabaseViewModel,
              beverage = beverage
            )

            Row(
              modifier = Modifier
                .clip(CircleShape)
                .clickable {
                  setShowCustomAddWaterDialog(true)
                }
                .fillMaxHeight(),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "+Custom",
                fontSize = fontSize
              )
            }
          }
        }
      }

      Card(
        shape = CircleShape,
        elevation = 6.dp,
        modifier = Modifier.width(addIconSize)
      ) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          CustomAddWaterButton(
            modifier = Modifier.rotate(45f * currentPercentage.value),
            setShowCustomAddWaterDialog = { showAddWaterButtons = !showAddWaterButtons }
          )
        }
      }
    }

    if(currentPercentage.value < 0.6f) {
      Row(
        modifier = Modifier.weight(1f),
        horizontalArrangement = Arrangement.Center
      ) {
        Spacer(modifier = Modifier.size(20.dp))
      }
    }
  }
}

@Composable
fun AddWaterContainerButton(
  container:Int,
  waterAmount:Int,
  waterUnit: String,
  fontSize:TextUnit = 12.sp,
  dailyWaterRecord: DailyWaterRecord,
  roomDatabaseViewModel: RoomDatabaseViewModel,
  beverage: Beverage
) {
  Row(
    modifier = Modifier
      .clip(RoundedCornerShape(15.dp))
      .clickable {
        roomDatabaseViewModel.insertDrinkLog(
          DrinkLogs(
            amount = waterAmount,
            icon = beverage.icon,
            beverage = beverage.name
          ),
        )
        roomDatabaseViewModel.updateDailyWaterRecord(
          DailyWaterRecord(
            goal = dailyWaterRecord.goal,
            currWaterAmount = dailyWaterRecord.currWaterAmount + waterAmount
          )
        )
      }
      .fillMaxHeight(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    IconText(
      text = "${waterAmount}${waterUnit}",
      fontSize = fontSize,
      icon = Container.IMAGE_MAPPER[container]
    )
  }
}

