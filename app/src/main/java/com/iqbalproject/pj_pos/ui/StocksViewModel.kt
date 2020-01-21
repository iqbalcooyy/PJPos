package com.iqbalproject.pj_pos.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iqbalproject.pj_pos.model.Stocks
import com.iqbalproject.pj_pos.network.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StocksViewModel : ViewModel() {

    private var status = MutableLiveData<Boolean>()
    private var data = MutableLiveData<Stocks>()

    init {
        loadData()
    }

    private fun loadData() {
        status.value = true

        NetworkConfig().api().getStocks().enqueue(object : Callback<Stocks> {
            override fun onFailure(call: Call<Stocks>, t: Throwable) {
                status.value = false
                data.value = null
            }

            override fun onResponse(call: Call<Stocks>, response: Response<Stocks>) {
                status.value = true

                if (response.isSuccessful == false || response.body()?.stocks.isNullOrEmpty()) {
                    status.value = false
                } else {
                    data.value = response.body()
                }
            }
        })
    }

    fun getData(): MutableLiveData<Stocks>{
        return data
    }

    fun getStatus(): MutableLiveData<Boolean>{
        return status
    }
}