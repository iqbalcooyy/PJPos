package com.iqbalproject.pj_pos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.adapter.viewHolder.CustomerViewHolder
import com.iqbalproject.pj_pos.model.CustomerResult

class CustomerAdapter(private val customer: List<CustomerResult>): RecyclerView.Adapter<CustomerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        return CustomerViewHolder(
            parent.context,
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_cust_supp, parent, false)
        )
    }

    override fun getItemCount(): Int = customer.size

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        holder.bindView(customer[position])
    }
}