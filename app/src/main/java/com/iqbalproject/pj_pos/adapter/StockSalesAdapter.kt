package com.iqbalproject.pj_pos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.adapter.viewHolder.StockSalesHolder
import com.iqbalproject.pj_pos.model.StockDetail

class StockSalesAdapter(private val stocks: List<StockDetail>) : RecyclerView.Adapter<StockSalesHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockSalesHolder {
        return StockSalesHolder(
            parent.context,
            LayoutInflater.from(parent.context).inflate(R.layout.items_sale, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return stocks.size
    }

    override fun onBindViewHolder(holder: StockSalesHolder, position: Int) {
        holder.bindView(stocks[position], stocks)
    }
}