package com.iqbalproject.pj_pos.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.adapter.SpinnerAdapter
import com.iqbalproject.pj_pos.adapter.StockAdapter
import com.iqbalproject.pj_pos.model.StockDetail
import com.iqbalproject.pj_pos.ui.viewModel.CustomerViewModel
import com.iqbalproject.pj_pos.ui.viewModel.StocksViewModel
import com.iqbalproject.pj_pos.utils.Tools
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.activity_sales.*
import kotlinx.android.synthetic.main.form_customer.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SalesActivity : AppCompatActivity() {

    private lateinit var viewModelStock: StocksViewModel
    private lateinit var viewModelCustomer: CustomerViewModel
    private lateinit var dialogForm: AlertDialog.Builder
    private lateinit var dialogView: View
    private lateinit var inflater: LayoutInflater
    private lateinit var spinnerCustomer: Spinner
    private lateinit var customerAddress: TextView
    private lateinit var customerTelp: TextView
    var saleConfirm: MutableList<StockDetail> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales)

        tvTransDate.text = Tools.getCurrentDate()

        viewModelStock = ViewModelProviders.of(this).get(StocksViewModel::class.java)

        viewModelStock.getStatus().observe(this, Observer {
            if (it == false) {
                rvItemsSale.visibility = View.GONE
                tvNull.visibility = View.VISIBLE
            } else {
                rvItemsSale.visibility = View.VISIBLE
                tvNull.visibility = View.GONE
            }
        })

        viewModelStock.getData().observe(this, Observer {
            it?.result?.let {
                rvItemsSale.adapter = StockAdapter(it)
            }
        })

        btnProcess.setOnClickListener {
            if (saleConfirm.isEmpty())
                FancyToast.makeText(
                    this,
                    "Please, choose your item!",
                    FancyToast.LENGTH_SHORT,
                    FancyToast.WARNING,
                    true
                ).show()
            else
                dialog()
        }
    }

    private fun dialog() {
        //declarations
        dialogForm = AlertDialog.Builder(this)
        inflater = layoutInflater
        dialogView = inflater.inflate(R.layout.form_customer, null)
        spinnerCustomer = dialogView.spinnerCustomer
        customerAddress = dialogView.tvCustAddress
        customerTelp = dialogView.tvCustTelp

        //get customers
        viewModelCustomer = ViewModelProviders.of(this).get(CustomerViewModel::class.java)
        viewModelCustomer.getStatus().observe(this, Observer {
            when (it) {
                false -> toast("Customers not found!").show()
            }
        })

        viewModelCustomer.getData().observe(this, Observer {
            spinnerCustomer.adapter = it.result?.let {
                SpinnerAdapter(this.applicationContext, it)
            }

            spinnerCustomer.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    customerAddress.text = it.result?.get(position)?.cust_address
                    customerTelp.text = it.result?.get(position)?.cust_telp

                    saleConfirm.forEach { confirm ->
                        confirm.cust_id = it.result?.get(position)?.cust_id
                        confirm.cust_name = it.result?.get(position)?.cust_name
                        confirm.cust_address = it.result?.get(position)?.cust_address
                        confirm.cust_telp = it.result?.get(position)?.cust_telp
                    }
                }
            }
        })

        //show dialogform
        dialogForm.setView(dialogView)
        dialogForm.setTitle("Detail Customer")
        dialogForm.setCancelable(false)
        dialogForm.setPositiveButton("Process", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                startActivity<SalesConfirmationActivity>("saleConfirm" to saleConfirm)
            }
        })
        dialogForm.setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, p1: Int) {
                dialog?.dismiss()
            }
        })

        dialogForm.show()
    }
}
