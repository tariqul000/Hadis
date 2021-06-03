package com.towhid.hadis.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.towhid.hadis.R
import com.towhid.hadis.adapter.RecylerPagingAdapter
import com.towhid.hadis.listener.HadisClickListener
import com.towhid.hadis.viewModel.HadisBookViewModel
import com.towhid.hadis.viewModel.HadisViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_hadis_book.view.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@AndroidEntryPoint
class HadisBookFragment : Fragment() {
    //private val viewModel by viewModels<HadisBookViewModel>()
    private lateinit var viewModel: HadisBookViewModel
    private lateinit var newsPagingAdapter: RecylerPagingAdapter
    private var isAlreadyLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_hadis_book, container, false)
    }

    /* override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

         if (!isAlreadyLoaded) {
             isAlreadyLoaded = true
             viewModel.list.observe(viewLifecycleOwner) {
                 newsPagingAdapter.submitData(lifecycle, it)
             }
             newsPagingAdapter.addLoadStateListener { state ->
                 when (state.refresh) {
                     is LoadState.Loading -> {
                         view.book_progress.visibility = View.VISIBLE
                     }
                     is LoadState.NotLoading -> {
                         view.book_progress.visibility = View.GONE
                     }
                     is LoadState.Error -> {
                         view.book_progress.visibility = View.GONE
                         Toast.makeText(requireContext(), "No Internet", Toast.LENGTH_SHORT).show()
                     }

                 }
             }
             view.rec_hadis_book.adapter = newsPagingAdapter
         }
     }*/
    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!isAlreadyLoaded) {
            isAlreadyLoaded = true
            viewModel = ViewModelProvider(this)[HadisBookViewModel::class.java]
            newsPagingAdapter = RecylerPagingAdapter(viewModel)
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.pager.collectLatest {
                    newsPagingAdapter.submitData(it)
                }
            }

        }
        view.rec_hadis_book.adapter = newsPagingAdapter
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(hadis: HadisClickListener) {
        hadis.hadisBook
        if (hadis.hadisBook.hasBooks) {
            if (hadis.hadisBook.hasChapters) {
                findNavController().navigate(
                    R.id.action_hadisBookFragment_to_hadisChapterFragment,
                    bundleOf("collectionName" to hadis.hadisBook.name)
                )
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