package com.iqbalproject.pj_pos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.adapter.viewHolder.ProductViewHolder
import com.iqbalproject.pj_pos.model.StockDetail

class ProductAdapter(private val product: List<StockDetail>): RecyclerView.Adapter<ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            parent.context,
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product, parent, false)
        )
    }

    override fun getItemCount(): Int = product.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindView(product[position])
    }
}