package com.iqbalproject.pj_pos.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iqbalproject.pj_pos.model.LoginResponse
import com.iqbalproject.pj_pos.network.NetworkConfig
import com.iqbalproject.pj_pos.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException

class LoginViewModel : ViewModel() {

    fun loadData(userId: String, pass: String): LiveData<LoginResponse> {
        var status = MutableLiveData<Boolean>()
        var data = MutableLiveData<LoginResponse>()

        NetworkConfig().api().login(username = userId, password = pass).enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                status.value = false

                if (t is SocketTimeoutException)
                    data.postValue(LoginResponse(status.toString(), Constants.RES_ON_TIMEOUT, null))
                else
                    data.postValue(LoginResponse(status.toString(), t.message.toString(), null))
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                status.value = response.body()?.status?.toBoolean()
                data.value = response.body()
            }
        })
        return data
    }
}