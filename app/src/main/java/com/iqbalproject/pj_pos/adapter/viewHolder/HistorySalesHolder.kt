package com.iqbalproject.pj_pos.adapter.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.iqbalproject.pj_pos.model.Sales
import com.iqbalproject.pj_pos.ui.HistoryDetailActivity
import com.iqbalproject.pj_pos.utils.Constants
import kotlinx.android.synthetic.main.item_history.view.*
import org.jetbrains.anko.startActivity

class HistorySalesHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindView(sales: Sales) = with(itemView) {
        tvItemListHistory1.text = sales.cust_name
        tvItemListHistory3.text = sales.sale_id
        tvItemListHistory4.text = sales.sale_date
        tvItemListHistory2.text = sales.status
        tvItemListHistory5.text = sales.to_be_paid

        setOnClickListener {
            context.startActivity<HistoryDetailActivity>(
                "tag_history" to Constants.SALES,
                "sales_header" to sales
            )
        }
    }
}