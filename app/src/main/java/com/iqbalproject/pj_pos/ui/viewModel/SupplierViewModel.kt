package com.iqbalproject.pj_pos.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iqbalproject.pj_pos.model.Suppliers
import com.iqbalproject.pj_pos.network.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SupplierViewModel : ViewModel() {

    fun loadData() : LiveData<Suppliers> {
        val data = MutableLiveData<Suppliers>()

            NetworkConfig().api().getSuppliers().enqueue(object : Callback<Suppliers> {
                override fun onFailure(call: Call<Suppliers>, t: Throwable) {
                    data.value = null
                }

                override fun onResponse(call: Call<Suppliers>, response: Response<Suppliers>) {
                    data.value = response.body()
                }
            })

        return data
    }
}