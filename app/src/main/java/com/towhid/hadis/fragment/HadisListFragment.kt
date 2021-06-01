package com.towhid.hadis.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.towhid.hadis.R
private const val COLLECTION_NAME = "collectionName"
private const val BOOK_NUMBER = "bookNumber"
class HadisListFragment : Fragment() {
    private var collectionName: String? = null
    private var bookNumber: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            collectionName = it.getString(COLLECTION_NAME)
            bookNumber = it.getString(BOOK_NUMBER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hadis_list, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(collectionName: String, bookNumber: String) =
            HadisListFragment().apply {
                arguments = Bundle().apply {
                    putString(COLLECTION_NAME, collectionName)
                    putString(BOOK_NUMBER, bookNumber)
                }
            }
    }
}