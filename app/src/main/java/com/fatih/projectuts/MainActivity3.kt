package com.fatih.projectuts

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast // Tambahkan import ini
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity3 : AppCompatActivity() {

    // Declare UI components
    private lateinit var dateTextView: TextView
    private lateinit var timeTextView: TextView
    private lateinit var selectDateButton: Button
    private lateinit var selectTimeButton: Button
    private lateinit var titleEditText: EditText
    private lateinit var saveButton: Button

    private var selectedDate: String = ""
    private var selectedTime: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        // Initialize UI components
        dateTextView = findViewById(R.id.textViewDate)
        timeTextView = findViewById(R.id.textViewTime)
        selectDateButton = findViewById(R.id.buttonSelectDate)
        selectTimeButton = findViewById(R.id.buttonSelectTime)
        titleEditText = findViewById(R.id.editTextTitle)
        saveButton = findViewById(R.id.buttonSave)

        // Set up listeners for date and time pickers
        selectDateButton.setOnClickListener {
            showDatePicker()
        }

        selectTimeButton.setOnClickListener {
            showTimePicker()
        }

        // Save button listener
        saveButton.setOnClickListener {
            saveHabit()
        }
    }

    // Function to show the date picker dialog
    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            // Format and set the selected date in the TextView
            selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            dateTextView.text = selectedDate
        }, year, month, day)

        datePickerDialog.show()
    }

    // Function to show the time picker dialog
    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            // Format and set the selected time in the TextView
            selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
            timeTextView.text = selectedTime
        }, hour, minute, true)

        timePickerDialog.show()
    }

    // Function to save habit details
    private fun saveHabit() {
        val title = titleEditText.text.toString()

        // Validate that date and time have been selected
        if (selectedDate.isEmpty() || selectedTime.isEmpty()) {
            // Show an error message or handle empty date/time
            return
        }

        // Save to Shared Preferences
        val sharedPreferences = getSharedPreferences("Habits", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val taskSet = sharedPreferences.getStringSet("taskList", mutableSetOf())?.toMutableSet()

        // Format task as "title - date - time" and add it
        val formattedTask = "$title - $selectedDate - $selectedTime"
        taskSet?.add(formattedTask)
        editor.putStringSet("taskList", taskSet)
        editor.apply()

        // Show Toast message
        Toast.makeText(this, "Task Saved", Toast.LENGTH_SHORT).show()

        // Navigate back to MainActivity2
        val intent = Intent(this, MainActivity2::class.java)
        startActivity(intent)
        finish()
    }
}











