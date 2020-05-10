package com.iqbalproject.pj_pos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.adapter.viewHolder.DetailArViewHolder
import com.iqbalproject.pj_pos.model.DetailAccReceivable

class DetailArAdapter(private val arDetail: List<DetailAccReceivable>): RecyclerView.Adapter<DetailArViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailArViewHolder {
        return DetailArViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_piutang_detail, parent, false)
        )
    }

    override fun getItemCount(): Int = arDetail.size

    override fun onBindViewHolder(holder: DetailArViewHolder, position: Int) {
        holder.bindView(arDetail[position])
    }
}