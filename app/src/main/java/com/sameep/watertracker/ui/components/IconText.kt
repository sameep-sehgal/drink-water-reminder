package com.sameep.watertracker.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.TextUnit

@Composable
fun IconText(
  modifier: Modifier = Modifier,
  text:String,
  fontSize: TextUnit,
  icon:Int?,
  image:Boolean = false,
) {
  val myId = "inlineContent"
  val textIcon = buildAnnotatedString {
    appendInlineContent(myId, "[icon]")
    append(text)
  }

  val inlineContent = mapOf(
    Pair(
      myId,
      InlineTextContent(
        Placeholder(
          width = fontSize,
          height = fontSize,
          placeholderVerticalAlign = PlaceholderVerticalAlign.Center
        )
      ) {
        if(image){
          icon?.let { it1 -> painterResource(id = it1) }?.let { it2 ->
            Image(
              painter = it2,
              contentDescription = "Icon $icon"
            )
          }
        }else{
          icon?.let { it1 -> painterResource(id = it1) }?.let { it2 ->
            Icon(
              painter = it2,
              contentDescription = "Icon $icon"
            )
          }
        }
      }
    )
  )

  Text(
    text = textIcon,
    inlineContent = inlineContent,
    fontSize = fontSize,
    modifier = modifier
  )
}