package com.towhid.hadis.viewModel

import android.app.Activity
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.mocat.btb.network.api.RetrofitClient
import com.towhid.hadis.R
import com.towhid.hadis.listener.HadisClickListener
import com.towhid.hadis.model.HadisBook
import com.towhid.hadis.network.model.response.hadis_book.HadisBookRes
import com.towhid.hadis.network.model.response.hadis_chapter.HadisChapterRes
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HadisBookViewModel: ViewModel() {

    fun callHadisBook(): MutableLiveData<Any> {
        val resultLiveData: MutableLiveData<Any> = MutableLiveData()
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
        return resultLiveData
    }

    fun callHadisChapter(collectionName: String): MutableLiveData<Any> {
        val resultLiveData: MutableLiveData<Any> = MutableLiveData()
        val call: Call<HadisChapterRes> =
            RetrofitClient.getInstance().getApi().HadisChapterRes_(collectionName)

        call.enqueue(object : Callback<HadisChapterRes> {
            override fun onResponse(
                call: Call<HadisChapterRes>,
                response: Response<HadisChapterRes>
            ) {
                if (response.isSuccessful) {
                    resultLiveData.value = response.body()
                }

            }

            override fun onFailure(call: Call<HadisChapterRes>, t: Throwable) {
                resultLiveData.value = t
            }
        })
        return resultLiveData
    }

    fun clickHadisBook(hadisBook: HadisBook){
        EventBus.getDefault().post(HadisClickListener(hadisBook))
    }

}