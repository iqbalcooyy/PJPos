package com.iqbalproject.pj_pos.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.adapter.AccReceivableAdapter
import com.iqbalproject.pj_pos.ui.viewModel.AccReceivViewModel
import com.iqbalproject.pj_pos.utils.Tools
import kotlinx.android.synthetic.main.activity_acc_receivable.*
import kotlinx.android.synthetic.main.item_adjust_pay_ar.view.*

class AccReceivableActivity : AppCompatActivity() {

    private lateinit var viewModel: AccReceivViewModel
    private lateinit var dialogForm: AlertDialog.Builder
    private lateinit var dialogView: View
    private lateinit var inflater: LayoutInflater
    private lateinit var etBayar: EditText
    private lateinit var etNotes: EditText
    private lateinit var tvArTotal: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acc_receivable)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Piutang"

        viewModel = ViewModelProviders.of(this).get(AccReceivViewModel::class.java)

        loadDataPiutang()
    }

    override fun onResume() {
        loadDataPiutang()
        super.onResume()
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

    private fun loadDataPiutang() {
        progressAccReceivable.visibility = View.VISIBLE
        viewModel.loadData().observe(this, Observer {
            progressAccReceivable.visibility = View.GONE
            rvAccReceivable.adapter = it.result?.let { listReceivable ->
                AccReceivableAdapter(listReceivable)
            }
        })
    }

    fun settlement(arId: String, arTotal: String) {
        dialogForm = AlertDialog.Builder(this)
        dialogForm.setTitle("Konfirmasi Penyelesaian")
        dialogForm.setIcon(R.drawable.ic_assignment)
        dialogForm.setMessage("Selesaikan transaksi $arId sebesar Rp $arTotal ?")
        dialogForm.setCancelable(true)
        dialogForm.setPositiveButton("Ok") { dialog: DialogInterface, i: Int ->
            adjustmentPay(arId, arTotal)
            dialog.dismiss()
        }
        dialogForm.setNegativeButton("Batal") { dialog: DialogInterface, i: Int ->
            dialog.dismiss()
        }
        dialogForm.show()
    }

    private fun adjustmentPay(arId: String, arTotal: String) {
        dialogForm = AlertDialog.Builder(this)
        inflater = layoutInflater
        dialogView = inflater.inflate(R.layout.item_adjust_pay_ar, null)
        etBayar = dialogView.etBayarAr
        etNotes = dialogView.etNotesAr
        tvArTotal = dialogView.tvArTotal

        tvArTotal.text = "Total Piutang : Rp$arTotal"

        dialogForm.setView(dialogView)
        dialogForm.setCancelable(false)
        dialogForm.setPositiveButton("Proses", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if (!etBayar.text.isNullOrEmpty()) {
                    if (etBayar.text.toString().toInt() > arTotal.toInt()) {
                        Tools.alertFailed(
                            this@AccReceivableActivity,
                            "Perhatian",
                            "Tidak boleh melebihi total piutang!"
                        )
                    } else {
                        p0?.dismiss()
                        progressAccReceivable.visibility = View.VISIBLE
                        viewModel.editData(
                            arId,
                            etBayar.text.trim().toString(),
                            etNotes.text.trim().toString()
                        )
                            .observe(this@AccReceivableActivity, Observer {
                                progressAccReceivable.visibility = View.GONE
                                when (it.status) {
                                    true -> {
                                        Tools.alertSuccess(
                                            this@AccReceivableActivity,
                                            "Sukses",
                                            it.message.toString(),
                                            false
                                        )
                                        loadDataPiutang()
                                    }
                                    false -> {
                                        Tools.alertFailed(
                                            this@AccReceivableActivity,
                                            "Gagal",
                                            it.message.toString()
                                        )
                                    }
                                }
                            })
                    }

                } else {
                    Tools.toastWarning(
                        this@AccReceivableActivity,
                        "Mohon masukan jumlah pembayaran!"
                    )
                }
            }
        })
        dialogForm.setNegativeButton("Batal", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, p1: Int) {
                dialog?.dismiss()
            }
        })
        dialogForm.show()
    }
}
