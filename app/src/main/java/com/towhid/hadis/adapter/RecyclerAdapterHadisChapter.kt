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
import kotlinx.android.synthetic.main.item_hadis_chapter.view.*


class RecyclerAdapterHadisChapter(var mcontext: Context, var mData: MutableList<HadisChapter>) :
    RecyclerView.Adapter<RecyclerAdapterHadisChapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder(
        LayoutInflater.from(mcontext).inflate(R.layout.item_hadis_chapter, parent, false)
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.tv_no.text = mData[position].hadithStartNumber.toString().plus(" - ")
            .plus(mData[position].hadithEndNumber.toString())
        holder.itemView.tv_title_en.text = mData[position].ChapterNameEn
        holder.itemView.tv_title_ar.text = mData[position].ChapterNameAr
        holder.itemView.setOnClickListener {
            val bundle = bundleOf("collectionName" to mData[position].collectionName,"bookNumber" to mData[position].BookNumber)
            Navigation.findNavController(mcontext as Activity, R.id.navHostMainFragment).navigate(R.id.action_hadisChapterFragment_to_hadisListFragment, bundle)
        }
    }

    override fun getItemCount(): Int = mData.size
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}