package com.iqbalproject.pj_pos.adapter.viewHolder

import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.iqbalproject.pj_pos.model.CustomerResult
import com.iqbalproject.pj_pos.ui.CustomerActivity
import com.iqbalproject.pj_pos.ui.EditActivity
import kotlinx.android.synthetic.main.item_cust_supp.view.*
import org.jetbrains.anko.startActivity

class CustomerViewHolder(private val context: Context, view: View) : RecyclerView.ViewHolder(view) {

    private lateinit var dialogForm: AlertDialog.Builder

    fun bindView(customer: CustomerResult) {
        itemView.tvCustSuppName.text = customer.cust_name
        itemView.tvCustSuppId.text = customer.cust_id
        itemView.tvCustSuppAddress.text = customer.cust_address
        itemView.tvCustSuppTelp.text = customer.cust_telp

        itemView.setOnClickListener {
            dialog(customer)
        }
    }

    private fun dialog(customer: CustomerResult) {
        dialogForm = AlertDialog.Builder(context)
        dialogForm.setTitle("Option Button")
        dialogForm.setItems(arrayOf<CharSequence>(
            "Edit Data",
            "Hapus Data"
        )
        ) { dialog, which ->
            when (which) {
                0 -> {
                    context.startActivity<EditActivity>(
                        "code" to "cust",
                        "customer" to customer
                    )
                }
                1 -> {
                    (context as CustomerActivity).deleteDialog(customer.cust_id.toString())
                    dialog.dismiss()
                }
            }
        }
        dialogForm.setCancelable(true)
        dialogForm.setPositiveButton("Batal", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, p1: Int) {
                dialog?.dismiss()
            }
        })

        dialogForm.show()
    }
}