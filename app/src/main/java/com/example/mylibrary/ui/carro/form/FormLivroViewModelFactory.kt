package com.example.mylibrary.ui.carro.form

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mylibrary.database.dao.LivroDao
import java.lang.IllegalArgumentException

class FormLivroViewModelFactory(
    private val livroDao: LivroDao,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FormLivroViewModel::class.java))
            return FormLivroViewModel(livroDao, application) as T
        throw IllegalArgumentException("Classe ViewModel desconhecida")
    }

}
