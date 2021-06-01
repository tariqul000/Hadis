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
import com.towhid.hadis.R
import com.towhid.hadis.adapter.RecyclerAdapterHadisChapter
import com.towhid.hadis.databinding.FragmentHadisChapterBinding
import com.towhid.hadis.model.HadisChapter
import com.towhid.hadis.network.model.response.hadis_chapter.HadisChapterRes
import com.towhid.hadis.viewModel.HadisBookViewModel
import kotlinx.android.synthetic.main.fragment_hadis_chapter.view.*

private const val COLLECTION_NAME = "collectionName"

class HadisChapterFragment : Fragment() {
    private var collectionName: String? = null

    private lateinit var binding: FragmentHadisChapterBinding
    private lateinit var hadisBookViewModel: HadisBookViewModel
    lateinit var recyclerAdapterHadisChapter: RecyclerAdapterHadisChapter
    private var isAlreadyLoaded = false

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
        hadisBookViewModel = ViewModelProvider(this)[HadisBookViewModel::class.java]
        recyclerAdapterHadisChapter = RecyclerAdapterHadisChapter(requireContext(), hadisChapters)
    }

    private fun action() {
        binding.recHadisChapter.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapterHadisChapter
        }
        chapterLoad()
    }

    private fun chapterLoad() {
        with(hadisBookViewModel) {
            callHadisChapter(collectionName!!).observe(
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