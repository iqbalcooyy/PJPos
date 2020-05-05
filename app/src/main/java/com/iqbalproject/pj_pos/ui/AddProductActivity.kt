package com.iqbalproject.pj_pos.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.model.StockDetail
import com.iqbalproject.pj_pos.ui.viewModel.ProductViewModel
import com.iqbalproject.pj_pos.utils.Tools
import kotlinx.android.synthetic.main.activity_add_product.*

class AddProductActivity : AppCompatActivity() {

    private lateinit var viewModel: ProductViewModel
    private lateinit var code: String
    private lateinit var product: StockDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        code = intent.getStringExtra("code")
        viewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)

        when (code) {
            "editProduct" -> {
                supportActionBar?.title = "Edit Produk"
                product = intent.getParcelableExtra("product")
                etIdProd.setText(product.item_id)
                etNameProd.setText(product.item_name)
                etUomProd.setText(product.uom)
                etQtyProd.setText(product.item_qty.toString())
                etSellPrice.setText(product.selling_price.toString())
                etBuyPrice.setText(product.purchase_price.toString())

                btnSaveProd.setOnClickListener {
                    if (etNameProd.text.toString().trim().isNullOrEmpty() ||
                        etUomProd.text.toString().trim().isNullOrEmpty() ||
                        etSellPrice.text.toString().trim().isNullOrEmpty() ||
                        etBuyPrice.text.toString().trim().isNullOrEmpty() ||
                        etQtyProd.text.toString().trim().isNullOrEmpty()
                    ) {
                        Tools.alertInfo(this, "Informasi", "Mohon Lengkapi Data!")
                    } else {
                        progressSaveProd.visibility = View.VISIBLE
                        viewModel.editData(
                            etIdProd.text.toString(),
                            etNameProd.text.toString(),
                            etUomProd.text.toString(),
                            etQtyProd.text.toString().toInt(),
                            etSellPrice.text.toString().toInt(),
                            etBuyPrice.text.toString().toInt()
                        ).observe(this, Observer {
                            progressSaveProd.visibility = View.GONE
                            when (it.status) {
                                true -> {
                                    Tools.alertSuccess(
                                        this,
                                        "Sukses Simpan",
                                        it.message.toString(),
                                        true
                                    )
                                }
                                else -> {
                                    Tools.alertFailed(this, "Gagal Simpan", it.message.toString())
                                }
                            }
                        })
                    }
                }
            }
            "addProduct" -> {
                supportActionBar?.title = "Tambah Produk"
                etIdProd.visibility = View.GONE
                etQtyProd.visibility = View.GONE

                btnSaveProd.setOnClickListener {
                    if (etNameProd.text.toString().trim().isNullOrEmpty() ||
                        etUomProd.text.toString().trim().isNullOrEmpty() ||
                        etSellPrice.text.toString().trim().isNullOrEmpty() ||
                        etBuyPrice.text.toString().trim().isNullOrEmpty()
                    ) {
                        Tools.alertInfo(this, "Informasi", "Mohon Lengkapi Data!")
                    } else {
                        progressSaveProd.visibility = View.VISIBLE
                        viewModel.addData(
                            etNameProd.text.toString(),
                            etUomProd.text.toString(),
                            etSellPrice.text.toString().toInt(),
                            etBuyPrice.text.toString().toInt()
                        ).observe(this, Observer {
                            progressSaveProd.visibility = View.GONE
                            when (it.status) {
                                true -> {
                                    Tools.alertSuccess(
                                        this,
                                        "Sukses Simpan",
                                        it.message.toString(),
                                        true
                                    )
                                }
                                else -> {
                                    Tools.alertFailed(this, "Gagal Simpan", it.message.toString())
                                }
                            }
                        })
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
