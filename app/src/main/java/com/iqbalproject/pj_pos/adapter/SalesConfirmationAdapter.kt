package com.iqbalproject.pj_pos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.adapter.viewHolder.SalesConfirmationHolder
import com.iqbalproject.pj_pos.model.StockDetail

class SalesConfirmationAdapter(private val items: List<StockDetail>): RecyclerView.Adapter<SalesConfirmationHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesConfirmationHolder {
        return SalesConfirmationHolder(LayoutInflater.from(parent.context).inflate(R.layout.items_confirmation, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SalesConfirmationHolder, position: Int) {
        holder.bindView(items[position])
    }
}