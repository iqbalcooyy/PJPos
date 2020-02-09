package com.iqbalproject.pj_pos.adapter.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.iqbalproject.pj_pos.model.StockDetail
import com.iqbalproject.pj_pos.utils.Tools
import kotlinx.android.synthetic.main.items_confirmation.view.*

class SalesConfirmationHolder(view: View): RecyclerView.ViewHolder(view) {

    private lateinit var salePrice: String
    private lateinit var pay: String

    fun bindView(item: StockDetail) {
        salePrice = Tools.convertRupiahsFormat(item.selling_price.toDouble())
        pay = Tools.convertRupiahsFormat(item.pay.toDouble())

        itemView.tvItemNameConf.text = item.item_name
        itemView.tvItemQtyPrice.text = item.sale_qty.toString() + " x " + salePrice
        itemView.tvItemPriceConf.text = pay
    }
}