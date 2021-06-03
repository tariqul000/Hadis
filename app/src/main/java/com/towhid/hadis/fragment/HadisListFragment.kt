package com.towhid.hadis.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.towhid.hadis.R
import com.towhid.hadis.adapter.RecyclerAdapterHadisList
import com.towhid.hadis.databinding.FragmentHadisListBinding
import com.towhid.hadis.model.HadisList
import com.towhid.hadis.network.model.response.hadis_list.HadisListRes
import com.towhid.hadis.viewModel.HadisViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val COLLECTION_NAME = "collectionName"
private const val BOOK_NUMBER = "bookNumber"


class HadisListFragment : Fragment() {
    private var collectionName: String? = null
    private var bookNumber: String? = null

    private lateinit var binding: FragmentHadisListBinding
    private lateinit var hadisViewModel: HadisViewModel
    lateinit var recyclerAdapterHadisList: RecyclerAdapterHadisList
    lateinit var linearLayoutManager: LinearLayoutManager
    private var isAlreadyLoaded = false
    private var loading = true
    private var pageNumber = 1
    private var pastVisibleItems = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private var hadisLists = mutableListOf<HadisList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            collectionName = it.getString(COLLECTION_NAME)
            bookNumber = it.getString(BOOK_NUMBER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!isAlreadyLoaded) {
            isAlreadyLoaded = true
            binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_hadis_list, container, false)
            init()
            action()

        }
        return binding.root
    }

    private fun init() {
        hadisViewModel = ViewModelProvider(this)[HadisViewModel::class.java]
        recyclerAdapterHadisList = RecyclerAdapterHadisList(requireContext(), hadisLists)
        linearLayoutManager = LinearLayoutManager(context)
    }

    private fun action() {
        binding.recHadisList.apply {
            layoutManager = linearLayoutManager
            adapter = recyclerAdapterHadisList
        }
        binding.recHadisList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount = linearLayoutManager.getChildCount()
                    totalItemCount = linearLayoutManager.getItemCount()
                    pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition()
                    if (!loading) {
                        if (visibleItemCount + pastVisibleItems >= totalItemCount - 20) {
                            loading = true
                            listLoad()
                        }
                    }
                }
            }
        })
        listLoad()
    }


    private fun listLoad() {
        with(hadisViewModel) {
            callHadisList(collectionName!!, bookNumber!!, pageNumber).observe(
                activity as LifecycleOwner, { any ->
                    if (any is HadisListRes) {
                        any.data.forEach {
                            var enName = ""
                            var arName = ""
                            it.hadith.forEach {
                                when (it.lang) {
                                    "en" -> enName = it.chapterTitle
                                    "ar" -> arName = it.chapterTitle
                                }
                            }
                            hadisLists.add(
                                HadisList(
                                    it.collection,
                                    enName,
                                    arName,
                                    it.hadithNumber
                                )
                            )
                        }
                        recyclerAdapterHadisList.notifyDataSetChanged()
                        pageNumber++
                        loading = false

                    }
                    else if(any is Throwable){
                        loading = false
                    }

                })
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(collectionName: String, bookNumber: String) =
            HadisListFragment().apply {
                arguments = Bundle().apply {
                    putString(COLLECTION_NAME, collectionName)
                    putString(BOOK_NUMBER, bookNumber)
                }
            }
    }
}