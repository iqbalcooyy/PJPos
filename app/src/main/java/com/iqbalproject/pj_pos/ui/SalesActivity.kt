package com.iqbalproject.pj_pos.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.adapter.StockAdapter
import com.iqbalproject.pj_pos.model.StockDetail
import com.iqbalproject.pj_pos.ui.viewModel.StocksViewModel
import kotlinx.android.synthetic.main.activity_sales.*

class SalesActivity : AppCompatActivity() {

    private lateinit var viewModel: StocksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales)

        viewModel = ViewModelProviders.of(this).get(StocksViewModel::class.java)

        viewModel.getStatus().observe(this, Observer {
            if (it == false) {
                rvItemsSale.visibility = View.GONE
                tvNull.visibility = View.VISIBLE
            } else {
                rvItemsSale.visibility = View.VISIBLE
                tvNull.visibility = View.GONE
            }
        })

        viewModel.getData().observe(this, Observer {
            it?.stocks?.let {
                showData(it)
            }
        })
    }

    private fun showData(data: List<StockDetail>) {
        rvItemsSale.adapter = StockAdapter(data)
    }
}
