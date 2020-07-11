package com.iqbalproject.pj_pos.ui

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.adapter.SpinnerCustAdapter
import com.iqbalproject.pj_pos.adapter.StockSalesAdapter
import com.iqbalproject.pj_pos.model.StockDetail
import com.iqbalproject.pj_pos.ui.viewModel.CustomerViewModel
import com.iqbalproject.pj_pos.ui.viewModel.ProductViewModel
import com.iqbalproject.pj_pos.utils.Constants
import com.iqbalproject.pj_pos.utils.Tools
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.activity_sales.*
import kotlinx.android.synthetic.main.form_customer.view.*
import org.jetbrains.anko.startActivity

class SalesActivity : AppCompatActivity() {

    private lateinit var viewModelStock: ProductViewModel
    private lateinit var viewModelCustomer: CustomerViewModel
    private lateinit var dialog: AlertDialog
    private lateinit var dialogForm: AlertDialog.Builder
    private lateinit var dialogView: View
    private lateinit var inflater: LayoutInflater
    private lateinit var spinnerCustomer: Spinner
    private lateinit var customerAddress: TextView
    private lateinit var customerTelp: TextView
    private lateinit var ivCloseDialog: ImageView
    private lateinit var etDiscount: EditText
    var saleConfirm: MutableList<StockDetail> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.menu_1)

        tvTransDate.text = Tools.getCurrentDate()

        viewModelStock = ViewModelProviders.of(this).get(ProductViewModel::class.java)

        viewModelStock.loadData().observe(this, Observer {
            when (it.status) {
                false -> {
                    rvItemsSale.visibility = View.GONE
                    tvNull.visibility = View.VISIBLE
                }
                else -> {
                    rvItemsSale.visibility = View.VISIBLE
                    tvNull.visibility = View.GONE

                    it.result?.let { stockList ->
                        rvItemsSale.adapter = StockSalesAdapter(stockList)
                    }
                }
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_history, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.btn_history -> startActivity<HistoryActivity>("tag_history" to Constants.SALES)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun dialog() {
        //declarations
        dialogForm = AlertDialog.Builder(this)
        inflater = layoutInflater
        dialogView = inflater.inflate(R.layout.form_customer, null)
        spinnerCustomer = dialogView.spinnerCustomer
        customerAddress = dialogView.tvCustAddress
        customerTelp = dialogView.tvCustTelp
        ivCloseDialog = dialogView.IvCloseDialog
        etDiscount = dialogView.etDiscount

        //get customers
        viewModelCustomer = ViewModelProviders.of(this).get(CustomerViewModel::class.java)

        viewModelCustomer.loadData().observe(this, Observer {
            spinnerCustomer.adapter = it.result?.let {
                SpinnerCustAdapter(this.applicationContext, it)
            }

            spinnerCustomer.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    customerAddress.text = it.result?.get(position)?.cust_address
                    customerTelp.text = it.result?.get(position)?.cust_telp

                    saleConfirm.forEach { confirm ->
                        confirm.id_dummy = it.result?.get(position)?.cust_id
                        confirm.name_dummy = it.result?.get(position)?.cust_name
                        confirm.address_dummy = it.result?.get(position)?.cust_address
                        confirm.telp_dummy = it.result?.get(position)?.cust_telp
                    }
                }
            }
        })

        //show dialogform
        dialogForm.setView(dialogView)
        dialogForm.setCancelable(true)
        dialogForm.setPositiveButton("Process", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                startActivity<SalesConfirmationActivity>(
                    "saleConfirm" to saleConfirm,
                    "discount" to etDiscount.text.toString()
                )
            }
        })
        dialogForm.setNeutralButton("Add Customer", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                startActivity<EditActivity>(
                    "code" to "addCust"
                )
            }
        })

        dialog = dialogForm.show()
        ivCloseDialog.setOnClickListener {
            dialog.dismiss()
        }
    }
}
