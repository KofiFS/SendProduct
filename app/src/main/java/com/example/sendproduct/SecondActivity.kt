package com.example.sendproduct

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SecondActivity : AppCompatActivity() {
    private lateinit var selectedProducts: ArrayList<Product>
    private lateinit var selectedProductRecyclerView: RecyclerView
    private lateinit var sendEmailButton: Button
    private lateinit var backButton: Button
    private lateinit var emailEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // Retrieve selected products from intent extras
        selectedProducts = intent.getSerializableExtra("selectedProducts") as ArrayList<Product>

        // Find RecyclerView, Button, and EditText
        selectedProductRecyclerView = findViewById(R.id.selectedProductRecyclerView)
        sendEmailButton = findViewById(R.id.sendEmailButton)
        backButton = findViewById(R.id.backButton)
        emailEditText = findViewById(R.id.emailEditText)

        // Set up RecyclerView to display selected products
        val adapter = SelectedProductAdapter(selectedProducts)
        selectedProductRecyclerView.layoutManager = LinearLayoutManager(this)
        selectedProductRecyclerView.adapter = adapter

        // Set up click listener for the send email button
        sendEmailButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()

            // Check if the email is valid
            if (isValidEmail(email)) {
                // Create an Intent to send email
                val emailIntent = Intent(Intent.ACTION_SEND)
                emailIntent.type = "text/plain"
                emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Selected Products")
                emailIntent.putExtra(Intent.EXTRA_TEXT, generateEmailBody(selectedProducts))

                // Start an activity to send the email
                startActivity(Intent.createChooser(emailIntent, "Send Email"))

                // Display toast indicating completion
                Toast.makeText(this, "Email sent successfully", Toast.LENGTH_SHORT).show()

                // Clear the selected products and update the adapter
                selectedProducts.clear()
                adapter.notifyDataSetChanged()
            } else {
                // Display toast for invalid email
                Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
            }
        }

        // Set up click listener for the back button
        backButton.setOnClickListener {
            // Navigate back to MainActivity
            onBackPressed()
        }
    }

    // Function to validate email address
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Function to generate email body with selected products
    private fun generateEmailBody(products: List<Product>): String {
        val stringBuilder = StringBuilder()
        for (product in products) {
            stringBuilder.append("Product Name: ${product.name}\n")
            stringBuilder.append("Description: ${product.description}\n")
            stringBuilder.append("Seller: ${product.seller}\n")
            stringBuilder.append("Price: ${product.price}\n\n")
        }
        return stringBuilder.toString()
    }
}
