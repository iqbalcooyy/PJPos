package com.iqbalproject.pj_pos.adapter.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.iqbalproject.pj_pos.model.DetailAccReceivable
import kotlinx.android.synthetic.main.item_piutang_detail.view.*

class DetailArViewHolder(view: View): RecyclerView.ViewHolder(view) {

    fun bindView(arDetail: DetailAccReceivable) {
        itemView.tvArDateDetail.text = arDetail.ar_paid_date
        itemView.tvArPaidDetail.text = "Rp ${arDetail.ar_paid}"
        itemView.tvArNotesDetail.text = "Notes: ${arDetail.notes}"
    }
}