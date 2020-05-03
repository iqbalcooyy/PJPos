package com.iqbalproject.pj_pos.adapter.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.iqbalproject.pj_pos.model.StockDetail
import kotlinx.android.synthetic.main.item_purchase_confirm.view.*

class PurchaseConfirmationHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindView(item: StockDetail, supplierName: String?) {
        itemView.tvItemPurchConf.text = item.item_name
        itemView.tvSuppPurConf.text = "($supplierName)"
        itemView.tvQtyPurcConf.text = "Quantity: ${item.qty_dummy.toString()} ${item.uom}"
        itemView.tvAmountPurcConf.text = " | Amount: " + item.amount_dummy.toString()
    }
}