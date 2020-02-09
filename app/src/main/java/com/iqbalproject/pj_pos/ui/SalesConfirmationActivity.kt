package com.iqbalproject.pj_pos.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.adapter.SalesConfirmationAdapter
import com.iqbalproject.pj_pos.model.StockDetail
import com.iqbalproject.pj_pos.ui.viewModel.SalesConfirmViewModel
import com.iqbalproject.pj_pos.utils.Tools
import kotlinx.android.synthetic.main.activity_sales_confirmation.*

class SalesConfirmationActivity : AppCompatActivity() {

    private val sales: MutableList<StockDetail> = mutableListOf()
    private lateinit var viewModel: SalesConfirmViewModel
    private var itemId: MutableList<String> = mutableListOf()
    private var saleQty: MutableList<Int> = mutableListOf()
    private var total: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales_confirmation)

        viewModel = ViewModelProviders.of(this).get(SalesConfirmViewModel::class.java)
        tvTransDateConfirm.text = Tools.getCurrentDate()

        initData()
        btnPay.setOnClickListener {
            progressConfirmSale.visibility = View.VISIBLE
            viewModel.loadData(sales.last().cust_id.toString(), itemId, 0, total, saleQty)

            viewModel.getData().observe(this, Observer { saleRes ->
                viewModel.getStatus().observe(this, Observer { status ->
                    when (status) {
                        true -> {
                            progressConfirmSale.visibility = View.GONE
                            Tools.alertSuccess(this, "Success", saleRes.message.toString())
                            tvCodeTrxSale.text = saleRes.id
                            tvTrxSaleStat.text = "Paid"
                            btnPay.visibility = View.GONE
                        }
                        else -> {
                            progressConfirmSale.visibility = View.GONE
                            Tools.alertFailed(this, "Failed", saleRes.message.toString())
                        }
                    }
                })
            })
        }
    }

    private fun initData() {
        sales.addAll(intent.getParcelableArrayListExtra("saleConfirm"))
        total = 0

        for (i in sales.indices) {
            total += sales.get(i).pay

            itemId.add(i, sales[i].item_id)
            saleQty.add(i, sales[i].sale_qty)
        }

        tvCustNameConf.text = sales.last().cust_name
        tvCustAddressConf.text = sales.last().cust_address
        tvCustTelpConf.text = sales.last().cust_telp

        tvTotalPayConfirm.text = Tools.convertRupiahsFormat(total.toDouble())
        tvSubTot.text = Tools.convertRupiahsFormat(total.toDouble())
        tvTotPayConf.text = Tools.convertRupiahsFormat(total.toDouble())

        rvItemsSaleConf.adapter = SalesConfirmationAdapter(sales)
    }
}