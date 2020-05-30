package com.iqbalproject.pj_pos.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.adapter.SupplierAdapter
import com.iqbalproject.pj_pos.model.SupplierResult
import com.iqbalproject.pj_pos.ui.viewModel.SupplierViewModel
import com.iqbalproject.pj_pos.utils.Tools
import kotlinx.android.synthetic.main.activity_supplier.*
import org.jetbrains.anko.startActivity

class SupplierActivity : AppCompatActivity() {

    private lateinit var viewModel: SupplierViewModel
    private lateinit var dialogForm: AlertDialog.Builder
    private var menuItem: MenuItem? = null
    private var searchView: SearchView? = null
    private var supplier: MutableList<SupplierResult> = mutableListOf()
    private var supplierFilter: MutableList<SupplierResult> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supplier)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Data Supplier"

        viewModel = ViewModelProviders.of(this).get(SupplierViewModel::class.java)

        loadDataSupplier()

        fabAddSupp.setOnClickListener {
            startActivity<EditActivity>(
                "code" to "addSupp"
            )
        }
    }

    override fun onResume() {
        loadDataSupplier()
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        menuItem = menu?.findItem(R.id.btn_search)
        searchView = menuItem?.actionView as SearchView

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                supplierFilter.clear()
                for (i in supplier.indices) {
                    if (supplier[i].supplier_id!!.toLowerCase().trim().contains(newText.toString().toLowerCase().trim()) ||
                        supplier[i].supplier_name!!.toLowerCase().trim().contains(newText.toString().toLowerCase().trim()))
                    {
                        supplierFilter.add(supplier[i])
                    }
                }

                rvSupplier.adapter = SupplierAdapter(supplierFilter)
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadDataSupplier() {
        progressSupplier.visibility = View.VISIBLE
        viewModel.loadData().observe(this, Observer {
            progressSupplier.visibility = View.GONE
            when(it.status) {
                true -> {
                    rvSupplier.adapter = it.result?.let { suppList ->
                        supplier.clear()
                        supplier.addAll(suppList)
                        SupplierAdapter(supplier)
                    }
                }
                else -> {
                    Tools.alertFailed(this, "Customer Response", "Data not found!")
                }
            }
        })
    }

    fun deleteDialog(supplierId: String) {
        dialogForm = AlertDialog.Builder(this)
        dialogForm.setTitle("Konfirmasi Hapus")
        dialogForm.setIcon(R.drawable.ic_delete)
        dialogForm.setMessage("Apakah Anda yakin ingin menghapus $supplierId ?")
        dialogForm.setCancelable(true)
        dialogForm.setPositiveButton("Ok") { dialog: DialogInterface, i: Int ->
            progressSupplier.visibility = View.VISIBLE
            viewModel.deleteData(supplierId).observe(this, Observer {
                progressSupplier.visibility = View.GONE
                loadDataSupplier()
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
