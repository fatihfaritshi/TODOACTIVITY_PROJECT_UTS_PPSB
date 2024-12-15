package com.fatih.projectuts

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Set up the window insets listener for padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Find the button and set an OnClickListener
        val buttonNext: Button = findViewById(R.id.button1) // Ensure this ID matches your XML
        buttonNext.setOnClickListener {
            // Create an Intent to start the next activity
            val intent = Intent(this, MainActivity2::class.java) // Ensure the activity class name is correct
            startActivity(intent)
        }
    }
}



