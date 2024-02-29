package com.example.sendproduct
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class MainActivity : AppCompatActivity(), ProductAdapter.OnProductClickListener {
    private lateinit var productRecyclerView: RecyclerView
    private lateinit var nextButton: Button
    private lateinit var productAdapter: ProductAdapter
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize DatabaseHelper
        databaseHelper = DatabaseHelper(this)

        // Define creative products
        val product1 = Product(1, "Eco-Friendly Bamboo Toothbrush", "100% Biodegradable toothbrush made from sustainable bamboo.", "GreenEarth Co.", 5.99, "bamboo_toothbrush_image_uri")
        val product2 = Product(2, "Galaxy-Inspired Handcrafted Soap", "Handmade soap with swirls of blue, purple, and silver representing a galaxy.", "Stellar Soaps", 8.49, "galaxy_soap_image_uri")
        val product3 = Product(3, "Mystical Crystal Necklace", "A beautiful necklace featuring a genuine crystal pendant on a silver chain.", "CrystalCraze", 29.99, "crystal_necklace_image_uri")
        val product4 = Product(4, "Vintage Polaroid Camera", "Classic instant camera perfect for capturing memories in retro style.", "RetroTech", 79.99, "polaroid_camera_image_uri")
        val product5 = Product(5, "Magical Unicorn Slippers", "Soft and fluffy slippers with unicorn horn and rainbow mane.", "Enchanted Feet", 14.99, "unicorn_slippers_image_uri")
        val product6 = Product(6, "Gourmet Artisanal Chocolate Truffles", "Handcrafted chocolate truffles in assorted flavors like salted caramel and raspberry rose.", "CocoaCraft", 24.99, "chocolate_truffles_image_uri")
        val product7 = Product(7, "Botanical Terrarium Kit", "DIY terrarium kit with a variety of succulents, moss, and decorative stones.", "NatureNest", 39.99, "terrarium_kit_image_uri")
        val product8 = Product(8, "Zen Garden Desktop Kit", "Miniature zen garden with sand, rocks, and a rake for stress relief and relaxation.", "SerenitySpace", 17.99, "zen_garden_image_uri")
        val product9 = Product(9, "Steampunk Inspired Pocket Watch", "Vintage-style pocket watch with intricate gears and cogs design.", "Clockwork Creations", 49.99, "steampunk_watch_image_uri")
        val product10 = Product(10, "Hand-Painted Silk Scarf", "Luxurious silk scarf hand-painted with vibrant floral motifs.", "SilkSensations", 34.99, "silk_scarf_image_uri")

        // Insert creative products into the database
        databaseHelper.insertProduct(product1)
        databaseHelper.insertProduct(product2)
        databaseHelper.insertProduct(product3)
        databaseHelper.insertProduct(product4)
        databaseHelper.insertProduct(product5)
        databaseHelper.insertProduct(product6)
        databaseHelper.insertProduct(product7)
        databaseHelper.insertProduct(product8)
        databaseHelper.insertProduct(product9)
        databaseHelper.insertProduct(product10)

        // Find views manually
        productRecyclerView = findViewById(R.id.productRecyclerView)
        nextButton = findViewById(R.id.nextButton)

        // Initialize RecyclerView
        productAdapter = ProductAdapter(this, databaseHelper.getAllProducts(), this)
        productRecyclerView.layoutManager = LinearLayoutManager(this)
        productRecyclerView.adapter = productAdapter


        nextButton.setOnClickListener {
            val selectedProducts = productAdapter.getSelectedProducts()
            if (selectedProducts.size >= 3) {
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("selectedProducts", ArrayList(selectedProducts))
                startActivity(intent)
            } else {
                Toast.makeText(this, "Select at least 3 products", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onProductClick(position: Int) {
        // Handle product selection if needed
        productAdapter.toggleSelection(position)

    }

    private fun getAllProductsFromDatabase(): List<Product> {
        // Implement code to retrieve products from SQLite database
        // Return a list of products
        return databaseHelper.getAllProducts()
    }
}