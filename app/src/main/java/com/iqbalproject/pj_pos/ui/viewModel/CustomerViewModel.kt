package com.iqbalproject.pj_pos.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iqbalproject.pj_pos.model.Customers
import com.iqbalproject.pj_pos.network.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomerViewModel : ViewModel() {
    private var status = MutableLiveData<Boolean>()
    private var data = MutableLiveData<Customers>()

    init {
        loadData()
    }

    private fun loadData() {
        status.value = true

        NetworkConfig().api().getCustomers().enqueue(object : Callback<Customers> {
            override fun onFailure(call: Call<Customers>, t: Throwable) {
                status.value = false
                data.value = null
            }

            override fun onResponse(call: Call<Customers>, response: Response<Customers>) {
                status.value = response.body()?.status?.toBoolean()
                data.value = response.body()
            }
        })
    }

    fun getData(): MutableLiveData<Customers> {
        return data
    }

    fun getStatus(): MutableLiveData<Boolean> {
        return status
    }
}