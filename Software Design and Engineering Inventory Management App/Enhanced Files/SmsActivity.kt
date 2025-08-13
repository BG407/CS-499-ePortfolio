package com.example.inventoryapp_brucegaudet

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class SmsActivity : AppCompatActivity() {

    private lateinit var txtResult: TextView
    private lateinit var requestButton: Button

    private val SMS_PERMISSION_CODE = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms)

        txtResult = findViewById(R.id.txtPermissionResult)
        requestButton = findViewById(R.id.btnRequestPermission)

        requestButton.setOnClickListener {
            handleSmsPermission()
        }
    }

    private fun handleSmsPermission() {
        val permission = Manifest.permission.SEND_SMS
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), SMS_PERMISSION_CODE)
        } else {
            txtResult.text = "SMS permission already granted"
            sendTestSms()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == SMS_PERMISSION_CODE) {
            val granted = grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
            if (granted) {
                txtResult.text = "SMS permission granted!"
                sendTestSms()
            } else {
                txtResult.text = "Permission denied. Cannot send SMS."
                Toast.makeText(this, "You must grant permission to use SMS features.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendTestSms() {
        val phoneNumber = "5554" // Use an emulator-friendly or safe test number
        val message = "Test SMS from InventoryApp!"

        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Toast.makeText(this, "Test SMS sent successfully", Toast.LENGTH_SHORT).show()
            txtResult.text = "SMS sent to $phoneNumber"
        } catch (e: Exception) {
            txtResult.text = "SMS failed: ${e.message}"
            Toast.makeText(this, "SMS failed: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}

