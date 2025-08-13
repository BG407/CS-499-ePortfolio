package com.example.inventoryapp_brucegaudet

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var nameText: EditText
    private lateinit var textGreeting: TextView
    private lateinit var buttonSayHello: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameText = findViewById(R.id.nameText)
        textGreeting = findViewById(R.id.textGreeting)
        buttonSayHello = findViewById(R.id.buttonSayHello)

        // Button starts disabled
        buttonSayHello.isEnabled = false

        // Watch text changes to enable/disable button
        nameText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                buttonSayHello.isEnabled = !s.isNullOrBlank()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        buttonSayHello.setOnClickListener {
            val nameInput = nameText.text.toString().trim()

            if (isValidName(nameInput)) {
                textGreeting.text = "Hello, $nameInput!"
            } else {
                textGreeting.text = ""
                Toast.makeText(this, "Invalid input. Please enter a valid name.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidName(name: String): Boolean {
        // Basic validation: no special characters, not empty, reasonable length
        val regex = "^[a-zA-Z\s]{1,50}$".toRegex()
        return name.matches(regex)
    }
}
