package com.towhid.hadis.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.towhid.hadis.databinding.ItemHadisBookBinding
import com.towhid.hadis.model.HadisBook
import com.towhid.hadis.viewModel.HadisBookViewModel


class RecyclerAdapterHadisBook(
    private var viewModel: HadisBookViewModel,
    private var data: List<HadisBook>
) :
    RecyclerView.Adapter<RecyclerAdapterHadisBook.MyViewHolder>() {

    inner class MyViewHolder(var binding: ItemHadisBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HadisBook) {
            binding.itemlist = item
            binding.viewModel=viewModel
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val listItemBinding = ItemHadisBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}