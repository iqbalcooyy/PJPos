package com.iqbalproject.pj_pos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.adapter.viewHolder.HistorySalesHolder
import com.iqbalproject.pj_pos.model.Sales

class HistorySalesAdapter(private val historySales: List<Sales>) :
    RecyclerView.Adapter<HistorySalesHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistorySalesHolder {
        return HistorySalesHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_history, parent, false)
        )
    }

    override fun getItemCount(): Int = historySales.size

    override fun onBindViewHolder(holder: HistorySalesHolder, position: Int) {
        holder.bindView(historySales[position])
    }
}