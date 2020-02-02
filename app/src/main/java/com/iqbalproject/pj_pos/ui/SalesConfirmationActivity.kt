package com.iqbalproject.pj_pos.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.model.StockDetail
import com.iqbalproject.pj_pos.utils.Tools
import kotlinx.android.synthetic.main.activity_sales_confirmation.*

class SalesConfirmationActivity : AppCompatActivity() {

    private val items: MutableList<StockDetail> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales_confirmation)

        tvTransDateConfirm.text = Tools.getCurrentDate()
        initData()
    }

    private fun initData() {
        items.addAll(intent.getParcelableArrayListExtra("saleConfirm"))
        var total = 0

        for (i in items.indices) {
            total += items.get(i).pay

            tvCustNameConf.text = items[i].cust_name
            tvCustAddressConf.text = items[i].cust_address
            tvCustTelpConf.text = items[i].cust_telp
        }

        tvTotalPayConfirm.text = Tools.convertRupiahsFormat(total.toDouble())
        tvSubTot.text = Tools.convertRupiahsFormat(total.toDouble())
        tvTotPayConf.text = Tools.convertRupiahsFormat(total.toDouble())
    }
}