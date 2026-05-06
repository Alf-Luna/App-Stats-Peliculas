package com.mooncowpines.kinostats.ui.components

import android.app.DatePickerDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api:: class)
@Composable
fun KinoCalendar(
    onDismissRequest: () -> Unit,
    onDateSelected: (Long?) -> Unit
) {

    val calendarState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {KinoButton(text = "Ok", onClick = {onDateSelected((calendarState.selectedDateMillis))})},
        dismissButton = {KinoButton(text = "Cancel", onClick = onDismissRequest)},

    ) {
        DatePicker(state = calendarState)
    }

}
