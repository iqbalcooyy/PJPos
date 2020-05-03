package com.iqbalproject.pj_pos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.adapter.viewHolder.SupplierViewHolder
import com.iqbalproject.pj_pos.model.SupplierResult

class SupplierAdapter(private val supplier: List<SupplierResult>): RecyclerView.Adapter<SupplierViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupplierViewHolder {
        return SupplierViewHolder(
            parent.context,
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_cust_supp, parent, false)
        )
    }

    override fun getItemCount(): Int = supplier.size

    override fun onBindViewHolder(holder: SupplierViewHolder, position: Int) {
        holder.bindView(supplier[position])
    }
}