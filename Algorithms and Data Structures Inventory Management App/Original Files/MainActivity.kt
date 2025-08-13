package com.example.inventoryapp_brucegaudet

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                buttonSayHello.isEnabled = !s.isNullOrBlank()
            }
        })
    }

    fun SayHello(view: View) {
        val name = nameText.text.toString().trim()
        if (name.isEmpty()) return
        textGreeting.text = "Hello, $name!"
    }
}
