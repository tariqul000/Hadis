package com.towhid.hadis.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.towhid.hadis.R
import com.towhid.hadis.databinding.ItemHadisBookBinding
import com.towhid.hadis.model.HadisBook


class RecyclerAdapterHadisBook(private var context: Context,private var data: List<HadisBook>) :
    RecyclerView.Adapter<RecyclerAdapterHadisBook.MyViewHolder>() {

    inner class MyViewHolder(var binding: ItemHadisBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HadisBook) {
            binding.itemlist = item
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val listItemBinding = ItemHadisBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            val bundle = bundleOf("hadisName" to "bukhari")
            Navigation.findNavController(context as Activity, R.id.navHostMainFragment).navigate(R.id.action_hadisBookFragment_to_hadisChapterFragment, bundle)
        }
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}