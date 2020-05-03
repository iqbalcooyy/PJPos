package com.iqbalproject.pj_pos.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.model.CustomerResult
import com.iqbalproject.pj_pos.model.SupplierResult
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    private lateinit var code: String
    private lateinit var customer: CustomerResult
    private lateinit var supplier: SupplierResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        code = intent.getStringExtra("code")

        when (code) {
            "cust" -> {
                supportActionBar?.title = "Edit Customer"
                customer = intent.getParcelableExtra("customer")
                etId.setText(customer.cust_id)
                etName.setText(customer.cust_name)
                etAddress.setText(customer.cust_address)
                etTelp.setText(customer.cust_telp)
            }
            "supp" -> {
                supportActionBar?.title = "Edit Supplier"
                supplier = intent.getParcelableExtra("supplier")
                etId.setText(supplier.supplier_id)
                etName.setText(supplier.supplier_name)
                etAddress.setText(supplier.supplier_address)
                etTelp.setText(supplier.supplier_telp)
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
