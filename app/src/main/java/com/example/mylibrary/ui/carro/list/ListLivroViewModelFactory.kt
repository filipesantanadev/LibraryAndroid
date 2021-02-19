package com.example.mylibrary.ui.carro.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mylibrary.database.dao.LivroDao
import java.lang.IllegalArgumentException

class ListLivroViewModelFactory(
    private val livroDao: LivroDao,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListLivroViewModel::class.java))
            return ListLivroViewModel(livroDao) as T
        throw IllegalArgumentException("Classe ViewModel deve ser ListLivroViewModel")
    }
}
