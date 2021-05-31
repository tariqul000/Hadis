package com.towhid.hadis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.towhid.hadis.fragment.HadisBookFragment
import com.towhid.hadis.fragment.HadisBookFragmentDirections
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            subtitle = getString(R.string.app_name)
        }
        navController = Navigation.findNavController(this, R.id.navHostMainFragment)
        toolbar.setupWithNavController(navController, null)
        check.setOnClickListener {
            val action = HadisBookFragmentDirections.actionHadisBookFragmentToHadisChapterFragment()
            val bundle = bundleOf("hadisName" to "bukhari")
            navController.navigate(R.id.action_hadisBookFragment_to_hadisChapterFragment, bundle)
        }
    }

    fun hello(view: View, a: Int) {}

}