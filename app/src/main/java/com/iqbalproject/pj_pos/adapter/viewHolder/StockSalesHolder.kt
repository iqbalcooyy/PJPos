package com.iqbalproject.pj_pos.adapter.viewHolder

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.model.StockDetail
import com.iqbalproject.pj_pos.ui.SalesActivity
import com.iqbalproject.pj_pos.utils.Tools
import kotlinx.android.synthetic.main.activity_sales.*
import kotlinx.android.synthetic.main.items_sale.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class StockSalesHolder(private val context: Context, view: View) : RecyclerView.ViewHolder(view) {

    private var qty: Int = 0
    private var priceTotal: Int = 0
    private lateinit var tvTotalPay: TextView
    private lateinit var tvTotalTerbilang: TextView
    private var items: MutableList<StockDetail> = mutableListOf()

    fun bindView(stocks: StockDetail, stocksList: List<StockDetail>) {
        itemView.tvItemName.text = stocks.item_name
        itemView.tvItemStock.text = "Stock: ${stocks.item_qty} ${stocks.uom}"
        itemView.tvItemPrice.text =
            "Price: ${Tools.convertRupiahsFormat(stocks.selling_price.toDouble())}/${stocks.uom}"
        tvTotalPay = (context as SalesActivity).tvTotalPay
        tvTotalTerbilang = context.tvTotalTerbilang

        if (stocks.item_qty == 0) {
            itemView.tvItemName.setTextColor(context.resources.getColor(android.R.color.holo_red_light))
            itemView.tvItemStock.setTextColor(context.resources.getColor(android.R.color.holo_red_light))
            itemView.tvItemPrice.setTextColor(context.resources.getColor(android.R.color.holo_red_light))
            itemView.btnAddItem.backgroundTintList =
                context.resources.getColorStateList(android.R.color.darker_gray)
            itemView.btnRemoveItem.backgroundTintList =
                context.resources.getColorStateList(android.R.color.darker_gray)
        } else {
            itemView.tvItemName.setTextColor(context.resources.getColor(android.R.color.black))
            itemView.tvItemStock.setTextColor(context.resources.getColor(R.color.colorGray))
            itemView.tvItemPrice.setTextColor(context.resources.getColor(R.color.colorGray))
            itemView.btnAddItem.backgroundTintList =
                context.resources.getColorStateList(R.color.colorAccent)
            itemView.btnRemoveItem.backgroundTintList =
                context.resources.getColorStateList(R.color.colorAccent)
        }

       /* itemView.btnAddItem.onClick {
            if (stocks.item_qty == 0 || qty == stocks.item_qty) {
                Tools.toastWarning(context, "Out of Stock")
            } else {
                qty += 1
                setValue(stocks, stocksList)
                tvTotalPay.text = Tools.convertRupiahsFormat(priceTotal.toDouble())
                tvTotalTerbilang.text = Tools.terbilang(priceTotal)
                itemView.tvPcs.text = qty.toString()
            }
        }

        itemView.btnRemoveItem.onClick {
            if (qty > 0) {
                qty -= 1
                setValue(stocks, stocksList)
                tvTotalPay.text = Tools.convertRupiahsFormat(priceTotal.toDouble())
                tvTotalTerbilang.text = Tools.terbilang(priceTotal)
            } else
                Tools.toastWarning(context, "Out of minimum quantity")

            itemView.tvPcs.text = qty.toString()
        }*/

        itemView.btnAddItem.onClick {
            if (stocks.item_qty == 0 || qty == stocks.item_qty) {
                Tools.toastWarning(context, "Out of Stock")
            } else {
                var x = 0
                if (itemView.tvPcs.text.trim().toString().isNotEmpty())
                    x = itemView.tvPcs.text.trim().toString().toInt()

                qty = x + 1
                //qty += 1
                setValue(stocks, stocksList)
                tvTotalPay.text = Tools.convertRupiahsFormat(priceTotal.toDouble())
                tvTotalTerbilang.text = Tools.terbilang(priceTotal)
                //itemView.tvPcs.text = qty.toString()
                itemView.tvPcs.setText(qty.toString())
            }
        }

        itemView.btnRemoveItem.onClick {
            if (qty > 0) {
                var x = 0
                if (itemView.tvPcs.text.trim().toString().isNotEmpty())
                    x = itemView.tvPcs.text.trim().toString().toInt()

                qty = x - 1
                //qty -= 1
                setValue(stocks, stocksList)
                tvTotalPay.text = Tools.convertRupiahsFormat(priceTotal.toDouble())
                tvTotalTerbilang.text = Tools.terbilang(priceTotal)
            } else
                Tools.toastWarning(context, "Out of minimum quantity")

            //itemView.tvPcs.text = qty.toString()
            itemView.tvPcs.setText(qty.toString())
        }

        itemView.tvPcs.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()){
                    if (stocks.item_qty == 0 || s.trim().toString().toInt() > stocks.item_qty) {
                        Tools.toastWarning(context, "Out of Stock")
                        qty = 0
                        itemView.tvPcs.text.clear()
                    } else {
                        qty = s.trim().toString().toInt()
                    }
                } else {
                    qty = 0
                    itemView.tvPcs.text.clear()
                }

                setValue(stocks, stocksList)
                tvTotalPay.text = Tools.convertRupiahsFormat(priceTotal.toDouble())
                tvTotalTerbilang.text = Tools.terbilang(priceTotal)
            }
        })
    }

    private fun setValue(stocks: StockDetail, stocksList: List<StockDetail>) {
        items.clear()
        priceTotal = 0
        stocks.qty_dummy = qty
        stocks.amount_dummy = qty * stocks.selling_price

        for (i in stocksList.indices) {
            priceTotal += stocksList[i].amount_dummy
        }

        for (elemen in stocksList) {
            if (elemen.amount_dummy > 0)
                items.add(elemen)
        }

        (context as SalesActivity).saleConfirm.clear()
        context.saleConfirm.addAll(items)
    }
}