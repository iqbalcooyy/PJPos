package com.iqbalproject.pj_pos.adapter.viewHolder

import android.content.Context
import android.content.DialogInterface
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.iqbalproject.pj_pos.model.AccReceivable
import com.iqbalproject.pj_pos.ui.AccReceivableActivity
import com.iqbalproject.pj_pos.ui.DetailAccReceiveActivity
import com.iqbalproject.pj_pos.utils.Tools
import kotlinx.android.synthetic.main.item_acc_receivable.view.*
import org.jetbrains.anko.startActivity

class AccReceivableVH(private val context: Context, view: View) : RecyclerView.ViewHolder(view) {

    private lateinit var dialogForm: AlertDialog.Builder

    fun bindView(receivable: AccReceivable) {
        itemView.tvCustNameAr.text = "${receivable.cust_name} (${receivable.cust_id})"
        itemView.tvStatusAr.text = receivable.ar_status
        itemView.tvArId.text = "AR ID : ${receivable.ar_id}"
        itemView.tvSaleIdAr.text = " | Sale ID : ${receivable.sale_id}"
        itemView.tvArDate.text = receivable.ar_date
        itemView.tvArTotal.text = receivable.remaining_payment

        itemView.setOnClickListener {
            dialog(receivable)
        }
    }

    private fun dialog(receivable: AccReceivable) {
        dialogForm = AlertDialog.Builder(context)
        dialogForm.setTitle("Option Button")
        dialogForm.setItems(
            arrayOf<CharSequence>(
                "Penyelesaian Pembayaran",
                "Riwayat Pembayaran"
            )
        ) { dialog, which ->
            when (which) {
                0 -> {
                    if (receivable.remaining_payment == "0") {
                        Tools.alertInfo(context, "Perhatian", "Piutang telah Lunas!")
                    } else {
                        (context as AccReceivableActivity).settlement(receivable.ar_id.toString(), receivable.remaining_payment.toString())
                    }

                    dialog.dismiss()
                }
                1 -> {
                    context.startActivity<DetailAccReceiveActivity>("receivable" to receivable)
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