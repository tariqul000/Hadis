package com.towhid.hadis.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.towhid.hadis.R
import com.towhid.hadis.network.model.response.hadis_details.HadisDetailRes
import com.towhid.hadis.viewModel.HadisViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_hadis_details.view.*
import javax.inject.Inject

private const val COLLECTION_NAME = "collectionName"
private const val HADITH_NUMBER = "hadithNumber"


class HadisDetailsFragment : Fragment() {
    private var collectionName: String? = null
    private var hadithNumber: String? = null

    @Inject
    lateinit var hadisViewModel: HadisViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            collectionName = it.getString(COLLECTION_NAME)
            hadithNumber = it.getString(HADITH_NUMBER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_hadis_details, container, false)
        init()
        action(view)
        return view
    }

    private fun init() {
        hadisViewModel = ViewModelProvider(this)[HadisViewModel::class.java]
    }

    private fun action(view: View) {
        detailsLoad(view)
    }

    private fun detailsLoad(view: View) {
        with(hadisViewModel) {
            callHadisDetail(collectionName!!, hadithNumber!!).observe(
                activity as LifecycleOwner, { any ->
                    if (any is HadisDetailRes) {
                        any.hadith.forEach {
                            when (it.lang) {
                                "en" -> {
                                    view.tv_title_en.text = HtmlCompat.fromHtml(it.chapterTitle,
                                        HtmlCompat.FROM_HTML_MODE_LEGACY)
                                    view.tv_description_en.text=HtmlCompat.fromHtml(it.body,
                                        HtmlCompat.FROM_HTML_MODE_LEGACY)
                                }
                                "ar" -> {
                                    view.tv_title_ar.text = HtmlCompat.fromHtml(it.chapterTitle,
                                        HtmlCompat.FROM_HTML_MODE_LEGACY)
                                    view.tv_description_ar.text=HtmlCompat.fromHtml(it.body,
                                        HtmlCompat.FROM_HTML_MODE_LEGACY)
                                }
                            }

                        }
                    }
                })
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(collectionName: String, hadithNumber: String) =
            HadisDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(COLLECTION_NAME, collectionName)
                    putString(HADITH_NUMBER, hadithNumber)
                }
            }
    }
}