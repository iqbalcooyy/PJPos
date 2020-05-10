package com.iqbalproject.pj_pos.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iqbalproject.pj_pos.model.GetReportResponse
import com.iqbalproject.pj_pos.network.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReportViewModel : ViewModel() {

    fun getReport(startDate: String, endDate: String): LiveData<GetReportResponse> {
        val report = MutableLiveData<GetReportResponse>()

        NetworkConfig().api().getReport(start_date = startDate, end_date = endDate)
            .enqueue(object : Callback<GetReportResponse> {
                override fun onFailure(call: Call<GetReportResponse>, t: Throwable) {
                    report.value = null
                }

                override fun onResponse(
                    call: Call<GetReportResponse>,
                    response: Response<GetReportResponse>
                ) {
                    report.value = response.body()
                }
            })

        return report
    }
}