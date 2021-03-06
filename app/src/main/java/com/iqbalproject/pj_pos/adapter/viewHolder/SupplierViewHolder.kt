package com.iqbalproject.pj_pos.adapter.viewHolder

import android.content.Context
import android.content.DialogInterface
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.model.SupplierResult
import com.iqbalproject.pj_pos.ui.EditActivity
import com.iqbalproject.pj_pos.ui.SupplierActivity
import kotlinx.android.synthetic.main.item_cust_supp.view.*
import org.jetbrains.anko.startActivity


class SupplierViewHolder(private val context: Context, view: View) : RecyclerView.ViewHolder(view) {

    private lateinit var dialogForm: AlertDialog.Builder

    fun bindView(supplier: SupplierResult) {
        itemView.tvCustSuppName.text = supplier.supplier_name
        itemView.tvCustSuppId.text = supplier.supplier_id
        itemView.tvCustSuppAddress.text = supplier.supplier_address
        itemView.tvCustSuppTelp.text = supplier.supplier_telp

        itemView.setOnClickListener {
            dialog(supplier)
        }
    }

    private fun dialog(supplier: SupplierResult) {
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
                    context.startActivity<EditActivity>(
                        "code" to "supp",
                        "supplier" to supplier
                    )
                }
                1 -> {
                    (context as SupplierActivity).deleteDialog(supplier.supplier_id.toString())
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