package com.towhid.hadis.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.towhid.hadis.listener.HadisClickListener
import com.towhid.hadis.model.HadisBook
import com.towhid.hadis.network.api.RetrofitClient
import com.towhid.hadis.network.model.response.hadis_book.HadisBookRes
import com.towhid.hadis.network.model.response.hadis_chapter.HadisChapterRes
import com.towhid.hadis.network.model.response.hadis_details.HadisDetailRes
import com.towhid.hadis.network.model.response.hadis_list.HadisListRes
import dagger.hilt.android.lifecycle.HiltViewModel
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class HadisViewModel : ViewModel() {

    fun callHadisChapter(collectionName: String,pageNumber: Int): MutableLiveData<Any> {
        val resultLiveData: MutableLiveData<Any> = MutableLiveData()
        val call: Call<HadisChapterRes> =
            RetrofitClient.getInstance().getApi().HadisChapterRes_(collectionName,pageNumber)

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

    fun callHadisList(
        collectionName: String,
        bookNumber: String,
        pageNumber: Int
    ): MutableLiveData<Any> {
        val resultLiveData: MutableLiveData<Any> = MutableLiveData()
        val call: Call<HadisListRes> =
            RetrofitClient.getInstance().getApi()
                .HadisListRes_(collectionName, bookNumber, pageNumber)

        call.enqueue(object : Callback<HadisListRes> {
            override fun onResponse(
                call: Call<HadisListRes>,
                response: Response<HadisListRes>
            ) {
                if (response.isSuccessful) {
                    resultLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<HadisListRes>, t: Throwable) {
                resultLiveData.value = t
            }
        })
        return resultLiveData
    }

    fun callHadisDetail(collectionName: String, hadithNumber: String): MutableLiveData<Any> {
        val resultLiveData: MutableLiveData<Any> = MutableLiveData()
        val call: Call<HadisDetailRes> =
            RetrofitClient.getInstance().getApi().HadisDetailRes_(collectionName, hadithNumber)

        call.enqueue(object : Callback<HadisDetailRes> {
            override fun onResponse(
                call: Call<HadisDetailRes>,
                response: Response<HadisDetailRes>
            ) {
                if (response.isSuccessful) {
                    resultLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<HadisDetailRes>, t: Throwable) {
                resultLiveData.value = t
            }
        })
        return resultLiveData
    }



}