package com.iqbalproject.pj_pos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.adapter.viewHolder.HistorySalesDetailHolder
import com.iqbalproject.pj_pos.model.HistorySalesResult

class HistorySalesDetailAdapter(private val sales_detail: List<HistorySalesResult>) :
    RecyclerView.Adapter<HistorySalesDetailHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistorySalesDetailHolder {
        return HistorySalesDetailHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_history_detail, parent, false)
        )
    }

    override fun getItemCount(): Int = sales_detail.size

    override fun onBindViewHolder(holder: HistorySalesDetailHolder, position: Int) {
        holder.bindView(sales_detail[position])
    }
}