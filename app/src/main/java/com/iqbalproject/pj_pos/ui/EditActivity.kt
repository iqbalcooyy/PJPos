package com.iqbalproject.pj_pos.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.model.CustomerResult
import com.iqbalproject.pj_pos.model.SupplierResult
import com.iqbalproject.pj_pos.ui.viewModel.CustomerViewModel
import com.iqbalproject.pj_pos.ui.viewModel.SupplierViewModel
import com.iqbalproject.pj_pos.utils.Tools
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    private lateinit var viewModelSupplier: SupplierViewModel
    private lateinit var viewModelCustomer: CustomerViewModel
    private lateinit var code: String
    private lateinit var customer: CustomerResult
    private lateinit var supplier: SupplierResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        code = intent.getStringExtra("code")
        viewModelSupplier = ViewModelProviders.of(this).get(SupplierViewModel::class.java)
        viewModelCustomer = ViewModelProviders.of(this).get(CustomerViewModel::class.java)

        when (code) {
            "cust" -> {
                supportActionBar?.title = "Edit Customer"
                customer = intent.getParcelableExtra("customer")
                etId.setText(customer.cust_id)
                etName.setText(customer.cust_name)
                etAddress.setText(customer.cust_address)
                etTelp.setText(customer.cust_telp)
                etId.hint = "Customer ID"
                etName.hint = "Customer Name"
                etAddress.hint = "Customer Address"
                etTelp.hint = "Customer Telp."

                btnSimpan.setOnClickListener { btn ->
                    progressSimpan.visibility = View.VISIBLE
                    btn.isEnabled = false
                    viewModelCustomer.editData(
                        etId.text.toString(),
                        etName.text.toString(),
                        etAddress.text.toString(),
                        etTelp.text.toString()
                    )
                        .observe(this, Observer {
                            progressSimpan.visibility = View.GONE
                            btn.isEnabled = true
                            when (it.status) {
                                true -> {
                                    Tools.alertSuccess(this, "Sukses Edit", it.message.toString(), true)
                                }
                                else -> {
                                    Tools.alertFailed(this, "Gagal Edit", it.message.toString())
                                }
                            }
                        })
                }
            }
            "supp" -> {
                supportActionBar?.title = "Edit Supplier"
                supplier = intent.getParcelableExtra("supplier")
                etId.setText(supplier.supplier_id)
                etName.setText(supplier.supplier_name)
                etAddress.setText(supplier.supplier_address)
                etTelp.setText(supplier.supplier_telp)
                etId.hint = "Supplier ID"
                etName.hint = "Supplier Name"
                etAddress.hint = "Supplier Address"
                etTelp.hint = "Supplier Telp."

                btnSimpan.setOnClickListener { btn ->
                    progressSimpan.visibility = View.VISIBLE
                    btn.isEnabled = false
                    viewModelSupplier.editData(
                        etId.text.toString(),
                        etName.text.toString(),
                        etAddress.text.toString(),
                        etTelp.text.toString()
                    )
                        .observe(this, Observer {
                            progressSimpan.visibility = View.GONE
                            btn.isEnabled = true
                            when (it.status) {
                                true -> {
                                    Tools.alertSuccess(this, "Sukses Edit", it.message.toString(), true)
                                }
                                else -> {
                                    Tools.alertFailed(this, "Gagal Edit", it.message.toString())
                                }
                            }
                        })
                }
            }
            "addCust" -> {
                supportActionBar?.title = "Tambah Customer"
                etId.visibility = View.GONE
                etName.hint = "Customer Name"
                etAddress.hint = "Customer Address"
                etTelp.hint = "Customer Telp."

                btnSimpan.setOnClickListener { btn ->
                    progressSimpan.visibility = View.VISIBLE
                    btn.isEnabled = false
                    viewModelCustomer.addData(
                        etName.text.toString(),
                        etAddress.text.toString(),
                        etTelp.text.toString()
                    )
                        .observe(this, Observer {
                            progressSimpan.visibility = View.GONE
                            btn.isEnabled = true
                            when (it.status) {
                                true -> {
                                    Tools.alertSuccess(
                                        this,
                                        "Sukses tambah data",
                                        it.message.toString(),
                                        true
                                    )
                                }
                                else -> {
                                    Tools.alertFailed(
                                        this,
                                        "Gagal tambah data",
                                        it.message.toString()
                                    )
                                }
                            }
                        })
                }
            }
            "addSupp" -> {
                supportActionBar?.title = "Tambah Supplier"
                etId.visibility = View.GONE
                etName.hint = "Supplier Name"
                etAddress.hint = "Supplier Address"
                etTelp.hint = "Supplier Telp."

                btnSimpan.setOnClickListener { btn ->
                    progressSimpan.visibility = View.VISIBLE
                    btn.isEnabled = false
                    viewModelSupplier.addData(
                        etName.text.toString(),
                        etAddress.text.toString(),
                        etTelp.text.toString()
                    )
                        .observe(this, Observer {
                            progressSimpan.visibility = View.GONE
                            btn.isEnabled = true
                            when (it.status) {
                                true -> {
                                    Tools.alertSuccess(
                                        this,
                                        "Sukses tambah data",
                                        it.message.toString(),
                                        true
                                    )
                                }
                                else -> {
                                    Tools.alertFailed(
                                        this,
                                        "Gagal tambah data",
                                        it.message.toString()
                                    )
                                }
                            }
                        })
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
