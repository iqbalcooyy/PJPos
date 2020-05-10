package com.iqbalproject.pj_pos.ui

import android.os.Bundle
import android.widget.BaseAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.adapter.MainMenuAdapter
import com.iqbalproject.pj_pos.utils.SessionManager
import com.iqbalproject.pj_pos.utils.Tools
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var menuAdapter: BaseAdapter
    private var itemMenu: MutableList<String> = mutableListOf()
    private var iconMenu: MutableList<Int> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*== Login Session ==*/
        sessionManager = SessionManager(applicationContext)
        sessionManager.checkLogin()
        /*===========*/

        inflateMainMenu()
        menuAdapter = MainMenuAdapter(this, itemMenu, iconMenu)
        gvMainMenu.adapter = menuAdapter

        gvMainMenu.setOnItemClickListener { parent, view, position, id ->
            when (position) {  // related to strings.xml -> main_menu
                0 -> startActivity<SalesActivity>()
                1 -> startActivity<PurchaseActivity>()
                2 -> startActivity<AccReceivableActivity>()
                3 -> startActivity<ProductActivity>()
                4 -> startActivity<SupplierActivity>()
                5 -> startActivity<CustomerActivity>()
                6 -> startActivity<ReportActivity>()
                7 -> logoutProcess()
                else -> Tools.toastWarning(this, "Under Development :)")
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }

    private fun inflateMainMenu() {
        val menus = resources.getStringArray(R.array.main_menu)
        val icons = resources.obtainTypedArray(R.array.main_menu_icon)
        itemMenu.clear()
        iconMenu.clear()
        for (i in menus.indices) {
            itemMenu.add(i, menus[i])
            iconMenu.add(i, icons.getResourceId(i, 0))
        }
        icons.recycle()
    }

    private fun logoutProcess() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Confirm Exit")
        dialog.setIcon(R.drawable.ic_lock)
        dialog.setMessage("Are you sure want to exit?")
        dialog.setCancelable(false)
        dialog.setPositiveButton("Ok") { dialog, which ->
            sessionManager.logoutUser()
            finish()
        }
        dialog.setNegativeButton("Cancel") { dialogs, which ->
            dialogs.dismiss()
        }
        dialog.create().show()
    }
}
