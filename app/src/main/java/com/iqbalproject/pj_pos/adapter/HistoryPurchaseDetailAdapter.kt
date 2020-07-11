package com.iqbalproject.pj_pos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.model.HistoryPurchaseResult

class HistoryPurchaseDetailAdapter(private val purchase_detail: List<HistoryPurchaseResult>) : RecyclerView.Adapter<HistoryPurchaseDetailHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryPurchaseDetailHolder {
        return HistoryPurchaseDetailHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_history_detail, parent, false)
        )
    }

    override fun getItemCount(): Int = purchase_detail.size

    override fun onBindViewHolder(holder: HistoryPurchaseDetailHolder, position: Int) {
        holder.bindView(purchase_detail[position])
    }
}