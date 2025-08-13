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
            checkSmsPermission()
        }
    }

    private fun checkSmsPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.SEND_SMS),
                SMS_PERMISSION_CODE
            )
        } else {
            txtResult.text = "SMS Permission already granted"
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
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                txtResult.text = "SMS Permission granted!"
                sendTestSms()
            } else {
                txtResult.text = "Permission denied. SMS feature unavailable."
            }
        }
    }

    private fun sendTestSms() {

        try {
            val smsManager = SmsManager.getDefault()
            val phoneNumber = "5554" // Fake/emulator number or your test device
            val message = "Test SMS from InventoryApp!"

            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Toast.makeText(this, "Test SMS sent", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "SMS failed: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
