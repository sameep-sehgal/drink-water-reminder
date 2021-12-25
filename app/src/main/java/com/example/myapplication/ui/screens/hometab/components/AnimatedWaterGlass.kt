package com.example.myapplication.ui.screens.hometab.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.myapplication.R

@Composable
fun AnimatedWaterGlass(
  currWaterAmount:Int,
  goal:Int
){
  val currentPercentage = remember { Animatable(0f) }
  LaunchedEffect(currWaterAmount) {
    //Run when the key(percentage) changes
    //Percentage must be 'remembered' to detect change here
    if(currWaterAmount.toFloat()/goal < 1f){
      currentPercentage.animateTo(
        currWaterAmount.toFloat()/goal,
        animationSpec = tween(durationMillis = 1000)
      )
    }else{
      currentPercentage.animateTo(
        1f,
        animationSpec = tween(durationMillis = 1000)
      )
    }
  }
  var cc by remember { mutableStateOf(IntSize.Zero) }

  Image(painter = painterResource(R.drawable.water_glass_with_bg),
    contentDescription = "Water Glass",
    modifier = Modifier
      .padding(0.dp)
      .onGloballyPositioned {
        cc = it.size
      }
      .background(
        Brush.verticalGradient(
          colors = listOf(Color.Cyan, Color.Transparent),
          startY = (cc.height + 45f) * (1 - currentPercentage.value + 0.01f),
          endY = cc.height * (1 - currentPercentage.value + 0.01f)
        )
      )
  )
}



//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .clip(shape = RoundedCornerShape(size = 12.dp))
//                        .onGloballyPositioned {
//                            cc = it.size
//                        }
//                        .background(
////                            Brush.verticalGradient(
////                                (1f-currentPercentage.value) to Color.Transparent,
////                                currentPercentage.value to Color.Cyan,
////                            )
//                            Brush.verticalGradient(
//                                colors = listOf(Color.Cyan, Color.Transparent),
//                                startY = (cc.height+60f)*(1-currentPercentage.value +0.01f),
//                                endY = cc.height*(1-currentPercentage.value+0.01f)
//                            )
//                        )
//                        .border(width = 8.dp, color = Color(0xFF565656)),
//                    contentAlignment = Alignment.Center
//                ){
//                    Row() {
//                        Button(onClick = {
//                            if(percentage < 0.95f)percentage += 0.1f
//                        }) {
//                            Text(text = (100*currentPercentage.value).toInt().toString())
//                        }
//                    }
//                }