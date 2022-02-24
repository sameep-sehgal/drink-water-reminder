package com.sameep.watertracker.ui.components.dialogs

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ShowDialog(
  title:String,
  content:@Composable()()->Unit,
  setShowDialog:(Boolean)->Unit,
  onConfirmButtonClick:() -> Unit,
  showConfirmButton:Boolean = true,
  showDismissButton:Boolean = true,
) {

  AlertDialog(
    onDismissRequest = { onDialogDismiss(setShowDialog) },
    title = {
      Text(
        title,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
      )
    },
    confirmButton = {
      if(showConfirmButton){
        Button(
          onClick = {
            onConfirmButtonClick()
            setShowDialog(false)
          },
        ) {
          Text("Confirm")
        }
      }
    },
    dismissButton = {
      if(showDismissButton){
        Button(
          onClick = {
            // Change the state to close the dialog
            setShowDialog(false)
          },
        ) {
          Text("Dismiss")
        }
      }
    },
    text = {
      content()
    },
  )
}


fun onDialogDismiss(setShowDialog: (Boolean) -> Unit){
  setShowDialog(false)
}