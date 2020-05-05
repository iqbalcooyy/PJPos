package com.iqbalproject.pj_pos.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.adapter.CustomerAdapter
import com.iqbalproject.pj_pos.ui.viewModel.CustomerViewModel
import com.iqbalproject.pj_pos.utils.Tools
import kotlinx.android.synthetic.main.activity_customer.*
import org.jetbrains.anko.startActivity

class CustomerActivity : AppCompatActivity() {

    private lateinit var viewModel: CustomerViewModel
    private lateinit var dialogForm: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Data Customer"

        viewModel = ViewModelProviders.of(this).get(CustomerViewModel::class.java)

        loadDataCustomer()

        fabAddCust.setOnClickListener {
            startActivity<EditActivity>(
                "code" to "addCust"
            )
        }
    }

    override fun onResume() {
        loadDataCustomer()
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

    private fun loadDataCustomer() {
        progressCustomer.visibility = View.VISIBLE
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

    fun deleteDialog(customerId: String) {
        dialogForm = AlertDialog.Builder(this)
        dialogForm.setTitle("Konfirmasi Hapus")
        dialogForm.setIcon(R.drawable.ic_delete)
        dialogForm.setMessage("Apakah Anda yakin ingin menghapus $customerId ?")
        dialogForm.setCancelable(true)
        dialogForm.setPositiveButton("Ok") { dialog: DialogInterface, i: Int ->
            progressCustomer.visibility = View.VISIBLE
            viewModel.deleteData(customerId).observe(this, Observer {
                progressCustomer.visibility = View.GONE
                loadDataCustomer()
                when (it.status) {
                    true -> {
                        Tools.alertSuccess(this, "Sukses Hapus", it.message.toString(), false)
                    }
                    else -> {
                        Tools.alertFailed(this, "Gagal Hapus", it.message.toString())
                    }
                }
            })
        }
        dialogForm.setNegativeButton("Batal") { dialog: DialogInterface, i: Int ->
            dialog.dismiss()
        }
        dialogForm.show()
    }
}
