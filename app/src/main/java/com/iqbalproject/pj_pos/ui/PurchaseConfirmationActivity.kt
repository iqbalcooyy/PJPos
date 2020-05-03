package com.iqbalproject.pj_pos.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cn.refactor.lib.colordialog.PromptDialog
import com.iqbalproject.pj_pos.R
import com.iqbalproject.pj_pos.adapter.PurchaseConfirmationAdapter
import com.iqbalproject.pj_pos.model.StockDetail
import com.iqbalproject.pj_pos.ui.viewModel.PurchaseViewModel
import com.iqbalproject.pj_pos.utils.Tools
import kotlinx.android.synthetic.main.activity_purchase_confirmation.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class PurchaseConfirmationActivity : AppCompatActivity() {

    private var purchaseConfirm: MutableList<StockDetail> = mutableListOf()
    private lateinit var viewModel: PurchaseViewModel

    private var itemId: MutableList<String> = mutableListOf()
    private var purchaseQty: MutableList<Int> = mutableListOf()
    private var purchaseUom: MutableList<String> = mutableListOf()
    private var purchasePrice: MutableList<Int> = mutableListOf()
    private var totalPrice: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase_confirmation)

        viewModel = ViewModelProviders.of(this).get(PurchaseViewModel::class.java)
        purchaseConfirm.addAll(intent.getParcelableArrayListExtra("purchase_confirm"))
        rvItemsPurcConf.adapter = PurchaseConfirmationAdapter(purchaseConfirm)

        for (i in purchaseConfirm.indices) {
            totalPrice += purchaseConfirm[i].amount_dummy
            itemId.add(i, purchaseConfirm[i].item_id)
            purchaseQty.add(i, purchaseConfirm[i].qty_dummy)
            purchaseUom.add(i, purchaseConfirm[i].uom)
            purchasePrice.add(i, purchaseConfirm[i].amount_dummy)
        }

        tvTotalPrice.text = "Rp$totalPrice"
        btnConfirmPurchase.setOnClickListener { btnConfirm ->
            progressPurchase.visibility = View.VISIBLE
            btnConfirm.isEnabled = false
            viewModel.pushData(
                purchaseConfirm.first().id_dummy.toString(),
                totalPrice,
                itemId,
                purchaseQty,
                purchaseUom,
                purchasePrice
            ).observe(this, Observer {
                when (it.status) {
                    "true" -> {
                        //Tools.alertSuccess(this, "Success", it.message.toString())
                        progressPurchase.visibility = View.GONE
                        btnConfirm.isEnabled = true
                        PromptDialog(this)
                            .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                            .setAnimationEnable(true)
                            .setTitleText("Success")
                            .setContentText(it.message.toString())
                            .setPositiveListener("OK") {
                                it.dismiss()
                                startActivity(
                                    intentFor<MainActivity>()
                                        .clearTop()
                                        .newTask()
                                )
                            }
                            .show()
                    }
                    else -> {
                        progressPurchase.visibility = View.GONE
                        btnConfirm.isEnabled = true
                        Tools.alertFailed(this, "Failed", it.message.toString())
                    }
                }
            })
        }
    }
}
