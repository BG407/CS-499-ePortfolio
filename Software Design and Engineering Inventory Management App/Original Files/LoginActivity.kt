package com.example.inventoryapp_brucegaudet

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var dbHelper: UserDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton = findViewById<Button>(R.id.btnLogin)
        val createAccountButton = findViewById<Button>(R.id.btnCreateAccount)
        val usernameInput = findViewById<EditText>(R.id.editUsername)
        val passwordInput = findViewById<EditText>(R.id.editPassword)

        dbHelper = UserDatabaseHelper(this)

        // LOGIN
        loginButton.setOnClickListener {
            val username = usernameInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter login info", Toast.LENGTH_SHORT).show()
            } else {
                val valid = dbHelper.checkUser(username, password)
                if (valid) {
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, DataDisplayActivity::class.java))
                } else {
                    Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // CREATE ACCOUNT
        createAccountButton.setOnClickListener {
            val username = usernameInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show()
            } else {
                val success = dbHelper.addUser(username, password)
                if (success) {
                    Toast.makeText(this, "Account created!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error creating account", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
