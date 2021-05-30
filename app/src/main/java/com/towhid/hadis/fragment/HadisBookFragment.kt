package com.towhid.hadis.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.towhid.hadis.R
import com.towhid.hadis.adapter.RecyclerAdapterHadisBook
import com.towhid.hadis.databinding.FragmentHadisBookBinding
import com.towhid.hadis.model.HadisBook
import com.towhid.hadis.network.model.response.hadis_book.HadisBookRes
import com.towhid.hadis.viewModel.HadisBookViewModel


class HadisBookFragment : Fragment() {

    private lateinit var binding: FragmentHadisBookBinding
    private lateinit var hadisBookViewModel: HadisBookViewModel
    private lateinit var recyclerAdapterHadisBook: RecyclerAdapterHadisBook

    private var data = mutableListOf<HadisBook>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hadis_book, container, false)
        hadisBookViewModel = ViewModelProvider(this)[HadisBookViewModel::class.java]
        recyclerAdapterHadisBook = RecyclerAdapterHadisBook(data)

        binding.recHadisBook.layoutManager = LinearLayoutManager(context)
        binding.recHadisBook.adapter = recyclerAdapterHadisBook


        with(hadisBookViewModel) {
            callHadisBook().observe(
                activity as LifecycleOwner, { any ->
                    if (any is HadisBookRes) {
                        var no = 0
                        any.data.forEach { it ->
                            var enName = ""
                            var arName = ""
                            it.collection.forEach {
                                when (it.lang) {
                                    "en" -> enName = it.title
                                    "ar" -> arName = it.title
                                }
                            }
                            data.add(
                                HadisBook(
                                    ++no,
                                    it.name,
                                    it.hasBooks,
                                    it.hasChapters,
                                    enName,
                                    arName,
                                    it.totalHadith,
                                    it.totalAvailableHadith
                                )
                            )
                        }
                        recyclerAdapterHadisBook.notifyDataSetChanged()
                    }
                })
        }


        return binding.root
    }


}