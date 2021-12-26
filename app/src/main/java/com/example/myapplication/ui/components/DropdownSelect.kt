package com.example.myapplication.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize

@Composable
fun DropdownSelect() {
  var expanded by remember { mutableStateOf(false) }
  val suggestions = listOf("Item1","Item2","Item3")
  var selectedText by remember { mutableStateOf("Glass") }

  var textfieldSize by remember { mutableStateOf(Size.Zero)}

  val icon = if (expanded)
    Icons.Filled.KeyboardArrowUp //it requires androidx.compose.material:material-icons-extended
  else
    Icons.Filled.KeyboardArrowDown


  Column() {
    Row(
      modifier = Modifier.border(
        width = 1.dp,
        color = if(expanded) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface
      ).clickable { expanded = !expanded }
        .onGloballyPositioned { coordinates ->
        //This value is used to assign to the DropDown the same width
        textfieldSize = coordinates.size.toSize()
      },
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = selectedText,
        fontSize = 16.sp,
        modifier = Modifier.padding(4.dp),
        color = if(expanded) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface
      )
      Icon(
        icon,
        contentDescription = "dropdown",
        tint = if(expanded) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface
      )
    }
//    OutlinedTextField(
//      value = selectedText,
//      onValueChange = { selectedText = it },
//      modifier = Modifier
//        .fillMaxWidth()
//        .onGloballyPositioned { coordinates ->
//          //This value is used to assign to the DropDown the same width
//          textfieldSize = coordinates.size.toSize()
//        },
//      label = {Text("Label")},
//      trailingIcon = {
//        Icon(icon,"contentDescription",
//          Modifier.clickable { expanded = !expanded })
//      },
//      keyboardOptions = KeyboardOptions(keyboardType = KeyBoard)
//    )
    DropdownMenu(
      expanded = expanded,
      onDismissRequest = { expanded = false },
      modifier = Modifier
        .width(with(LocalDensity.current){textfieldSize.width.toDp()})
    ) {
      suggestions.forEach { label ->
        DropdownMenuItem(onClick = {
          selectedText = label
          expanded = false
        }) {
          Text(text = label)
        }
      }
    }
  }
}