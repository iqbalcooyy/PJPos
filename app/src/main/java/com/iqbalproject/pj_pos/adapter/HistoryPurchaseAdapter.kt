package com.iqbalproject.pj_pos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.adapter.viewHolder.HistoryPurchaseHolder
import com.iqbalproject.pj_pos.model.Purchase

class HistoryPurchaseAdapter(private val historyPurchase: List<Purchase>) : RecyclerView.Adapter<HistoryPurchaseHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryPurchaseHolder {
        return HistoryPurchaseHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_history, parent, false)
        )
    }

    override fun getItemCount(): Int = historyPurchase.size

    override fun onBindViewHolder(holder: HistoryPurchaseHolder, position: Int) {
        holder.bindView(historyPurchase[position])
    }
}