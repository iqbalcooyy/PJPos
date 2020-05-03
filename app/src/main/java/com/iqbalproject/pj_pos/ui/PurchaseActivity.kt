package com.iqbalproject.pj_pos.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.adapter.StockPurchaseAdapter
import com.iqbalproject.pj_pos.adapter.SpinnerSuppAdapter
import com.iqbalproject.pj_pos.model.StockDetail
import com.iqbalproject.pj_pos.ui.viewModel.StocksViewModel
import com.iqbalproject.pj_pos.ui.viewModel.SupplierViewModel
import com.iqbalproject.pj_pos.utils.Tools
import kotlinx.android.synthetic.main.activity_purchase.*
import org.jetbrains.anko.startActivity

class PurchaseActivity : AppCompatActivity() {

    private lateinit var viewModelSupplier: SupplierViewModel
    private lateinit var viewModelStock: StocksViewModel
    private lateinit var supplierName: String
    private lateinit var supplierId: String

    var purchaseConfirm: MutableList<StockDetail> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase)

        viewModelSupplier = ViewModelProviders.of(this).get(SupplierViewModel::class.java)
        viewModelStock = ViewModelProviders.of(this).get(StocksViewModel::class.java)

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

        viewModelStock.getStatus().observe(this, Observer {
            if (it == false) {
                rvItemsPurchase.visibility = View.GONE
                tvNullPurc.visibility = View.VISIBLE
            } else {
                rvItemsPurchase.visibility = View.VISIBLE
                tvNullPurc.visibility = View.GONE
            }
        })

        viewModelStock.getData().observe(this, Observer {
            it?.result?.let {
                rvItemsPurchase.adapter = StockPurchaseAdapter(it)
            }
        })

        btnProcessSupp.setOnClickListener {
            //Log.d("PURCHASEE", purchaseConfirm.toString())
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
}
