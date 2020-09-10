package com.iqbalproject.pj_pos.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iqbalproject.pj_pos.model.DetailAccReceivRespon
import com.iqbalproject.pj_pos.model.GetAccReceivResponse
import com.iqbalproject.pj_pos.model.TrxAccReceivResponse
import com.iqbalproject.pj_pos.network.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccReceivViewModel : ViewModel() {

    fun loadData(): LiveData<GetAccReceivResponse> {
        val data = MutableLiveData<GetAccReceivResponse>()

        NetworkConfig().api().getAccReceivable().enqueue(object : Callback<GetAccReceivResponse> {
            override fun onFailure(call: Call<GetAccReceivResponse>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(
                call: Call<GetAccReceivResponse>,
                response: Response<GetAccReceivResponse>
            ) {
                data.value = response.body()
            }
        })

        return data
    }

    fun pushData(saleId: String, custId: String, arTotal: Int, user: String): LiveData<TrxAccReceivResponse> {
        val data = MutableLiveData<TrxAccReceivResponse>()

        NetworkConfig().api().trx_acc_receivable(
            sale_id = saleId,
            cust_id = custId,
            ar_total = arTotal,
            user = user
        ).enqueue(object : Callback<TrxAccReceivResponse> {
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

    fun editData(arId: String, arPaid: String, arNotes: String, saleId: String): LiveData<TrxAccReceivResponse> {
        val data = MutableLiveData<TrxAccReceivResponse>()

        NetworkConfig().api().editAccReceivable(
            ar_id = arId,
            ar_paid = arPaid,
            notes = arNotes,
            sale_id = saleId
        ).enqueue(object : Callback<TrxAccReceivResponse> {
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

    fun loadDetailAr(arId: String): LiveData<DetailAccReceivRespon> {
        val data = MutableLiveData<DetailAccReceivRespon>()

        NetworkConfig().api().getDetailAccReceivable(ar_id = arId)
            .enqueue(object : Callback<DetailAccReceivRespon> {
                override fun onFailure(call: Call<DetailAccReceivRespon>, t: Throwable) {
                    data.value = null
                }

                override fun onResponse(
                    call: Call<DetailAccReceivRespon>,
                    response: Response<DetailAccReceivRespon>
                ) {
                    data.value = response.body()
                }

            })

        return data
    }
}