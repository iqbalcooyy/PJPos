package com.iqbalproject.pj_pos.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iqbalproject.pj_pos.model.HistoryPurchaseDetailResponse
import com.iqbalproject.pj_pos.model.HistoryPurchaseResponse
import com.iqbalproject.pj_pos.model.HistorySalesDetailResponse
import com.iqbalproject.pj_pos.model.HistorySalesResponse
import com.iqbalproject.pj_pos.network.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryViewModel : ViewModel() {

    fun loadHistorySales(): LiveData<HistorySalesResponse> {
        val data = MutableLiveData<HistorySalesResponse>()

        NetworkConfig().api().getHistorySales().enqueue(object : Callback<HistorySalesResponse> {
            override fun onFailure(call: Call<HistorySalesResponse>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(
                call: Call<HistorySalesResponse>,
                response: Response<HistorySalesResponse>
            ) {
                if (!response.isSuccessful || response.body()?.rownum_history == 0 || response.body()?.result_history.isNullOrEmpty())
                    data.value = null
                else
                    data.value = response.body()
            }
        })

        return data
    }

    fun loadHistorySalesDetail(saleId: String): LiveData<HistorySalesDetailResponse> {
        val data = MutableLiveData<HistorySalesDetailResponse>()

        NetworkConfig().api().getHistorySalesDetail(sale_id = saleId).enqueue(object : Callback<HistorySalesDetailResponse>{
            override fun onFailure(call: Call<HistorySalesDetailResponse>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(
                call: Call<HistorySalesDetailResponse>,
                response: Response<HistorySalesDetailResponse>
            ) {
                if (!response.isSuccessful || response.body()?.rownum_history_detail == 0 || response.body()?.result_history_detail.isNullOrEmpty())
                    data.value = null
                else
                    data.value = response.body()
            }
        })

        return data
    }

    fun loadHistoryPurchase(): LiveData<HistoryPurchaseResponse> {
        val data = MutableLiveData<HistoryPurchaseResponse>()

        NetworkConfig().api().getHistoryPurchase().enqueue(object : Callback<HistoryPurchaseResponse>{
            override fun onFailure(call: Call<HistoryPurchaseResponse>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(
                call: Call<HistoryPurchaseResponse>,
                response: Response<HistoryPurchaseResponse>
            ) {
                if (!response.isSuccessful || response.body()?.rownum_history == 0 || response.body()?.result_history.isNullOrEmpty())
                    data.value = null
                else
                    data.value = response.body()
            }

        })

        return data
    }

    fun loadHistoryPurchaseDetail(purchaseId: String): LiveData<HistoryPurchaseDetailResponse> {
        val data = MutableLiveData<HistoryPurchaseDetailResponse>()

        NetworkConfig().api().getHistoryPurchaseDetail(purchase_id = purchaseId).enqueue(object : Callback<HistoryPurchaseDetailResponse>{
            override fun onFailure(call: Call<HistoryPurchaseDetailResponse>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(
                call: Call<HistoryPurchaseDetailResponse>,
                response: Response<HistoryPurchaseDetailResponse>
            ) {
                if (!response.isSuccessful || response.body()?.rownum_history_detail == 0 || response.body()?.result_history_detail.isNullOrEmpty())
                    data.value = null
                else
                    data.value = response.body()
            }
        })

        return data
    }
}