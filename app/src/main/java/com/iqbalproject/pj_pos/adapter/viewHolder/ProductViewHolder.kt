package com.iqbalproject.pj_pos.adapter.viewHolder

import android.content.Context
import android.content.DialogInterface
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.model.StockDetail
import com.iqbalproject.pj_pos.ui.AddProductActivity
import com.iqbalproject.pj_pos.ui.ProductActivity
import kotlinx.android.synthetic.main.item_product.view.*
import org.jetbrains.anko.startActivity

class ProductViewHolder(private val context: Context, view: View) : RecyclerView.ViewHolder(view) {

    private lateinit var dialogForm: AlertDialog.Builder

    fun bindView(product: StockDetail) {
        itemView.tvProductName.text = product.item_name
        itemView.tvProductId.text = product.item_id
        itemView.tvProdSellPrice.text =
            "${context.getString(R.string.sell_price)} : Rp${product.selling_price}"
        itemView.tvProdBuyPrice.text =
            " | ${context.getString(R.string.purchase_price)} : Rp${product.purchase_price}"
        itemView.tvProductQty.text =
            "${context.getString(R.string.curr_stock)} : ${product.item_qty}"

        itemView.setOnClickListener {
            dialog(product)
        }
    }

    private fun dialog(product: StockDetail) {
        dialogForm = AlertDialog.Builder(context)
        dialogForm.setTitle("Option Button")
        dialogForm.setItems(
            arrayOf<CharSequence>(
                "Edit Data",
                "Hapus Data"
            )
        ) { dialog, which ->
            when (which) {
                0 -> {
                    context.startActivity<AddProductActivity>(
                        "code" to "editProduct",
                        "product" to product
                    )
                }
                1 -> {
                    (context as ProductActivity).deleteDialog(product.item_id)
                    dialog.dismiss()
                }
            }
        }
        dialogForm.setCancelable(true)
        dialogForm.setNegativeButton("Batal", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, p1: Int) {
                dialog?.dismiss()
            }
        })

        dialogForm.show()
    }
}