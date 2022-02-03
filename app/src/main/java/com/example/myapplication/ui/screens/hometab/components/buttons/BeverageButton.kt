package com.example.myapplication.ui.screens.hometab.components.buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.data.models.Beverage
import com.example.myapplication.ui.components.IconText
import com.example.myapplication.utils.Beverages

@Composable
fun BeverageButton(
  setShowBeverageDialog:(Boolean)->Unit,
  beverage: Beverage
) {
  Card(
    shape = RoundedCornerShape(15.dp),
    elevation = 6.dp,
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .clip(RoundedCornerShape(15.dp))
        .clickable { setShowBeverageDialog(true) },
    ) {
      Row(
        modifier = Modifier.padding(16.dp,2.dp),
        verticalAlignment = Alignment.CenterVertically,
        ) {
        IconText(
          text = " ${beverage.name} ",
          fontSize = 16.sp,
          icon = Beverages.IMAGE_MAPPER[beverage.icon],
          image = true
        )
        Icon(
          painter = painterResource(id = R.drawable.change_icon),
          contentDescription = "Change Beverage"
        )
      }
    }
  }
}