package com.iqbalproject.pj_pos.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iqbalproject.pj_pos.model.LoginResponse
import com.iqbalproject.pj_pos.network.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException

class LoginViewModel : ViewModel() {

    private var status = MutableLiveData<Boolean>()
    private var data = MutableLiveData<LoginResponse>()

    fun loadData(userId: String, pass: String) {

        NetworkConfig().api().login(userId, pass).enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                status.value = false

                if (t is SocketTimeoutException)
                    data.postValue(LoginResponse(status.toString(), "Request Timeout to Server, Please Check Your Connection!", null))
                else
                    data.postValue(LoginResponse(status.toString(), t.message.toString(), null))
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                status.value = true
                data.value = response.body()
            }

        })
    }

    fun getStatus(): MutableLiveData<Boolean> {
        return status
    }

    fun getData(): MutableLiveData<LoginResponse>{
        return data
    }
}