package com.iqbalproject.pj_pos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.adapter.viewHolder.PurchaseConfirmationHolder
import com.iqbalproject.pj_pos.model.StockDetail

class PurchaseConfirmationAdapter(private val items: List<StockDetail>) :
    RecyclerView.Adapter<PurchaseConfirmationHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseConfirmationHolder {
        return PurchaseConfirmationHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_purchase_confirm, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PurchaseConfirmationHolder, position: Int) {
        holder.bindView(items[position], items.first().name_dummy)
    }
}