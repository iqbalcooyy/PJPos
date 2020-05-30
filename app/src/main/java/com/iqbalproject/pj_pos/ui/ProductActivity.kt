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
import com.iqbalproject.pj_pos.adapter.ProductAdapter
import com.iqbalproject.pj_pos.model.StockDetail
import com.iqbalproject.pj_pos.ui.viewModel.ProductViewModel
import com.iqbalproject.pj_pos.utils.Tools
import kotlinx.android.synthetic.main.activity_product.*
import org.jetbrains.anko.startActivity

class ProductActivity : AppCompatActivity() {

    private lateinit var viewModel: ProductViewModel
    private lateinit var dialogForm: AlertDialog.Builder
    private var menuItem: MenuItem? = null
    private var searchView: SearchView? = null
    private var products: MutableList<StockDetail> = mutableListOf()
    private var productsFilter: MutableList<StockDetail> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Data Product"

        viewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)

        loadDataProduct()

        fabAddProduct.setOnClickListener {
            startActivity<AddProductActivity>(
                "code" to "addProduct"
            )
        }
    }

    override fun onResume() {
        loadDataProduct()
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
                productsFilter.clear()
                for (i in products.indices) {
                    if (products[i].item_name.toLowerCase().trim().contains(newText.toString().toLowerCase().trim()) ||
                        products[i].item_id.toLowerCase().trim().contains(newText.toString().toLowerCase().trim()))
                    {
                        productsFilter.add(products[i])
                    }
                }

                rvProduct.adapter = ProductAdapter(productsFilter)
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

    private fun loadDataProduct() {
        progressProduct.visibility = View.VISIBLE
        viewModel.loadData().observe(this, Observer {
            progressProduct.visibility = View.GONE
            when (it.status) {
                true -> {
                    rvProduct.adapter = it.result?.let { stockList ->
                        products.clear()
                        products.addAll(stockList)
                        ProductAdapter(products)
                    }
                }
                else -> {
                    Tools.alertFailed(this, "Product Response", "Data not found!")
                }
            }
        })
    }

    fun deleteDialog(itemId: String) {
        dialogForm = AlertDialog.Builder(this)
        dialogForm.setTitle("Konfirmasi Hapus")
        dialogForm.setIcon(R.drawable.ic_delete)
        dialogForm.setMessage("Apakah Anda yakin ingin menghapus $itemId ?")
        dialogForm.setCancelable(true)
        dialogForm.setPositiveButton("Ok") { dialog: DialogInterface, i: Int ->
            progressProduct.visibility = View.VISIBLE
            viewModel.deleteData(itemId).observe(this, Observer {
                progressProduct.visibility = View.GONE
                loadDataProduct()
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
