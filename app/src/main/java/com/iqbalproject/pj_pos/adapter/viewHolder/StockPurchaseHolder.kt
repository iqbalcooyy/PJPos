package com.iqbalproject.pj_pos.adapter.viewHolder

import android.content.Context
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.iqbalproject.pj_pos.model.StockDetail
import com.iqbalproject.pj_pos.ui.PurchaseActivity
import kotlinx.android.synthetic.main.item_purchase.view.*
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange

class StockPurchaseHolder(private val context: Context, view: View) :
    RecyclerView.ViewHolder(view) {

    fun bindView(stock: StockDetail) {
        itemView.tvItemNamePurc.text = stock.item_name

        itemView.cbItemPurchase.onCheckedChange { buttonView, isChecked ->
            if (isChecked) {
                itemView.etBanyaknya.visibility = View.VISIBLE
                itemView.etTotalHarga.visibility = View.VISIBLE
                itemView.tvUom.visibility = View.VISIBLE
                itemView.tvUom.text = stock.uom

                itemView.etBanyaknya.addTextChangedListener {
                    itemView.etBanyaknya.let { etQty ->
                        if (!etQty.text.isNullOrEmpty()) {
                            if (etQty.text.toString() != "0") {
                                stock.qty_dummy = etQty.text.toString().toInt()
                                if (stock.amount_dummy != 0) {
                                    (context as PurchaseActivity).purchaseConfirm.remove(stock)
                                    context.purchaseConfirm.add(stock)
                                } else {
                                    (context as PurchaseActivity).purchaseConfirm.remove(stock)
                                }
                            } else {
                                (context as PurchaseActivity).purchaseConfirm.remove(stock)
                            }
                        } else {
                            stock.qty_dummy = 0
                            (context as PurchaseActivity).purchaseConfirm.remove(stock)
                        }
                    }
                }

                itemView.etTotalHarga.addTextChangedListener {
                    itemView.etTotalHarga.let { etAmount ->
                        if (!etAmount.text.isNullOrEmpty()) {
                            if (etAmount.text.toString() != "0") {
                                stock.amount_dummy = etAmount.text.toString().toInt()
                                if (stock.qty_dummy != 0) {
                                    (context as PurchaseActivity).purchaseConfirm.remove(stock)
                                    context.purchaseConfirm.add(stock)
                                } else {
                                    (context as PurchaseActivity).purchaseConfirm.remove(stock)
                                }
                            } else {
                                (context as PurchaseActivity).purchaseConfirm.remove(stock)
                            }
                        } else {
                            stock.amount_dummy = 0
                            (context as PurchaseActivity).purchaseConfirm.remove(stock)
                        }
                    }
                }
            } else {
                itemView.etBanyaknya.visibility = View.GONE
                itemView.etTotalHarga.visibility = View.GONE
                itemView.tvUom.visibility = View.GONE
                itemView.etBanyaknya.text = null
                itemView.etTotalHarga.text = null

                (context as PurchaseActivity).purchaseConfirm.remove(stock)
            }
        }
    }

}