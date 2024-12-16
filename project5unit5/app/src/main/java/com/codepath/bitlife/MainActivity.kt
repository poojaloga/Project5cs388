package com.codepath.bitlife

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.bitlife.data.DisplayWaterIntake
import com.codepath.bitlife.data.WaterIntakeEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val entries = mutableListOf<DisplayWaterIntake>()
    private lateinit var adapter: WaterIntakeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = WaterIntakeAdapter(this, entries)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val db = (application as WaterIntakeApplication).db

        // Load entries from the database and update the adapter
        loadEntries(db)

        val addButton = findViewById<Button>(R.id.addButton)
        addButton.setOnClickListener {
            val date = findViewById<EditText>(R.id.dateInput).text.toString()
            val amount = findViewById<EditText>(R.id.amountInput).text.toString().toIntOrNull()

            if (date.isNotBlank() && amount != null && amount > 0) {
                val newEntry = WaterIntakeEntity(date = date, amount = amount)

                // Insert entry in the background and reload entries
                lifecycleScope.launch(Dispatchers.IO) {
                    db.waterIntakeDao().insertAll(listOf(newEntry))
                    loadEntries(db) // Refresh entries after adding new one
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "Entry added", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please enter valid data", Toast.LENGTH_SHORT).show()
            }
        }

        val clearButton = findViewById<Button>(R.id.clearButton) // Assuming you have a button with this ID
        clearButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val db = (application as WaterIntakeApplication).db
                db.waterIntakeDao().deleteAllEntries()

                withContext(Dispatchers.Main) {
                    entries.clear()
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this@MainActivity, "All entries deleted", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loadEntries(db: AppDatabase) {
        lifecycleScope.launch(Dispatchers.IO) {
            val databaseList = db.waterIntakeDao().getAllEntries().first()
            val mappedList = databaseList.map { entity ->
                DisplayWaterIntake(entity.date, entity.amount)
            }
            withContext(Dispatchers.Main) {
                adapter.updateEntries(mappedList) // Call updateEntries here
            }
        }
    }

}
