package com.towhid.hadis.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.towhid.hadis.databinding.ItemHadisBookBinding
import com.towhid.hadis.model.HadisBook
import com.towhid.hadis.network.model.response.hadis_book.Datum
import com.towhid.hadis.viewModel.HadisBookViewModel

class RecylerPagingAdapter(val hadisBookViewModel: HadisBookViewModel) :
    PagingDataAdapter<Datum, RecylerPagingAdapter.MyViewHolder>(DIFF_UTIL) {


    companion object {
        var DIFF_UTIL = object : DiffUtil.ItemCallback<Datum>() {
            override fun areItemsTheSame(oldItem: Datum, newItem: Datum): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Datum, newItem: Datum): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }

    inner class MyViewHolder(val viewDataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)

    override fun onBindViewHolder(holder: RecylerPagingAdapter.MyViewHolder, position: Int) {
        Log.d("pos", "pos : " + position)
        val item = getItem(position)
        var enName = ""
        var arName = ""
        item!!.collection.forEach {
            when (it.lang) {
                "en" -> enName = it.title
                "ar" -> arName = it.title
            }
        }
        val hadisBook = HadisBook(
            position + 1,
            item.name,
            item.hasBooks,
            item.hasChapters,
            enName,
            arName,
            item.totalHadith,
            item.totalAvailableHadith
        )
        holder.viewDataBinding.setVariable(BR.itemlist, hadisBook)
        holder.viewDataBinding.setVariable(BR.viewModel, hadisBookViewModel)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyViewHolder(
            ItemHadisBookBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
}