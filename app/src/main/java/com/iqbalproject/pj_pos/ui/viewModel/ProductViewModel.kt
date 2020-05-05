package com.iqbalproject.pj_pos.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iqbalproject.pj_pos.model.EditResponse
import com.iqbalproject.pj_pos.model.Stocks
import com.iqbalproject.pj_pos.network.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel : ViewModel() {

    fun loadData(): LiveData<Stocks> {
        val data = MutableLiveData<Stocks>()

        NetworkConfig().api().getStocks().enqueue(object : Callback<Stocks> {
            override fun onFailure(call: Call<Stocks>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(call: Call<Stocks>, response: Response<Stocks>) {
                data.value = response.body()
            }
        })

        return data
    }

    fun addData(
        itemName: String,
        uom: String,
        sellingPrice: Int,
        purchasePrice: Int
    ): LiveData<EditResponse> {
        val data = MutableLiveData<EditResponse>()

        NetworkConfig().api().addStocks(
            item_name = itemName,
            uom = uom,
            selling_price = sellingPrice,
            purchase_price = purchasePrice
        ).enqueue(object : Callback<EditResponse> {
            override fun onFailure(call: Call<EditResponse>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(call: Call<EditResponse>, response: Response<EditResponse>) {
                data.value = response.body()
            }
        })

        return data
    }

    fun editData(
        itemId: String,
        itemName: String,
        uom: String,
        itemQty: Int,
        sellingPrice: Int,
        purchasePrice: Int
    ): LiveData<EditResponse> {
        val data = MutableLiveData<EditResponse>()

        NetworkConfig().api().editStocks(
            item_id = itemId,
            item_name = itemName,
            uom = uom,
            item_qty = itemQty,
            selling_price = sellingPrice,
            purchase_price = purchasePrice
        ).enqueue(object : Callback<EditResponse> {
            override fun onFailure(call: Call<EditResponse>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(call: Call<EditResponse>, response: Response<EditResponse>) {
                data.value = response.body()
            }
        })

        return data
    }

    fun deleteData(itemId: String): LiveData<EditResponse> {
        val data = MutableLiveData<EditResponse>()

        NetworkConfig().api().deleteStocks(item_id = itemId)
            .enqueue(object : Callback<EditResponse> {
                override fun onFailure(call: Call<EditResponse>, t: Throwable) {
                    data.value = null
                }

                override fun onResponse(
                    call: Call<EditResponse>,
                    response: Response<EditResponse>
                ) {
                    data.value = response.body()
                }
            })

        return data
    }
}