package com.iqbalproject.pj_pos.adapter.viewHolder

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.model.CustomerResult
import com.iqbalproject.pj_pos.ui.EditActivity
import kotlinx.android.synthetic.main.item_cust_supp.view.*
import kotlinx.android.synthetic.main.option_button.view.*
import org.jetbrains.anko.layoutInflater
import org.jetbrains.anko.startActivity

class CustomerViewHolder(private val context: Context, view: View) : RecyclerView.ViewHolder(view) {

    private lateinit var dialogForm: AlertDialog.Builder
    private lateinit var dialogView: View
    private lateinit var inflater: LayoutInflater
    private lateinit var btnEdit: Button
    private lateinit var btnDelete: Button

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
        inflater = context.layoutInflater
        dialogView = inflater.inflate(R.layout.option_button, null)
        btnEdit = dialogView.btnEdit
        btnDelete = dialogView.btnDelete

        //show dialogform
        dialogForm.setView(dialogView)
        dialogForm.setTitle("Option Button")
        dialogForm.setCancelable(true)
        dialogForm.setPositiveButton("Batal", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, p1: Int) {
                dialog?.dismiss()
            }
        })

        btnEdit.setOnClickListener {
            context.startActivity<EditActivity>(
                "code" to "cust",
                "customer" to customer
            )
        }

        dialogForm.show()
    }
}