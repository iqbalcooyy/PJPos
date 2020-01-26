package com.iqbalproject.pj_pos.adapter.viewHolder

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.iqbalproject.pj_pos.model.StockDetail
import com.iqbalproject.pj_pos.ui.SalesActivity
import com.iqbalproject.pj_pos.utils.Terbilang
import kotlinx.android.synthetic.main.activity_sales.*
import kotlinx.android.synthetic.main.items_sale.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.text.NumberFormat
import java.util.*

class StockHolder(private val context: Context, view: View) : RecyclerView.ViewHolder(view) {

    private var qty: Int = 0
    private var pricePerItem: Int = 0
    private var priceTotal: Int = 0
    private lateinit var tvTotalPay: TextView
    private lateinit var tvTotalTerbilang: TextView

    fun bindView(stocks: StockDetail, stocksList: List<StockDetail>) {
        itemView.tvItemName.text = stocks.item_name
        itemView.tvItemPrice.text = "Rp " + stocks.selling_price.toString()
        tvTotalPay = (context as SalesActivity).tvTotalPay
        tvTotalTerbilang = (context as SalesActivity).tvTotalTerbilang

        val localeID: Locale = Locale("in", "ID")
        val rupiahsFormat: NumberFormat = NumberFormat.getCurrencyInstance(localeID)

        itemView.btnAddItem.onClick {
            qty += 1
            tvTotalPay.text = rupiahsFormat.format(totalPay(stocks, stocksList).toDouble())
            tvTotalTerbilang.text = Terbilang.konversi(totalPay(stocks, stocksList))
        }

        itemView.btnRemoveItem.onClick {
            if (qty > 0) {
                qty -= 1
                tvTotalPay.text = rupiahsFormat.format(totalPay(stocks, stocksList).toDouble())
                tvTotalTerbilang.text = Terbilang.konversi(totalPay(stocks, stocksList))
            } else
                Toast.makeText(context, "Out of minimum quantity", Toast.LENGTH_SHORT).show()

            itemView.tvPcs.text = qty.toString()
        }
    }

    private fun totalPay(stocks: StockDetail, stocks1: List<StockDetail>): Int {
        priceTotal = 0
        pricePerItem = qty * stocks.selling_price
        itemView.tvPcs.text = qty.toString()
        stocks.temporary = pricePerItem

        for (i in stocks1.indices) {
            priceTotal += stocks1.get(i).temporary
        }

        return priceTotal
    }
}