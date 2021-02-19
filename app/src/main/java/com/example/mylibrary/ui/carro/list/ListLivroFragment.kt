package com.example.mylibrary.ui.carro.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mylibrary.R
import com.example.mylibrary.adapter.RecyclerListLivro
import com.example.mylibrary.database.AppDatabase
import com.example.mylibrary.model.Livro
import com.example.mylibrary.model.LivroUtil
import kotlinx.android.synthetic.main.list_livro_fragment.*

class ListLivroFragment : Fragment() {

    private lateinit var viewModel: ListLivroViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.list_livro_fragment, container, false)

        val appDatabase = AppDatabase.getInstance(requireContext().applicationContext)
        val livroDao = appDatabase.livroDao()
        val listLivroVMF = ListLivroViewModelFactory(livroDao) // livroDao
        viewModel = ViewModelProvider(this, listLivroVMF)
                        .get(ListLivroViewModel::class.java)


        viewModel.livros.observe(viewLifecycleOwner) {
            setupListViewLivros(it)
        }
        viewModel.atualizarQuantidade()

        return view
    }

    private fun setupListViewLivros(livros: List<Livro>) {
        recyclerListLivro.adapter = RecyclerListLivro(livros) {
            LivroUtil.livroSelecionado = it
            findNavController().navigate(R.id.formLivroFragment)
        }
        recyclerListLivro.layoutManager = LinearLayoutManager(requireContext())
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabFormLivro.setOnClickListener {
            LivroUtil.livroSelecionado = null
            findNavController().navigate(R.id.formLivroFragment)
        }
    }
}