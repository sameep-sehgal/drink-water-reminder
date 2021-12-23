package com.example.myapplication.ui.screens.settingstab.components.settingsdialogcontent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.ui.components.ShowDialog
import com.example.myapplication.ui.theme.Typography
import com.example.myapplication.utils.AppTheme
import com.example.myapplication.utils.Settings

@Composable
fun SetAppThemeSettingDialog(
  appTheme: String,
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  setShowDialog:(Boolean) -> Unit,
) {
  val selectedAppTheme = remember { mutableStateOf(appTheme) }

  ShowDialog(
    title = "Set ${Settings.APP_THEME}",
    content = {
      //Bring Show Dialog here
      Column {
        Row(
          modifier = Modifier.clickable {
            selectedAppTheme.value = AppTheme.LIGHT
          }.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Checkbox(
            checked = selectedAppTheme.value == AppTheme.LIGHT,
            onCheckedChange = {selectedAppTheme.value = AppTheme.LIGHT}
          )
          Text(
            text = AppTheme.LIGHT,
            style = Typography.subtitle1
          )
        }
        Row(
          modifier = Modifier.clickable {
            selectedAppTheme.value = AppTheme.DEFAULT
          }.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Checkbox(
            checked = selectedAppTheme.value == AppTheme.DEFAULT,
            onCheckedChange = {selectedAppTheme.value = AppTheme.DEFAULT}
          )
          Text(
            text = AppTheme.DEFAULT,
            style = Typography.subtitle1
          )
        }
        Row(
          modifier = Modifier.clickable {
            selectedAppTheme.value = AppTheme.DARK
          }.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Checkbox(
            checked = selectedAppTheme.value == AppTheme.DARK,
            onCheckedChange = {selectedAppTheme.value = AppTheme.DARK}
          )
          Text(
            text = AppTheme.DARK,
            style = Typography.subtitle1
          )
        }
      }
    },
    setShowDialog = setShowDialog,
    onConfirmButtonClick = {
      preferenceDataStoreViewModel.setAppTheme(selectedAppTheme.value)
    }
  )
}