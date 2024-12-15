package com.fatih.projectuts

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar // Import Snackbar

class MainActivity4 : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var updateButton: Button
    private lateinit var originalTitle: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        // Initialize UI components
        titleEditText = findViewById(R.id.editTextTitle)
        updateButton = findViewById(R.id.buttonSave)

        // Get task details passed from MainActivity2
        originalTitle = intent.getStringExtra("taskTitle") ?: ""
        titleEditText.setText(originalTitle)

        // Update button listener
        updateButton.setOnClickListener {
            updateTask()
        }
    }

    private fun updateTask() {
        val updatedTitle = titleEditText.text.toString()

        // Update SharedPreferences
        val sharedPreferences = getSharedPreferences("Habits", Context.MODE_PRIVATE)
        val taskSet = sharedPreferences.getStringSet("taskList", mutableSetOf())?.toMutableSet()
        taskSet?.remove(originalTitle) // Remove old task
        taskSet?.add(updatedTitle) // Add updated task
        sharedPreferences.edit().putStringSet("taskList", taskSet).apply()

        // Tampilkan Snackbar setelah tugas diedit
        val snackbar = Snackbar.make(findViewById(R.id.main), "Tugas telah diedit", Snackbar.LENGTH_SHORT)
        snackbar.show()

        // Navigate back to MainActivity2
        val intent = Intent(this, MainActivity2::class.java)
        startActivity(intent)
        finish()
    }
}







