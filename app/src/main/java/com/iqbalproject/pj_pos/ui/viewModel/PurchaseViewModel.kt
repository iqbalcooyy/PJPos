package com.iqbalproject.pj_pos.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iqbalproject.pj_pos.model.TrxResponses
import com.iqbalproject.pj_pos.network.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PurchaseViewModel : ViewModel() {

    fun pushData(
        supplierId: String,
        priceTotal: Int,
        itemId: List<String>,
        purchaseQty: List<Int>,
        purchaseUom: List<String>,
        purchasePrice: List<Int>
    ): LiveData<TrxResponses> {
        val data = MutableLiveData<TrxResponses>()

        NetworkConfig().api().trx_purchase(
            supplier_id = supplierId,
            price_total = priceTotal,
            item_id = itemId,
            purchase_qty = purchaseQty,
            purchase_uom = purchaseUom,
            purchase_price = purchasePrice
        ).enqueue(object : Callback<TrxResponses> {
            override fun onFailure(call: Call<TrxResponses>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(call: Call<TrxResponses>, response: Response<TrxResponses>) {
                data.value = response.body()
            }

        })

        return data
    }
}