package com.iqbalproject.pj_pos.adapter.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.iqbalproject.pj_pos.model.HistorySalesResult
import kotlinx.android.synthetic.main.item_history_detail.view.*

class HistorySalesDetailHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindView(sales_detail: HistorySalesResult) = with(itemView) {
        tvItemIdHistory.text = sales_detail.item_id
        tvItemNameHistory.text = sales_detail.item_name
        tvSaleQty.text = "${sales_detail.sale_qty} ${sales_detail.uom}"
    }
}