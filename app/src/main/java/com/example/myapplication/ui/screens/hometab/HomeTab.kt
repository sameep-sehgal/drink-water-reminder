package com.example.myapplication.ui.screens.hometab

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.data.models.DrinkLogs
import com.example.myapplication.ui.screens.hometab.components.*
import com.example.myapplication.ui.screens.hometab.components.buttons.BackToTopButton
import com.example.myapplication.ui.screens.hometab.components.buttons.FloatingAlarmButton
import com.example.myapplication.ui.screens.hometab.components.dialogs.ReminderTimingsDialog
import com.example.myapplication.ui.screens.hometab.screens.*
import com.example.myapplication.ui.theme.Typography
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun HomeTab():Unit{
    val homeTabViewModel = viewModel<HomeTabViewModel>()
    val key = homeTabViewModel.savedKey.collectAsState()
    val drinkLogsList = homeTabViewModel.drinkLogs.collectAsState()
    val todaysWaterRecord = homeTabViewModel.waterRecord.collectAsState()
    val (showWorkoutDialog, setShowWorkoutDialog) =  remember { mutableStateOf(false) }
    val (showWeatherDialog, setShowWeatherDialog) =  remember { mutableStateOf(false) }
    val (showResetDialog, setShowResetDialog) =  remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Scaffold(
        floatingActionButton = {
            Column(horizontalAlignment = Alignment.CenterHorizontally){
                BackToTopButton(scrollState)
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ){
            Text(text = "${todaysWaterRecord.value.currWaterAmount}/${todaysWaterRecord.value.goal}")
            Row(modifier = Modifier.height(200.dp)) {
                Column(
                    Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    UndoButton(modifier = Modifier
                        .weight(1f)
                        .clickable(enabled = true) {
                            Log.d("TAG11", "onCreate: ${key.value}")
                        })

                    ResetButton(modifier = Modifier.weight(1f), setShowResetDialog = setShowResetDialog)
                }

                AnimatedWaterGlass()

                Column(
                    Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    WeatherButton(modifier = Modifier.weight(1f), setShowWeatherDialog = setShowWeatherDialog)

                    WorkoutButton(modifier = Modifier.weight(1f), setShowWorkoutDialog = setShowWorkoutDialog)
                }
            }

            Row{
                Text(text = "Today's Records", style = Typography.body1)
                Text(text = "+Add", Modifier.clickable(enabled = true,
                    onClick = {
                        homeTabViewModel.insertDrinkLog(
                            DrinkLogs(amount = 100, icon = R.drawable.soda_bottle_5)
                        )
                    }
                ))
            }


            DrinkLogsList(
                drinkLogsList = drinkLogsList.value,
                homeTabViewModel = homeTabViewModel
            )


            Spacer(modifier = Modifier.height(96.dp))


            if(showWorkoutDialog){
                WorkoutDialog(setShowWorkoutDialog = setShowWorkoutDialog)
            }
            if(showWeatherDialog){
                WeatherDialog(setShowWeatherDialog = setShowWeatherDialog)
            }
            if(showResetDialog){
                ResetDialog(setShowResetDialog = setShowResetDialog)
            }
        }
    }
}
