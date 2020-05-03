package com.iqbalproject.pj_pos.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iqbalproject.pj_pos.model.TrxResponses
import com.iqbalproject.pj_pos.network.NetworkConfig
import com.iqbalproject.pj_pos.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException

class SalesConfirmViewModel : ViewModel() {

    private var status = MutableLiveData<Boolean>()
    private var data = MutableLiveData<TrxResponses>()

    fun loadData(
        custId: String,
        itemId: List<String>,
        discount: Int,
        to_be_paid: Int,
        saleQty: List<Int>,
        paid: Int
    ) {
        status.value = null

        NetworkConfig().api().trx_sales(
            cust_id = custId,
            item_id = itemId,
            discount = discount,
            to_be_paid = to_be_paid,
            sale_qty = saleQty,
            paid = paid
        )
            .enqueue(object : Callback<TrxResponses> {
                override fun onFailure(call: Call<TrxResponses>, t: Throwable) {
                    status.value = false

                    if (t is SocketTimeoutException)
                        data.postValue(TrxResponses(status.toString(), null, Constants.RES_ON_TIMEOUT))
                    else
                        data.postValue(TrxResponses(status.toString(), null, t.message.toString()))
                }

                override fun onResponse(call: Call<TrxResponses>, response: Response<TrxResponses>) {
                    status.value = response.body()?.status?.toBoolean()
                    data.value = response.body()
                }
            })
    }

    fun getStatus(): MutableLiveData<Boolean> {
        return status
    }

    fun getData(): MutableLiveData<TrxResponses> {
        return data
    }
}