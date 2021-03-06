package com.iqbalproject.pj_pos.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iqbalproject.pj_pos.model.EditResponse
import com.iqbalproject.pj_pos.model.Suppliers
import com.iqbalproject.pj_pos.network.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SupplierViewModel : ViewModel() {

    fun loadData(): LiveData<Suppliers> {
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

    fun addData(
        suppName: String,
        suppAddress: String,
        suppTelp: String
    ): LiveData<EditResponse> {
        val data = MutableLiveData<EditResponse>()

        NetworkConfig().api().addSupplier(
            supplier_name = suppName,
            supplier_address = suppAddress,
            supplier_telp = suppTelp
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
        suppId: String,
        suppName: String,
        suppAddress: String,
        suppTelp: String
    ): LiveData<EditResponse> {
        val data = MutableLiveData<EditResponse>()

        NetworkConfig().api().editSupplier(
            supplier_id = suppId,
            supplier_name = suppName,
            supplier_address = suppAddress,
            supplier_telp = suppTelp
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

    fun deleteData(suppId: String): LiveData<EditResponse> {
        val data = MutableLiveData<EditResponse>()

        NetworkConfig().api().deleteSupplier(supplier_id = suppId)
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