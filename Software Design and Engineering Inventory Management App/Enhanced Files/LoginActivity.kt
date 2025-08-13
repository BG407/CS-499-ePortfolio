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

            if (!isValidUsername(username) || !isValidPassword(password)) {
                Toast.makeText(this, "Enter a valid username and password", Toast.LENGTH_SHORT).show()
            } else {
                val valid = dbHelper.checkUser(username, password)
                if (valid) {
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, DataDisplayActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // CREATE ACCOUNT
        createAccountButton.setOnClickListener {
            val username = usernameInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (!isValidUsername(username) || !isValidPassword(password)) {
                Toast.makeText(this, "Username must be 4–20 characters, password at least 6", Toast.LENGTH_SHORT).show()
            } else {
                val success = dbHelper.addUser(username, password)
                if (success) {
                    Toast.makeText(this, "Account created!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error: username may already exist", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun isValidUsername(username: String): Boolean {
        val regex = "^[a-zA-Z0-9_]{4,20}$".toRegex()
        return username.matches(regex)
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }
}
