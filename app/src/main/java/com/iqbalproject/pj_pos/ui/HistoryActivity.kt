package com.iqbalproject.pj_pos.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.adapter.HistoryPurchaseAdapter
import com.iqbalproject.pj_pos.adapter.HistorySalesAdapter
import com.iqbalproject.pj_pos.model.Purchase
import com.iqbalproject.pj_pos.model.Sales
import com.iqbalproject.pj_pos.ui.viewModel.HistoryViewModel
import com.iqbalproject.pj_pos.utils.Constants
import kotlinx.android.synthetic.main.activity_history.*
import java.util.*

class HistoryActivity : AppCompatActivity() {

    private lateinit var viewModel: HistoryViewModel
    private lateinit var tagHistory: String
    private var sales: MutableList<Sales> = mutableListOf()
    private var salesFilter: MutableList<Sales> = mutableListOf()
    private var purchase: MutableList<Purchase> = mutableListOf()
    private var purchaseFilter: MutableList<Purchase> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tagHistory = intent.getStringExtra("tag_history")
        viewModel = ViewModelProviders.of(this).get(HistoryViewModel::class.java)

        when (tagHistory) {
            Constants.SALES -> {
                supportActionBar?.title = "Riwayat Penjualan"
            }
            Constants.PURCHASE -> {
                supportActionBar?.title = "Riwayat Pembelian"
            }
        }

        loadHistory()
    }

    override fun onResume() {
        loadHistory()
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val menuItem: MenuItem? = menu?.findItem(R.id.btn_search)
        val searchView: SearchView? = menuItem?.actionView as SearchView

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                when (tagHistory) {
                    Constants.SALES -> {
                        salesFilter.clear()
                        for (i in sales.indices) {
                            if (sales[i].cust_name!!.toLowerCase(Locale.ROOT).trim()
                                    .contains(newText.toString().toLowerCase(Locale.ROOT).trim()) ||
                                sales[i].sale_id!!.toLowerCase(Locale.ROOT).trim()
                                    .contains(newText.toString().toLowerCase(Locale.ROOT).trim())
                            ) {
                                salesFilter.add(sales[i])
                            }
                        }

                        rvHistory.adapter = HistorySalesAdapter(salesFilter)
                    }
                    Constants.PURCHASE -> {
                        purchaseFilter.clear()
                        for (i in purchase.indices) {
                            if (purchase[i].supplier_name!!.toLowerCase(Locale.ROOT).trim()
                                    .contains(newText.toString().toLowerCase(Locale.ROOT).trim()) ||
                                purchase[i].purchase_id!!.toLowerCase(Locale.ROOT).trim()
                                    .contains(newText.toString().toLowerCase(Locale.ROOT).trim())
                            ) {
                                purchaseFilter.add(purchase[i])
                            }
                        }

                        rvHistory.adapter = HistoryPurchaseAdapter(purchaseFilter)

                    }
                }

                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadHistory() {
        progressHistory.visibility = View.VISIBLE

        when (tagHistory) {
            Constants.SALES -> {
                viewModel.loadHistorySales().observe(this, Observer {
                    progressHistory.visibility = View.GONE
                    if (it != null) {
                        rvHistory.adapter = it.result_history?.let { listSales ->
                            sales.clear()
                            sales.addAll(listSales)
                            HistorySalesAdapter(listSales)
                        }
                    }
                })
            }
            Constants.PURCHASE -> {
                viewModel.loadHistoryPurchase().observe(this, Observer {
                    progressHistory.visibility = View.GONE
                    if (it != null) {
                        rvHistory.adapter = it.result_history?.let { listPurchase ->
                            purchase.clear()
                            purchase.addAll(listPurchase)
                            HistoryPurchaseAdapter(listPurchase)
                        }
                    }
                })
            }
        }
    }
}