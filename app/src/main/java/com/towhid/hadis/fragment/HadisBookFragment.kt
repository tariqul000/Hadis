package com.towhid.hadis.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.towhid.hadis.R
import com.towhid.hadis.adapter.RecyclerAdapterHadisBook
import com.towhid.hadis.databinding.FragmentHadisBookBinding
import com.towhid.hadis.listener.HadisClickListener
import com.towhid.hadis.model.HadisBook
import com.towhid.hadis.network.model.response.hadis_book.HadisBookRes
import com.towhid.hadis.viewModel.HadisBookViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class HadisBookFragment : Fragment() {

    private lateinit var binding: FragmentHadisBookBinding
    private lateinit var hadisBookViewModel: HadisBookViewModel
    private lateinit var recyclerAdapterHadisBook: RecyclerAdapterHadisBook
    private var isAlreadyLoaded = false

    private var data = mutableListOf<HadisBook>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!isAlreadyLoaded) {
            isAlreadyLoaded = true
            binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_hadis_book, container, false)
            init()
            action()
        }
        return binding.root
    }

    private fun init() {
        hadisBookViewModel = ViewModelProvider(this)[HadisBookViewModel::class.java]
        recyclerAdapterHadisBook = RecyclerAdapterHadisBook(hadisBookViewModel, data)


    }

    private fun action() {
        binding.recHadisBook.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapterHadisBook
        }
        loadBooks()
    }

    private fun loadBooks() {
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
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun hadisClickResponse(hadis: HadisClickListener) {
        hadis.hadisBook
        if (hadis.hadisBook.hasBooks) {
            if (hadis.hadisBook.hasChapters) {
                val bundle = bundleOf("collectionName" to hadis.hadisBook.name)
                Navigation.findNavController(this.context as Activity, R.id.navHostMainFragment)
                    .navigate(R.id.action_hadisBookFragment_to_hadisChapterFragment, bundle)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

}