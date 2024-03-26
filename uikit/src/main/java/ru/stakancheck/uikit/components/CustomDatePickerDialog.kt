/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.uikit.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    onDateSelected: (Long) -> Unit,
    selectedDateSeconds: Long? = null,
    title: String,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDateSeconds?.let { it * 1000L },
        initialDisplayedMonthMillis = null,
        yearRange = IntRange(2023, 2100),
        initialDisplayMode = DisplayMode.Picker
    )

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        shape = MaterialTheme.shapes.small,
        confirmButton = {
            Button(
                onClick = {
                    datePickerState.selectedDateMillis?.let {
                        onDateSelected(it / 1000L)
                    }
                    onDismiss()
                },
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = "Выбрать")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                },
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = "Отмена")
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )
            },
            headline = null
        )
    }
}