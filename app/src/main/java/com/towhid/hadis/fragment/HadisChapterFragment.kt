package com.towhid.hadis.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.towhid.hadis.R
import com.towhid.hadis.adapter.RecyclerAdapterHadisChapter
import com.towhid.hadis.databinding.FragmentHadisChapterBinding
import com.towhid.hadis.model.HadisChapter
import com.towhid.hadis.network.model.response.hadis_chapter.HadisChapterRes
import com.towhid.hadis.viewModel.HadisViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_hadis_chapter.view.*

private const val COLLECTION_NAME = "collectionName"


class HadisChapterFragment : Fragment() {
    private var collectionName: String? = null

    private lateinit var binding: FragmentHadisChapterBinding
    private lateinit var hadisViewModel: HadisViewModel
    lateinit var recyclerAdapterHadisChapter: RecyclerAdapterHadisChapter
    lateinit var linearLayoutManager: LinearLayoutManager
    private var isAlreadyLoaded = false
    private var loading = true
    private var pageNumber = 1
    private var pastVisibleItems = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private var hadisChapters = mutableListOf<HadisChapter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            collectionName = it.getString(COLLECTION_NAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!isAlreadyLoaded) {
            isAlreadyLoaded = true
            binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_hadis_chapter, container, false)
            init()
            action()

        }
        return binding.root
    }

    private fun init() {
        hadisViewModel = ViewModelProvider(this)[HadisViewModel::class.java]
        recyclerAdapterHadisChapter = RecyclerAdapterHadisChapter(requireContext(), hadisChapters)
        linearLayoutManager = LinearLayoutManager(context)
    }

    private fun action() {
        binding.recHadisChapter.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapterHadisChapter
        }
        binding.recHadisChapter.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount = linearLayoutManager.getChildCount()
                    totalItemCount = linearLayoutManager.getItemCount()
                    pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition()
                    if (!loading) {
                        if (visibleItemCount + pastVisibleItems >= totalItemCount - 20) {
                            loading = true
                            chapterLoad()
                        }
                    }
                }
            }
        })
        chapterLoad()
    }

    private fun chapterLoad() {
        with(hadisViewModel) {
            callHadisChapter(collectionName!!,pageNumber).observe(
                activity as LifecycleOwner, { any ->
                    if (any is HadisChapterRes) {
                        any.data.forEach {
                            var enName = ""
                            var arName = ""
                            it.book.forEach {
                                when (it.lang) {
                                    "en" -> enName = it.name
                                    "ar" -> arName = it.name
                                }
                            }
                            hadisChapters.add(
                                HadisChapter(
                                    collectionName!!,
                                    it.bookNumber,
                                    enName,
                                    arName,
                                    it.hadithStartNumber,
                                    it.hadithEndNumber,
                                    it.numberOfHadith
                                )
                            )
                        }
                        recyclerAdapterHadisChapter.notifyDataSetChanged()
                        pageNumber++
                        loading = false
                    } else if (any is Throwable) {
                        loading = false
                    }
                })
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(collectionName: String) =
            HadisChapterFragment().apply {
                arguments = Bundle().apply {
                    putString(COLLECTION_NAME, collectionName)
                }
            }
    }
}