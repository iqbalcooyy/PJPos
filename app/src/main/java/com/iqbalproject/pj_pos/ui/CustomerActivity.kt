package com.iqbalproject.pj_pos.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.adapter.CustomerAdapter
import com.iqbalproject.pj_pos.ui.viewModel.CustomerViewModel
import com.iqbalproject.pj_pos.utils.Tools
import kotlinx.android.synthetic.main.activity_customer.*

class CustomerActivity : AppCompatActivity() {

    private lateinit var viewModel: CustomerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Data Customer"

        viewModel = ViewModelProviders.of(this).get(CustomerViewModel::class.java)

        viewModel.loadData().observe(this, Observer {
            progressCustomer.visibility = View.GONE
            when(it.status) {
                true -> {
                    rvCustomer.adapter = it.result?.let { custList ->
                        CustomerAdapter(custList)
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
