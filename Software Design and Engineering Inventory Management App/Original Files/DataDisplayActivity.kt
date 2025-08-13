package com.example.inventoryapp_brucegaudet

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class DataDisplayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_display)

        val addButton = findViewById<Button>(R.id.btnAddItem)

        // TEMP: Navigate to SMS screen
        addButton.setOnClickListener {
            startActivity(Intent(this, SmsActivity::class.java))
        }
    }
}
