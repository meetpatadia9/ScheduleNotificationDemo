package com.codebyzebru.schedulenotificationdemo.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codebyzebru.schedulenotificationdemo.R
import com.codebyzebru.schedulenotificationdemo.notification.ScheduleNotification
import com.codebyzebru.schedulenotificationdemo.ui.baseutils.TimePickerDialog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

const val TAG = "AddScheduleScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScheduleScreen() {
    val context = LocalContext.current

    val date = remember { Calendar.getInstance().timeInMillis }
    val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    var scheduleText by remember { mutableStateOf("") }
    var scheduleDate by remember { mutableStateOf("") }
    var scheduleTime by rememberSaveable { mutableStateOf("") }
    val placeholderTime by remember { mutableStateOf("12:00") }

    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = date)
    var showDatePicker by remember { mutableStateOf(false) }

    val timePickerState = rememberTimePickerState()
    var showTimePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val selectedDate = Calendar.getInstance().apply {
                            timeInMillis = datePickerState.selectedDateMillis!!
                        }
                        scheduleDate = formatter.format(selectedDate.time)
                        showDatePicker = false
                        Log.d(TAG, "scheduleDate: $scheduleDate")
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton( onClick = { showDatePicker = false }
                ) { Text("Cancel") }
            }
        ) { DatePicker(state = datePickerState) }
    }

    if (showTimePicker) {
        TimePickerDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        scheduleTime = "${timePickerState.hour}:${timePickerState.minute}"
                        showTimePicker = false
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(
                    onClick = { showTimePicker = false }
                ) { Text("Cancel") }
            }
        ) { TimePicker(state = timePickerState) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Text(
            text = "ADD REMINDER",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Schedule name:",
            modifier = Modifier.padding(top = 9.dp, start = 7.dp),
            fontWeight = FontWeight.W500
        )
        OutlinedTextField(
            value = scheduleText,
            onValueChange = { if (it.length <= 25) scheduleText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(7.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done
            ),
            maxLines = Int.MAX_VALUE,
            placeholder = {
                Text(
                    text = "Schedule name",
                    color = Color.LightGray
                )
            }
        )   // OutlinedTextField: schedule date

        Text(
            text = "Schedule date:",
            modifier = Modifier.padding(top = 12.dp, start = 7.dp),
            fontWeight = FontWeight.W500
        )
        OutlinedTextField(
            value = scheduleDate,
            onValueChange = { if (it.length <= 25) scheduleDate = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(7.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done
            ),
            maxLines = Int.MAX_VALUE,
            placeholder = {
                Text(
                    text = "Schedule date",
                    color = Color.LightGray
                )
            },
            trailingIcon = {
                Image(
                    imageVector = Icons.Rounded.DateRange,
                    contentDescription = "Date picker",
                    modifier = Modifier.clickable { showDatePicker = true }
                )
            }
        )   // OutlinedTextField: schedule date

        Text(
            text = "Schedule time:",
            modifier = Modifier.padding(top = 12.dp, start = 7.dp),
            fontWeight = FontWeight.W500
        )
        OutlinedTextField(
            value = scheduleTime,
            placeholder = {
                Text(
                    text = placeholderTime,
                    color = Color.LightGray
                )
            },
            onValueChange = { scheduleTime = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(7.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done
            ),
            maxLines = Int.MAX_VALUE,
            trailingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.round_schedule_24),
                    contentDescription = "Date picker",
                    modifier = Modifier.clickable {
                        showTimePicker = true
                    }
                )
            }
        )   // OutlinedTextField: schedule time

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp)
        ) {
            Button(
                onClick = {
                    ScheduleNotification().scheduleNotification(context, timePickerState, datePickerState, scheduleText)
                    scheduleText = ""
                    scheduleDate = ""
                    scheduleTime = ""
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) { Text(text = "Add reminder") }   // Button
        }   // Row
    }   // Column
}