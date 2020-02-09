package com.iqbalproject.pj_pos.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iqbalproject.pj_pos.model.SalesResponses
import com.iqbalproject.pj_pos.network.NetworkConfig
import com.iqbalproject.pj_pos.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException

class SalesConfirmViewModel : ViewModel() {

    private var status = MutableLiveData<Boolean>()
    private var data = MutableLiveData<SalesResponses>()

    fun loadData(
        custId: String,
        itemId: List<String>,
        discount: Int,
        totalPay: Int,
        saleQty: List<Int>
    ) {
        status.value = null

        NetworkConfig().api().trx_sales(
            cust_id = custId,
            item_id = itemId,
            discount = discount,
            total_pay = totalPay,
            sale_qty = saleQty
        )
            .enqueue(object : Callback<SalesResponses> {
                override fun onFailure(call: Call<SalesResponses>, t: Throwable) {
                    status.value = false

                    if (t is SocketTimeoutException)
                        data.postValue(SalesResponses(status.toString(), null, Constants.RES_ON_TIMEOUT))
                    else
                        data.postValue(SalesResponses(status.toString(), null, t.message.toString()))
                }

                override fun onResponse(call: Call<SalesResponses>, response: Response<SalesResponses>) {
                    status.value = response.body()?.status?.toBoolean()
                    data.value = response.body()
                }
            })
    }

    fun getStatus(): MutableLiveData<Boolean> {
        return status
    }

    fun getData(): MutableLiveData<SalesResponses> {
        return data
    }
}