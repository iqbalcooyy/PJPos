package com.iqbalproject.pj_pos.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iqbalproject.pj_pos.model.Customers
import com.iqbalproject.pj_pos.network.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomerViewModel : ViewModel() {

    fun loadData(): LiveData<Customers> {
        val data = MutableLiveData<Customers>()

        NetworkConfig().api().getCustomers().enqueue(object : Callback<Customers> {
            override fun onFailure(call: Call<Customers>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(call: Call<Customers>, response: Response<Customers>) {
                data.value = response.body()
            }
        })

        return data
    }
}