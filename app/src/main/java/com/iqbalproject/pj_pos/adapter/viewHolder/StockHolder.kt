package com.iqbalproject.pj_pos.adapter.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.iqbalproject.pj_pos.model.StockDetail
import kotlinx.android.synthetic.main.items_sale.view.*

class StockHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindView(stocks: StockDetail){
        itemView.tvItemName.text = stocks.item_name
        itemView.tvItemPrice.text = stocks.selling_price.toString()
    }
}