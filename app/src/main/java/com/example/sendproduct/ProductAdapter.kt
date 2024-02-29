package com.example.sendproduct

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.os.Handler
import android.os.Looper

class ProductAdapter(
    private val context: Context,
    private val productList: List<Product>,
    private val listener: OnProductClickListener
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val selectedProducts: MutableList<Product> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun getSelectedProducts(): List<Product> {
        return selectedProducts
    }

    fun toggleSelection(position: Int)  {
        val product = productList[position]
        if (selectedProducts.contains(product)) {
            selectedProducts.remove(product)
        } else {
            selectedProducts.add(product)
        }

        // Post the call to notifyItemChanged(position) to the main thread's message queue
        Handler(Looper.getMainLooper()).post {
            notifyItemChanged(position)
        }
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)

        fun bind(product: Product) {
            nameTextView.text = product.name
            descriptionTextView.text = product.description
            checkBox.isChecked = selectedProducts.contains(product)

            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onProductClick(position)
                }
            }

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                toggleSelection(adapterPosition)
            }
        }
    }

    interface OnProductClickListener {
        fun onProductClick(position: Int)
    }
}
