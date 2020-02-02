package com.iqbalproject.pj_pos.adapter.viewHolder

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.iqbalproject.pj_pos.model.StockDetail
import com.iqbalproject.pj_pos.ui.SalesActivity
import com.iqbalproject.pj_pos.utils.Tools
import kotlinx.android.synthetic.main.activity_sales.*
import kotlinx.android.synthetic.main.items_sale.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class StockHolder(private val context: Context, view: View) : RecyclerView.ViewHolder(view) {

    private var qty: Int = 0
    private var priceTotal: Int = 0
    private lateinit var tvTotalPay: TextView
    private lateinit var tvTotalTerbilang: TextView
    private var items: MutableList<StockDetail> = mutableListOf()

    fun bindView(stocks: StockDetail, stocksList: List<StockDetail>) {
        itemView.tvItemName.text = stocks.item_name
        itemView.tvItemPrice.text = Tools.convertRupiahsFormat(stocks.selling_price.toDouble())
        tvTotalPay = (context as SalesActivity).tvTotalPay
        tvTotalTerbilang = context.tvTotalTerbilang

        itemView.btnAddItem.onClick {
            qty += 1
            setValue(stocks, stocksList)
            tvTotalPay.text = Tools.convertRupiahsFormat(priceTotal.toDouble())
            tvTotalTerbilang.text = Tools.terbilang(priceTotal)
            itemView.tvPcs.text = qty.toString()
        }

        itemView.btnRemoveItem.onClick {
            if (qty > 0) {
                qty -= 1
                setValue(stocks, stocksList)
                tvTotalPay.text = Tools.convertRupiahsFormat(priceTotal.toDouble())
                tvTotalTerbilang.text = Tools.terbilang(priceTotal)
            } else
                Toast.makeText(context, "Out of minimum quantity", Toast.LENGTH_SHORT).show()

            itemView.tvPcs.text = qty.toString()
        }
    }

    private fun setValue(stocks: StockDetail, stocksList: List<StockDetail>) {
        items.clear()
        priceTotal = 0
        stocks.sale_qty = qty
        stocks.pay = qty * stocks.selling_price

        for (i in stocksList.indices) {
            priceTotal += stocksList.get(i).pay
        }

        for (elemen in stocksList) {
            if (elemen.pay > 0)
                items.add(elemen)
        }

        (context as SalesActivity).saleConfirm.clear()
        context.saleConfirm.addAll(items)
    }
}