package com.iqbalproject.pj_pos.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.adapter.DetailArAdapter
import com.iqbalproject.pj_pos.model.AccReceivable
import com.iqbalproject.pj_pos.ui.viewModel.AccReceivViewModel
import com.iqbalproject.pj_pos.utils.Tools
import kotlinx.android.synthetic.main.activity_detail_acc_receive.*

class DetailAccReceiveActivity : AppCompatActivity() {

    private lateinit var viewModel: AccReceivViewModel
    private lateinit var receivable: AccReceivable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_acc_receive)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Riwayat Pembayaran Piutang"

        receivable = intent.getParcelableExtra("receivable")
        viewModel = ViewModelProviders.of(this).get(AccReceivViewModel::class.java)

        tvArDateDetail.text = "Tanggal: ${receivable.ar_date}"
        tvStatusArDetail.text = "Status: ${receivable.ar_status}"
        tvArIdDetail.text = "AR ID: ${receivable.ar_id}"
        tvSaleIdArDetail.text = "Sale ID: ${receivable.sale_id}"
        tvCustIdArDetail.text = "Customer: ${receivable.cust_id}"
        tvCustNameArDetail.text = "${receivable.cust_name}"
        tvArTotalDetail.text = "Total AR: Rp ${receivable.ar_total}"
        tvRemainPayment.text = "Sisa AR: Rp ${receivable.remaining_payment}"

        viewModel.loadDetailAr(receivable.ar_id.toString()).observe(this, Observer {
            progressDetailAr.visibility = View.GONE
            when(it.status) {
                true -> {
                    if (it.rownum == 0) {
                        Tools.toastWarning(this, "Riwayat pembayaran tidak tersedia.")
                    } else {
                        rvItemDetailAr.adapter = it.result?.let { listDetail ->
                            DetailArAdapter(listDetail)
                        }
                    }
                }
                false -> {
                    Tools.alertFailed(this, "Gagal", it.message.toString())
                }
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
