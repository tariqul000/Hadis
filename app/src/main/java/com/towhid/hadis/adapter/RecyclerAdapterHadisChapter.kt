package com.towhid.hadis.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    }

    override fun getItemCount(): Int = mData.size
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}