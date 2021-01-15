package com.example.myapplication.Director.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.Director.DirectorViewModel
import com.example.myapplication.R

class DirectoresFragment : Fragment() {

    companion object {
        fun newInstance() = DirectoresFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var Ndirectorviewmodel: DirectorViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Ndirectorviewmodel = ViewModelProvider(this).get(DirectorViewModel::class.java)
        return inflater.inflate(R.layout.main_fragment, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}