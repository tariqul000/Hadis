package com.towhid.hadis.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mocat.btb.network.api.RetrofitClient
import com.towhid.hadis.network.model.response.hadis_book.HadisBookRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HadisBookViewModel : ViewModel() {

    fun callHadisBook(): MutableLiveData<Any> {
        var resultLiveData: MutableLiveData<Any> = MutableLiveData()
        val call: Call<HadisBookRes> = RetrofitClient.getInstance().getApi().HadisBookRes_(
        )

        call.enqueue(object : Callback<HadisBookRes> {
            override fun onResponse(call: Call<HadisBookRes>, response: Response<HadisBookRes>) {
                if (response.isSuccessful) {
                    resultLiveData.value = response.body()

                }

            }

            override fun onFailure(call: Call<HadisBookRes>, t: Throwable) {
                resultLiveData.value = t
            }
        })



        return resultLiveData as MutableLiveData<Any>
    }

}