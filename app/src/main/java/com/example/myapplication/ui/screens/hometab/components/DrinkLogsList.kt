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
import com.example.myapplication.ui.screens.hometab.HomeTabViewModel
import com.example.myapplication.ui.theme.Typography
import com.example.myapplication.utils.TimeString

@ExperimentalFoundationApi
@Composable
fun DrinkLogsList(
  drinkLogsList: List<DrinkLogs>,
  homeTabViewModel: HomeTabViewModel
){
  Column(
    modifier = Modifier.fillMaxWidth(),
  ){
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
              Icon(painter = painterResource(it.icon), contentDescription = "Selected glass")
              Text(text = it.amount.toString(), style = Typography.subtitle1)
            }
            Row(
              verticalAlignment = Alignment.CenterVertically
            ){
              IconButton(
                onClick = { /*TODO*/ }
              ) {
                Icon(
                  painter = painterResource(R.drawable.edit_icon),
                  contentDescription = "Edit Button"
                )
              }
              IconButton(
                onClick = {
                  homeTabViewModel.deleteDrinkLog(it)
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