package com.example.mylibrary.ui.carro.show

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mylibrary.R

class ShowLivroFragment : Fragment() {

    private lateinit var viewModel: ShowLivroViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.show_livro_fragment, container, false)

        viewModel = ViewModelProvider(this).get(ShowLivroViewModel::class.java)

        //viewModel.livro

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ShowLivroViewModel::class.java)
        // TODO: Use the ViewModel
    }

}