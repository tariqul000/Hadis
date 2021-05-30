package com.towhid.hadis.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.towhid.hadis.R
import com.towhid.hadis.adapter.RecyclerAdapterHadisChapter
import com.towhid.hadis.model.HadisChapter
import com.towhid.hadis.network.model.response.hadis_chapter.HadisChapterRes
import com.towhid.hadis.viewModel.HadisBookViewModel
import kotlinx.android.synthetic.main.fragment_hadis_chapter.view.*

private const val HADIS_NAME = "hadisName"

class HadisChapterFragment : Fragment() {
    private var hadisName: String? = null
    var v: View? = null

    lateinit var hadisChapters: MutableList<HadisChapter>
    lateinit var recyclerAdapterHadisChapter: RecyclerAdapterHadisChapter
    private lateinit var hadisBookViewModel: HadisBookViewModel




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            hadisName = it.getString(HADIS_NAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_hadis_chapter, container, false)
        init(view)
        action(view)
        return view
    }

    private fun init(view: View) {
        v = view
        hadisChapters = mutableListOf<HadisChapter>()
        recyclerAdapterHadisChapter =
            RecyclerAdapterHadisChapter(view.context, hadisChapters)
        hadisBookViewModel = ViewModelProvider(this)[HadisBookViewModel::class.java]

    }

    private fun action(view: View) {

        view.rec_hadis_chapter.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = recyclerAdapterHadisChapter
        }
        chapterLoad()
    }

    private fun chapterLoad() {
        with(hadisBookViewModel) {
            callHadisChapter(hadisName!!).observe(
                activity as LifecycleOwner, { any ->
                    if (any is HadisChapterRes) {
                        any.data.forEach {
                            var enName = ""
                            var arName = ""
                            it.book.forEach {
                                when (it.lang) {
                                    "en" -> enName = it.name
                                    "ar" -> arName = it.name
                                }
                            }
                            hadisChapters.add(
                                HadisChapter(
                                    it.bookNumber,
                                    enName,
                                    arName,
                                    it.hadithStartNumber,
                                    it.hadithEndNumber,
                                    it.numberOfHadith
                                )
                            )
                        }
                        recyclerAdapterHadisChapter.notifyDataSetChanged()
                    }
                })
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(hadisName: String) =
            HadisChapterFragment().apply {
                arguments = Bundle().apply {
                    putString(HADIS_NAME, hadisName)
                }
            }
    }
}