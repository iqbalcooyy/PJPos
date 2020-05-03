package com.iqbalproject.pj_pos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.adapter.viewHolder.StockPurchaseHolder
import com.iqbalproject.pj_pos.model.StockDetail

class StockPurchaseAdapter(private val stocks: List<StockDetail>) : RecyclerView.Adapter<StockPurchaseHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockPurchaseHolder {
        return StockPurchaseHolder(
            parent.context,
            LayoutInflater.from(parent.context).inflate(R.layout.item_purchase, parent, false)
        )
    }

    override fun getItemCount(): Int = stocks.size

    override fun onBindViewHolder(holder: StockPurchaseHolder, position: Int) {
        holder.bindView(stocks[position])
    }
}