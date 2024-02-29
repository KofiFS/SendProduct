package com.example.sendproduct

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SelectedProductAdapter (private val selectedProducts: List<Product>) : RecyclerView.Adapter<SelectedProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product_selected, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = selectedProducts[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return selectedProducts.size
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.selectedProductNameTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.selectedProductDescriptionTextView)

        fun bind(product: Product) {
            nameTextView.text = product.name
            descriptionTextView.text = product.description
        }
    }
}