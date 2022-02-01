package com.example.myapplication.ui.screens.hometab.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.models.Beverage
import com.example.myapplication.utils.Beverages

@Composable
fun BeverageCard(
  beverage: Beverage,
  selected:Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val primaryColor = MaterialTheme.colors.primary
  val shape = RoundedCornerShape(15.dp)
  Card(
    elevation = if(!selected) 6.dp else 0.dp,
    modifier = modifier
      .fillMaxSize()
      .selectable(selected = selected, onClick = onClick)
      .border(
        width = if(selected) 4.dp else (-1).dp,
        color = primaryColor,
        shape = shape
      ),
    shape = shape
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Beverages.IMAGE_MAPPER[beverage.icon]?.let { painterResource(id = it) }?.let {
        Image(
          painter = it,
          contentDescription = "${beverage.name} icon",
          modifier = Modifier.size(40.dp)
        )
      }
      Text(
        text = beverage.name,
        fontSize = 11.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth().padding(0.dp, 6.dp, 0.dp, 0.dp),
        color = if(selected) primaryColor else MaterialTheme.colors.onSurface
      )
    }
  }
}