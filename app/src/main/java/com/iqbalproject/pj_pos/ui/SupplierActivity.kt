package com.iqbalproject.pj_pos.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.adapter.SupplierAdapter
import com.iqbalproject.pj_pos.ui.viewModel.SupplierViewModel
import com.iqbalproject.pj_pos.utils.Tools
import kotlinx.android.synthetic.main.activity_supplier.*

class SupplierActivity : AppCompatActivity() {

    private lateinit var viewModel: SupplierViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supplier)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Data Supplier"

        viewModel = ViewModelProviders.of(this).get(SupplierViewModel::class.java)

        viewModel.loadData().observe(this, Observer {
            progressSupplier.visibility = View.GONE
            when(it.status) {
                true -> {
                    rvSupplier.adapter = it.result?.let { suppList ->
                        SupplierAdapter(suppList)
                    }
                }
                else -> {
                    Tools.alertFailed(this, "Customer Response", "Data not found!")
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
