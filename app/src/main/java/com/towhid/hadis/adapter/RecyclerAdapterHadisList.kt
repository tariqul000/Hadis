package com.towhid.hadis.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.towhid.hadis.R
import com.towhid.hadis.model.HadisChapter
import com.towhid.hadis.model.HadisList
import kotlinx.android.synthetic.main.item_hadis_chapter.view.*


class RecyclerAdapterHadisList(var mcontext: Context, var mData: MutableList<HadisList>) :
    RecyclerView.Adapter<RecyclerAdapterHadisList.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder(
        LayoutInflater.from(mcontext).inflate(R.layout.item_hadis_list, parent, false)
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.tv_no.text = mData[position].hadithNumber
        holder.itemView.tv_title_en.text = mData[position].HadisTitleEn
        holder.itemView.tv_title_ar.text = mData[position].HadisTitleAr
        holder.itemView.setOnClickListener {
            val bundle = bundleOf("collectionName" to mData[position].collection,"hadithNumber" to mData[position].hadithNumber)
            Navigation.findNavController(mcontext as Activity, R.id.navHostMainFragment).navigate(R.id.action_hadisListFragment_to_hadisDetailsFragment, bundle)
        }
    }

    override fun getItemCount(): Int = mData.size
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}