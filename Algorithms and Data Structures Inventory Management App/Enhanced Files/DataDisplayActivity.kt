package com.example.inventoryapp_brucegaudet

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DataDisplayActivity : AppCompatActivity() {

    private lateinit var itemNameInput: EditText
    private lateinit var itemQuantityInput: EditText
    private lateinit var itemListDisplay: TextView
    private lateinit var addItemButton: Button

    private val inventoryList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_display)

        itemNameInput = findViewById(R.id.editItemName)
        itemQuantityInput = findViewById(R.id.editItemQuantity)
        itemListDisplay = findViewById(R.id.textItemList)
        addItemButton = findViewById(R.id.btnAddItem)

        addItemButton.setOnClickListener {
            val itemName = itemNameInput.text.toString().trim()
            val quantityText = itemQuantityInput.text.toString().trim()

            if (itemName.isEmpty() || quantityText.isEmpty()) {
                Toast.makeText(this, "Please enter item name and quantity", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val quantity = quantityText.toIntOrNull()
            if (quantity == null || quantity <= 0) {
                Toast.makeText(this, "Quantity must be a positive number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val itemEntry = "$itemName x $quantity"
            inventoryList.add(itemEntry)
            updateItemListDisplay()

            // Clear inputs
            itemNameInput.text.clear()
            itemQuantityInput.text.clear()

            // TEMP: Navigate to SMS screen (you can move this elsewhere later)
            startActivity(Intent(this, SmsActivity::class.java))
        }
    }

    private fun updateItemListDisplay() {
        itemListDisplay.text = "Inventory:\n" + inventoryList.joinToString("\n")
    }
}

