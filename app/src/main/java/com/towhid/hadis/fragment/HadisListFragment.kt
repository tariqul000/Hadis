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
import com.towhid.hadis.adapter.RecyclerAdapterHadisList
import com.towhid.hadis.databinding.FragmentHadisListBinding
import com.towhid.hadis.model.HadisChapter
import com.towhid.hadis.model.HadisList
import com.towhid.hadis.network.model.response.hadis_chapter.HadisChapterRes
import com.towhid.hadis.network.model.response.hadis_list.HadisListRes
import com.towhid.hadis.viewModel.HadisBookViewModel
import kotlinx.android.synthetic.main.fragment_hadis_chapter.view.*
import kotlinx.android.synthetic.main.fragment_hadis_list.view.*

private const val COLLECTION_NAME = "collectionName"
private const val BOOK_NUMBER = "bookNumber"

class HadisListFragment : Fragment() {
    private var collectionName: String? = null
    private var bookNumber: String? = null

    private lateinit var binding: FragmentHadisListBinding
    private lateinit var hadisBookViewModel: HadisBookViewModel
    lateinit var recyclerAdapterHadisList: RecyclerAdapterHadisList
    private var isAlreadyLoaded = false

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
        hadisBookViewModel = ViewModelProvider(this)[HadisBookViewModel::class.java]
        recyclerAdapterHadisList = RecyclerAdapterHadisList(requireContext(), hadisLists)
    }

    private fun action() {
        binding.recHadisList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapterHadisList
        }

        listLoad()
    }

    private fun listLoad() {
        with(hadisBookViewModel) {
            callHadisList(collectionName!!, bookNumber!!).observe(
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