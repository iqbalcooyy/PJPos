package com.iqbalproject.pj_pos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.adapter.viewHolder.AccReceivableVH
import com.iqbalproject.pj_pos.model.AccReceivable

class AccReceivableAdapter(private val receivable: List<AccReceivable>): RecyclerView.Adapter<AccReceivableVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccReceivableVH {
        return AccReceivableVH(
            parent.context,
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_acc_receivable, parent, false)
        )
    }

    override fun getItemCount(): Int = receivable.size

    override fun onBindViewHolder(holder: AccReceivableVH, position: Int) {
        holder.bindView(receivable[position])
    }
}