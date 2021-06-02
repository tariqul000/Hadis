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

@HiltViewModel
class HadisBookViewModel @Inject constructor() : ViewModel() {

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

    fun callHadisList(collectionName: String, bookNumber: String): MutableLiveData<Any> {
        val resultLiveData: MutableLiveData<Any> = MutableLiveData()
        val call: Call<HadisListRes> =
            RetrofitClient.getInstance().getApi().HadisListRes_(collectionName, bookNumber)

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

    fun clickHadisBook(hadisBook: HadisBook) {
        EventBus.getDefault().post(HadisClickListener(hadisBook))
    }

}