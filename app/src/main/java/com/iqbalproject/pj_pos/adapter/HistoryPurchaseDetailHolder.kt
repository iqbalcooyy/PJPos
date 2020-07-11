package com.iqbalproject.pj_pos.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.iqbalproject.pj_pos.model.HistoryPurchaseResult
import kotlinx.android.synthetic.main.item_history_detail.view.*

class HistoryPurchaseDetailHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindView(purchase_detail: HistoryPurchaseResult) = with(itemView) {
        tvItemIdHistory.text = purchase_detail.item_id
        tvItemNameHistory.text = purchase_detail.item_name
        tvSaleQty.text = "${purchase_detail.purchase_qty} ${purchase_detail.purchase_uom}"
    }
}