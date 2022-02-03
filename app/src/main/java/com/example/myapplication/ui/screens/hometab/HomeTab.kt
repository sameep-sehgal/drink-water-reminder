package com.example.myapplication.ui.screens.hometab

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.RoomDatabaseViewModel
import com.example.myapplication.ui.screens.hometab.components.*
import com.example.myapplication.ui.screens.hometab.components.buttons.BeverageButton
import com.example.myapplication.ui.screens.hometab.components.dialogs.*
import com.example.myapplication.utils.RecommendedWaterIntake
import com.example.myapplication.utils.Beverages
import com.example.myapplication.utils.Units

@Composable
fun HomeTab(
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  roomDatabaseViewModel: RoomDatabaseViewModel
){
  val drinkLogsList = roomDatabaseViewModel.drinkLogs.collectAsState()
  val todaysWaterRecord = roomDatabaseViewModel.todaysWaterRecord.collectAsState()
  val waterUnit = preferenceDataStoreViewModel.waterUnit.collectAsState(initial = Units.ML)
  val recommendedWaterIntake = preferenceDataStoreViewModel.recommendedWaterIntake.collectAsState(initial = RecommendedWaterIntake.NOT_SET)
  val beverageName = preferenceDataStoreViewModel.beverage.collectAsState(initial = Beverages.DEFAULT)
  val (showBeverageDialog, setShowBeverageDialog) =  remember { mutableStateOf(false) }
  val (showSetTodaysGoalDialog, setShowSetTodaysGoalDialog) =  remember { mutableStateOf(false) }
  val (showFruitDialog, setShowFruitDialog) =  remember { mutableStateOf(false) }
  val (showCustomAddWaterDialog, setShowCustomAddWaterDialog) =  remember { mutableStateOf(false) }
  val scrollState = rememberScrollState()
  var showAllDrinkLogs by remember{ mutableStateOf(false) }
  val beverage = roomDatabaseViewModel.beverage.collectAsState()
  val setBeverageName = { it:String -> preferenceDataStoreViewModel.setBeverage(it) }

  LaunchedEffect(key1 = beverageName.value) {
    roomDatabaseViewModel.getBeverage(beverageName.value)
  }

  Column(horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(scrollState),
    verticalArrangement = Arrangement.Center
  ){

    TodaysWaterRecord(
      currWaterAmount = todaysWaterRecord.value.currWaterAmount,
      goal = todaysWaterRecord.value.goal,
      waterUnit = waterUnit.value,
      setShowSetTodaysGoalDialog = setShowSetTodaysGoalDialog
    )

    Spacer(modifier = Modifier.size(8.dp))

    AnimatedHeartBrain(
      todaysWaterRecord.value.currWaterAmount,
      todaysWaterRecord.value.goal
    )

    Spacer(modifier = Modifier.size(8.dp))

    var columnModifier = Modifier.animateContentSize()

    if(showAllDrinkLogs) columnModifier = columnModifier.height(0.dp)

    Column(
      modifier = columnModifier,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {

      BeverageButton(
        setShowBeverageDialog = setShowBeverageDialog,
        beverage = beverage.value
      )

      Spacer(modifier = Modifier.size(16.dp))

      AddWaterButtonsRow(
        waterUnit.value,
        roomDatabaseViewModel,
        todaysWaterRecord.value,
        preferenceDataStoreViewModel,
        setShowCustomAddWaterDialog,
        setShowFruitDialog,
        beverage = beverage.value
      )

      Spacer(modifier = Modifier.size(12.dp))

    }

    Text(
      modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp),
      text = "Today's Logs",
      textAlign = TextAlign.Center,
      fontSize = 18.sp
    )

    var columnModifier2 = Modifier.animateContentSize()
    val scrollState2 = rememberScrollState()

    if(showAllDrinkLogs)
      columnModifier2 = columnModifier2
        .height(200.dp)
        .verticalScroll(scrollState2)

    /*TODO(Fix this columns initial height equal to 2 drinkLogs)*/
    Column(
      modifier = columnModifier2
    ) {
      DrinkLogsList(
        drinkLogsList =
        if(drinkLogsList.value.size>2 && !showAllDrinkLogs)
          drinkLogsList.value.subList(0,2)
        else drinkLogsList.value,
        roomDatabaseViewModel = roomDatabaseViewModel,
        waterUnit = waterUnit.value,
        dailyWaterRecord = todaysWaterRecord.value
      )
    }

    if(drinkLogsList.value.size > 2) {
      Text(
        text =
        if(!showAllDrinkLogs)
          "View All"
        else "Collapse",
        textDecoration = TextDecoration.Underline,
        color = MaterialTheme.colors.primary,
        fontSize = 12.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
          .clickable{
            showAllDrinkLogs = !showAllDrinkLogs
          }
      )
    }



    //Dialogs Code
    if(showBeverageDialog){
      BeverageDialog(
        setShowBeverageDialog = setShowBeverageDialog,
        setSelectedBeverage = setBeverageName,
        selectedBeverage = beverageName.value,
        roomDatabaseViewModel = roomDatabaseViewModel
      )
    }
    if(showSetTodaysGoalDialog){
      SetWaterGoalDialog(
        setShowSetTodaysGoalDialog = setShowSetTodaysGoalDialog,
        roomDatabaseViewModel = roomDatabaseViewModel,
        dailyWaterRecord = todaysWaterRecord.value,
        waterUnit = waterUnit.value,
        recommendedWaterIntake = recommendedWaterIntake.value
      )
    }
    if(showFruitDialog){
      FruitDialog(setShowFruitDialog = setShowFruitDialog)
    }
    if(showCustomAddWaterDialog){
      CustomAddWaterDialog(
        waterUnit = waterUnit.value,
        setShowCustomAddWaterDialog = setShowCustomAddWaterDialog,
        dailyWaterRecord = todaysWaterRecord.value,
        roomDatabaseViewModel = roomDatabaseViewModel,
        beverage = beverage.value
      )
    }
  }
}

