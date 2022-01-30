package com.example.myapplication.ui.screens.edithistory.components

import androidx.compose.runtime.*
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.RoomDatabaseViewModel

@Composable
fun DataGraph(
  roomDatabaseViewModel: RoomDatabaseViewModel,
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  setSelectedDate: (String) -> Unit,
  selectedDate: String
) {
//  val listState = rememberLazyListState()
//  val coroutineScope = rememberCoroutineScope()
//  val firstWaterRecordDate = preferenceDataStoreViewModel.firstWaterDataDate.collectAsState(initial = DateString.NOT_SET)
//  val waterUnit = preferenceDataStoreViewModel.waterUnit.collectAsState(initial = Units.ML)
//  val dailyWaterGoal = preferenceDataStoreViewModel.dailyWaterGoal.collectAsState(
//    initial = if(waterUnit.value == Units.ML) RecommendedWaterIntake.DEFAULT_WATER_GOAL_IN_ML else RecommendedWaterIntake.DEFAULT_WATER_GOAL_IN_OZ
//  )
//  val dateRecordMapper = roomDatabaseViewModel.waterRecord.collectAsState()
//  var currDate = DateString.getTodaysDate()
//
//  Log.d("TAG", "DataGraph: ${firstWaterRecordDate.value}")
//
//  Box(
//    contentAlignment = Alignment.BottomEnd
//  ) {
//    LazyRow(
//      reverseLayout = true,
//      state = listState,
//      modifier = Modifier.padding(0.dp, 8.dp)
//    ) {
//      if(firstWaterRecordDate.value != DateString.NOT_SET) {
//        //Compose composable only when data is loaded
//        items(count = 33) {
//          while(currDate >= firstWaterRecordDate.value) {
//            val tempDate = currDate
//            roomDatabaseViewModel.getDailyWaterRecord(currDate)
//            Log.d("TAG", "DataGraph: $currDate - ${dateRecordMapper.value[currDate]}")
//            dateRecordMapper.value[currDate]?.date?.let { it1 ->
//              dateRecordMapper.value[currDate]?.currWaterAmount?.let { it2 ->
//                dateRecordMapper.value[currDate]?.goal?.let { it3 ->
//                  Bar(
//                    date = it1,
//                    waterAmount = it2,
//                    waterGoal = it3,
//                    setSelectedDate = setSelectedDate,
//                    selectedDate = selectedDate
//                  )
//                }
//              }
//            }
//
//            currDate = DateString.getPrevDate(currDate)
//          }
//        }
//      }
//    }
//
//    if(listState.firstVisibleItemIndex >= 4){
//      /*TODO("Need to optimize Lazy Row for this to work")*/
//      IconButton(onClick = { /*TODO*/ }) {
//        Icon(
//          painter = painterResource(id = R.drawable.up_arrow),
//          contentDescription = "Scroll to 0"
//        )
//      }
//    }
//  }
}