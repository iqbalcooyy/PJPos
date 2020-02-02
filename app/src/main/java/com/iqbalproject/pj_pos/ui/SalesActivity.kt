package com.iqbalproject.pj_pos.ui

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.adapter.StockAdapter
import com.iqbalproject.pj_pos.model.StockDetail
import com.iqbalproject.pj_pos.ui.viewModel.StocksViewModel
import com.iqbalproject.pj_pos.utils.Tools
import kotlinx.android.synthetic.main.activity_sales.*
import kotlinx.android.synthetic.main.form_customer.view.*
import org.jetbrains.anko.startActivity

class SalesActivity : AppCompatActivity() {

    private lateinit var viewModel: StocksViewModel
    private lateinit var dialogForm: AlertDialog.Builder
    private lateinit var dialogView: View
    private lateinit var inflater: LayoutInflater
    private lateinit var customerName: Editable
    private lateinit var customerAddress: Editable
    private lateinit var customerTelp: Editable
    var saleConfirm: MutableList<StockDetail> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales)

        tvTransDate.text = Tools.getCurrentDate()

        viewModel = ViewModelProviders.of(this).get(StocksViewModel::class.java)

        viewModel.getStatus().observe(this, Observer {
            if (it == false) {
                rvItemsSale.visibility = View.GONE
                tvNull.visibility = View.VISIBLE
            } else {
                rvItemsSale.visibility = View.VISIBLE
                tvNull.visibility = View.GONE
            }
        })

        viewModel.getData().observe(this, Observer {
            it?.result?.let {
                showData(it)
            }
        })

        btnProcess.setOnClickListener {
            dialog()
        }
    }

    private fun showData(data: List<StockDetail>) {
        rvItemsSale.adapter = StockAdapter(data)
    }

    private fun dialog() {
        dialogForm = AlertDialog.Builder(this)
        inflater = layoutInflater
        dialogView = inflater.inflate(R.layout.form_customer, null)
        customerName = dialogView.etCustomerName.editableText
        customerAddress = dialogView.etCustAddress.editableText
        customerTelp = dialogView.etCustTelp.editableText

        dialogForm.setView(dialogView)
        dialogForm.setTitle("Detail Customer")
        dialogForm.setCancelable(false)
        dialogForm.setPositiveButton("Process", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                saleConfirm.forEach {
                    it.cust_name = customerName.toString()
                    it.cust_address = customerAddress.toString()
                    it.cust_telp = customerTelp.toString()
                }

                startActivity<SalesConfirmationActivity>("saleConfirm" to saleConfirm)
            }
        })
        dialogForm.setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                p0?.dismiss()
            }
        })

        dialogForm.show()
    }
}
