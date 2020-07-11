package com.iqbalproject.pj_pos.adapter.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.iqbalproject.pj_pos.model.Purchase
import com.iqbalproject.pj_pos.ui.HistoryDetailActivity
import com.iqbalproject.pj_pos.utils.Constants
import kotlinx.android.synthetic.main.item_history.view.*
import org.jetbrains.anko.startActivity

class HistoryPurchaseHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindView(purchase: Purchase) = with(itemView) {
        tvItemListHistory1.text = "${purchase.supplier_name} (${purchase.supplier_id})"
        tvItemListHistory3.text = purchase.purchase_id
        tvItemListHistory4.text = purchase.purchase_date
        tvItemListHistory2.text = "Total:"
        tvItemListHistory5.text = purchase.purchase_price_total

        setOnClickListener {
            context.startActivity<HistoryDetailActivity>(
                "tag_history" to Constants.PURCHASE,
                "purchase_header" to purchase)
        }
    }
}