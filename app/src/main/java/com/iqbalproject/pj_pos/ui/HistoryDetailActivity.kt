package com.iqbalproject.pj_pos.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.adapter.HistoryPurchaseDetailAdapter
import com.iqbalproject.pj_pos.adapter.HistorySalesDetailAdapter
import com.iqbalproject.pj_pos.model.Purchase
import com.iqbalproject.pj_pos.model.Sales
import com.iqbalproject.pj_pos.ui.viewModel.HistoryViewModel
import com.iqbalproject.pj_pos.utils.Constants
import kotlinx.android.synthetic.main.activity_history_detail.*

class HistoryDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: HistoryViewModel
    private var tagHistory: String? = null
    private var sales_header: Sales? = null
    private var purchase_header: Purchase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProviders.of(this).get(HistoryViewModel::class.java)
        tagHistory = intent.getStringExtra("tag_history")
        sales_header = intent.getParcelableExtra("sales_header")
        purchase_header = intent.getParcelableExtra("purchase_header")

        when (tagHistory) {
            Constants.SALES -> {
                supportActionBar?.title = "Riwayat Transaksi Penjualan"
                setupSales()
            }
            Constants.PURCHASE -> {
                supportActionBar?.title = "Riwayat Transaksi Pembelian"
                setupPurchase()
            }
        }


    }

    private fun setupPurchase() {
        tvHistoryParam1.visibility = View.GONE
        tvHistoryParam6.visibility = View.GONE
        tvHistoryParam7.visibility = View.GONE
        tvHistoryParam2.text = "Tanggal: ${purchase_header?.purchase_date}"
        tvHistoryParam3.text = "Purchase ID: ${purchase_header?.purchase_id}"
        tvHistoryParam4.text = purchase_header?.supplier_name
        tvHistoryParam5.text = "Total: ${purchase_header?.purchase_price_total}"

        purchase_header?.purchase_id?.let { purchaseId ->
            viewModel.loadHistoryPurchaseDetail(purchaseId).observe(this, Observer {
                progressHistoryDetail.visibility = View.GONE

                if (it != null) {
                    rvHistoryDetail.adapter = it.result_history_detail?.let { purchase_detail ->
                        HistoryPurchaseDetailAdapter(purchase_detail)
                    }
                }
            })
        }
    }

    private fun setupSales() {
        tvHistoryParam1.text = sales_header?.status
        tvHistoryParam2.text = "Tanggal: ${sales_header?.sale_date}"
        tvHistoryParam3.text = "Sale ID: ${sales_header?.sale_id}"
        tvHistoryParam4.text = "Customer: ${sales_header?.cust_id}"
        tvHistoryParam5.text = sales_header?.cust_name
        tvHistoryParam6.text = "Total: ${sales_header?.to_be_paid}"
        tvHistoryParam7.text = "Terbayar: ${sales_header?.paid}"

        sales_header?.sale_id?.let { saleId ->
            viewModel.loadHistorySalesDetail(saleId).observe(this, Observer {
                progressHistoryDetail.visibility = View.GONE

                if (it != null) {
                    rvHistoryDetail.adapter = it.result_history_detail?.let { sales_detail ->
                        HistorySalesDetailAdapter(sales_detail)
                    }
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }
}