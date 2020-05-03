package com.iqbalproject.pj_pos.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iqbalproject.pj_pos.model.TrxAccReceivResponse
import com.iqbalproject.pj_pos.network.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccReceivViewModel : ViewModel() {

    fun pushData(saleId: String, custId: String, arTotal: Int): LiveData<TrxAccReceivResponse> {
        val data = MutableLiveData<TrxAccReceivResponse>()

        NetworkConfig().api().trx_acc_receivable(
            sale_id = saleId,
            cust_id = custId,
            ar_total = arTotal).enqueue(object : Callback<TrxAccReceivResponse> {
            override fun onFailure(call: Call<TrxAccReceivResponse>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(
                call: Call<TrxAccReceivResponse>,
                response: Response<TrxAccReceivResponse>
            ) {
                data.value = response.body()
            }
        })

        return data
    }
}