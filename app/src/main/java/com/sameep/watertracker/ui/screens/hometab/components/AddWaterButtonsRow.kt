package com.sameep.watertracker.ui.screens.hometab.components

import android.content.Context
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
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.UpdateAvailability
import com.sameep.watertracker.PreferenceDataStoreViewModel
import com.sameep.watertracker.RoomDatabaseViewModel
import com.sameep.watertracker.data.models.Beverage
import com.sameep.watertracker.data.models.DailyWaterRecord
import com.sameep.watertracker.data.models.DrinkLogs
import com.sameep.watertracker.ui.components.IconText
import com.sameep.watertracker.ui.screens.hometab.components.buttons.CustomAddWaterButton
import com.sameep.watertracker.ui.screens.hometab.components.buttons.AppUpdateButton
import com.sameep.watertracker.ui.screens.hometab.components.buttons.UndoButton
import com.sameep.watertracker.utils.Container
import com.sameep.watertracker.utils.DateString

@Composable
fun AddWaterButtonsRow(
  waterUnit:String,
  roomDatabaseViewModel: RoomDatabaseViewModel,
  dailyWaterRecord: DailyWaterRecord,
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  setShowCustomAddWaterDialog:(Boolean) -> Unit,
  setShowAppUpdateDialog:(Boolean) -> Unit,
  beverage: Beverage,
  mostRecentDrinkLog: DrinkLogs?,
  context: Context
) {
  val glassCapacity = preferenceDataStoreViewModel.glassCapacity.collectAsState(initial = Container.baseGlassCapacity(waterUnit))
  val mugCapacity = preferenceDataStoreViewModel.mugCapacity.collectAsState(initial = Container.baseMugCapacity(waterUnit))
  val bottleCapacity = preferenceDataStoreViewModel.bottleCapacity.collectAsState(initial = Container.baseBottleCapacity(waterUnit))
  var showAddWaterButtons by remember{ mutableStateOf(false) }
  var showAppUpdateButton by remember{ mutableStateOf(false) }
  val currentPercentage = remember { Animatable(0f) }
  val addIconSize = 48.dp
  val todaysDate = DateString.getTodaysDate()

  LaunchedEffect(key1 = true) {
    val appUpdateManager = AppUpdateManagerFactory.create(context)

    val appUpdateInfoTask = appUpdateManager.appUpdateInfo

    appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
      if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
        showAppUpdateButton = true
      }
    }
  }

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
              if(todaysDate > dailyWaterRecord.date){
                roomDatabaseViewModel.refreshData()
              }else {
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
                  if (todaysDate > dailyWaterRecord.date) {
                    roomDatabaseViewModel.refreshData()
                  } else {
                    setShowCustomAddWaterDialog(true)
                  }
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
        if(showAppUpdateButton) {
          AppUpdateButton(
            setShowAppUpdateDialog = setShowAppUpdateDialog
          )
        } else {
          Spacer(modifier = Modifier.size(20.dp))
        }
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
  val todaysDate = DateString.getTodaysDate()
  Row(
    modifier = Modifier
      .clip(RoundedCornerShape(15.dp))
      .clickable {
        if (todaysDate > dailyWaterRecord.date) {
          roomDatabaseViewModel.refreshData()
        } else {
          roomDatabaseViewModel.insertDrinkLog(
            DrinkLogs(
              amount = waterAmount,
              icon = beverage.icon,
              beverage = beverage.name,
              date = dailyWaterRecord.date
            ),
          )
          roomDatabaseViewModel.updateDailyWaterRecord(
            DailyWaterRecord(
              goal = dailyWaterRecord.goal,
              currWaterAmount = dailyWaterRecord.currWaterAmount + waterAmount,
              date = dailyWaterRecord.date
            )
          )
        }
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

