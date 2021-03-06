package com.iqbalproject.pj_pos.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.adapter.SpinnerSuppAdapter
import com.iqbalproject.pj_pos.adapter.StockPurchaseAdapter
import com.iqbalproject.pj_pos.model.StockDetail
import com.iqbalproject.pj_pos.ui.viewModel.ProductViewModel
import com.iqbalproject.pj_pos.ui.viewModel.SupplierViewModel
import com.iqbalproject.pj_pos.utils.Constants
import com.iqbalproject.pj_pos.utils.Tools
import kotlinx.android.synthetic.main.activity_purchase.*
import org.jetbrains.anko.startActivity

class PurchaseActivity : AppCompatActivity() {

    private lateinit var viewModelSupplier: SupplierViewModel
    private lateinit var viewModelStock: ProductViewModel
    private lateinit var supplierName: String
    private lateinit var supplierId: String

    var purchaseConfirm: MutableList<StockDetail> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.menu_2)

        viewModelSupplier = ViewModelProviders.of(this).get(SupplierViewModel::class.java)
        viewModelStock = ViewModelProviders.of(this).get(ProductViewModel::class.java)

        viewModelSupplier.loadData().observe(this, Observer { suppliers ->
            spinnerSupplier.adapter = suppliers.result?.let {
                SpinnerSuppAdapter(this, it)
            }

            spinnerSupplier.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    supplierName = suppliers.result?.get(position)?.supplier_name.toString()
                    supplierId = suppliers.result?.get(position)?.supplier_id.toString()
                    tvSuppAddress.text = suppliers.result?.get(position)?.supplier_address
                    tvSuppTelp.text = suppliers.result?.get(position)?.supplier_telp
                }
            }
        })

        viewModelStock.loadData().observe(this, Observer {
            when (it.status) {
                false -> {
                    rvItemsPurchase.visibility = View.GONE
                    tvNullPurc.visibility = View.VISIBLE
                }
                else -> {
                    rvItemsPurchase.visibility = View.VISIBLE
                    tvNullPurc.visibility = View.GONE

                    it?.result?.let { stockList ->
                        rvItemsPurchase.adapter = StockPurchaseAdapter(stockList)
                    }
                }
            }
        })

        btnProcessSupp.setOnClickListener {
            if (purchaseConfirm.isNullOrEmpty()) {
                Tools.toastWarning(this, "Harap Lengkapi Data!")
            } else {
                purchaseConfirm.first().name_dummy = supplierName
                purchaseConfirm.first().id_dummy = supplierId
                startActivity<PurchaseConfirmationActivity>(
                    "purchase_confirm" to purchaseConfirm
                )
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_history, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.btn_history -> startActivity<HistoryActivity>("tag_history" to Constants.PURCHASE)
        }
        return super.onOptionsItemSelected(item)
    }
}
