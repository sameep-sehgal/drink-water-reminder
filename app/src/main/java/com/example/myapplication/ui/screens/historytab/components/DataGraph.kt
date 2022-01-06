package com.example.myapplication.ui.screens.historytab.components

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.R
import com.example.myapplication.RoomDatabaseViewModel
import com.example.myapplication.data.models.DailyWaterRecord
import com.example.myapplication.utils.DateString
import com.example.myapplication.utils.RecommendedWaterIntake
import com.example.myapplication.utils.Units

@Composable
fun DataGraph(
  roomDatabaseViewModel: RoomDatabaseViewModel,
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  setSelectedDate: (String) -> Unit,
  selectedDate: String
) {
  val listState = rememberLazyListState()
  val coroutineScope = rememberCoroutineScope()
  val firstWaterRecordDate = preferenceDataStoreViewModel.firstWaterDataDate.collectAsState(initial = DateString.NOT_SET)
  val waterUnit = preferenceDataStoreViewModel.waterUnit.collectAsState(initial = Units.ML)
  val dailyWaterGoal = preferenceDataStoreViewModel.dailyWaterGoal.collectAsState(
    initial = if(waterUnit.value == Units.ML) RecommendedWaterIntake.DEFAULT_WATER_GOAL_IN_ML else RecommendedWaterIntake.DEFAULT_WATER_GOAL_IN_OZ
  )
  val dateRecordMapper = roomDatabaseViewModel.waterRecord.collectAsState()
  var currDate = DateString.getTodaysDate()

  Log.d("TAG", "DataGraph: ${firstWaterRecordDate.value}")
  
  Box(
    contentAlignment = Alignment.BottomEnd
  ) {
    LazyRow(
      reverseLayout = true,
      state = listState,
      modifier = Modifier.padding(0.dp, 8.dp)
    ) {
      if(firstWaterRecordDate.value != DateString.NOT_SET) {
        //Compose composable only when data is loaded
        items(count = 33) {
          while(currDate >= firstWaterRecordDate.value) {
            val tempDate = currDate
            roomDatabaseViewModel.getDailyWaterRecord(currDate)
            Log.d("TAG", "DataGraph: $currDate - ${dateRecordMapper.value[currDate]}")
            dateRecordMapper.value[currDate]?.date?.let { it1 ->
              dateRecordMapper.value[currDate]?.currWaterAmount?.let { it2 ->
                dateRecordMapper.value[currDate]?.goal?.let { it3 ->
                  Bar(
                    date = it1,
                    waterAmount = it2,
                    waterGoal = it3,
                    setSelectedDate = setSelectedDate,
                    selectedDate = selectedDate
                  )
                }
              }
            }

            currDate = DateString.getPrevDate(currDate)
          }
        }
      }
    }

    if(listState.firstVisibleItemIndex >= 4){
      /*TODO("Need to optimize Lazy Row for this to work")*/
      IconButton(onClick = { /*TODO*/ }) {
        Icon(
          painter = painterResource(id = R.drawable.up_arrow),
          contentDescription = "Scroll to 0"
        )
      }
    }
  }
}