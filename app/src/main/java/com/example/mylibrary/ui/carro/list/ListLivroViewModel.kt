package com.example.mylibrary.ui.carro.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylibrary.database.Repositorio
import com.example.mylibrary.database.dao.LivroDao
import com.example.mylibrary.model.Livro
import kotlinx.coroutines.launch

class ListLivroViewModel(
    private val livroDao: LivroDao
) : ViewModel() {

    private val _livros = MutableLiveData<List<Livro>>()
    val livros: LiveData<List<Livro>> = _livros

    fun atualizarQuantidade() {
        viewModelScope.launch {
            _livros.value = livroDao.all() //Repositorio.getInstance().all()
        }
    }
}