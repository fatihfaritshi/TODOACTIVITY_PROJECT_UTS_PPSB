package com.fatih.projectuts

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity2 : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskList: MutableList<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        // Initialize task list
        taskList = mutableListOf()

        // Setup RecyclerView
        recyclerView = findViewById(R.id.recyclerViewTasks)
        recyclerView.layoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(this, taskList, { task -> navigateToEditTask(task) }, { task -> deleteTask(task) })
        recyclerView.adapter = taskAdapter

        // Handle adding new tasks
        val buttonAddTask: androidx.appcompat.widget.AppCompatButton = findViewById(R.id.buttonAddTask)
        buttonAddTask.setOnClickListener {
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }
    }

    private fun deleteTask(task: Task) {
        // Buat dialog konfirmasi
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Konfirmasi")
        builder.setMessage("Apakah Anda yakin ingin menghapus tugas ini?")

        // Set tombol "Ya"
        builder.setPositiveButton("Ya") { dialog, which ->
            taskList.remove(task)
            taskAdapter.notifyDataSetChanged()
            saveTasks() // Simpan daftar tugas yang diperbarui

            // Tampilkan Toast setelah tugas dihapus
            Toast.makeText(this, "Tugas telah dihapus", Toast.LENGTH_SHORT).show()
        }

        // Set tombol "Tidak"
        builder.setNegativeButton("Tidak") { dialog, which ->
            dialog.cancel() // Menutup dialog jika memilih "Tidak"
        }

        // Tampilkan dialog
        builder.show()
    }

    private fun navigateToEditTask(task: Task) {
        val intent = Intent(this, MainActivity4::class.java)
        intent.putExtra("taskTitle", task.title) // Pass task details
        startActivity(intent)
    }

    private fun loadTasks() {
        val sharedPreferences = getSharedPreferences("Habits", Context.MODE_PRIVATE)
        val savedTasks = sharedPreferences.getStringSet("taskList", null)

        // Clear the current task list and load from shared preferences
        taskList.clear()
        savedTasks?.forEach {
            taskList.add(Task(it))
        }
        taskAdapter.notifyDataSetChanged()  // Notify adapter about the change
    }

    private fun saveTasks() {
        val sharedPreferences = getSharedPreferences("Habits", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val taskTitles = taskList.map { it.title }.toSet()
        editor.putStringSet("taskList", taskTitles)
        editor.apply()
    }

    override fun onResume() {
        super.onResume()
        // Reload tasks if any new task was added or edited
        loadTasks()
    }
}
