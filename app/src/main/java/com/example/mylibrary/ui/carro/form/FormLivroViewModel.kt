package com.example.mylibrary.ui.carro.form

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ImageReader
import android.net.Uri
import android.os.storage.StorageManager
import com.google.firebase.storage.StorageReference
import android.util.Log
import androidx.constraintlayout.solver.Cache
import androidx.core.net.toUri
import androidx.lifecycle.*
import com.example.mylibrary.database.dao.LivroDao
import com.example.mylibrary.model.Livro
import com.example.mylibrary.model.LivroUtil
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class FormLivroViewModel(
    private val livroDao: LivroDao,
    application: Application
) : AndroidViewModel(application) {

    private val app = application

    private val _imagemLivro = MutableLiveData<Uri>()
    val imagemLivro: LiveData<Uri> = _imagemLivro

    // status
    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    // msg
    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg

    init {
        _status.value = false
        _msg.value = null
    }

    fun store(
        titulo: String,
        autor: String,
        editora: String,
        generos: String,
        isbn: String,
        anoLancamento: String
    ) {
        _status.value = false
        viewModelScope.launch {
            try {
                val livro = Livro(titulo, autor, editora, generos, isbn, anoLancamento)
                if (LivroUtil.livroSelecionado != null) {
                    livro.id = LivroUtil.livroSelecionado!!.id
                    livroDao.update(livro)
                } else
                    livroDao.insert(livro)
                uploadImagemLivro(livro.isbn, _imagemLivro.value!!)
                _status.value = true
                _msg.value = "Persistência realizado com sucesso."
            } catch (e: Exception) {
                _msg.value = "Problemas ao persistir os dados."
                Log.i("SQLRoom", "${e.message}")
            }
        }
    }

    fun setImagemLivro(uri: Uri) {
        _imagemLivro.value = uri
    }

    fun uploadImagemLivro(isbn: String, foto: Uri){
        val inputStream = app.contentResolver.openInputStream(foto)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val file = File(app.filesDir, "$isbn.png")
        file.createNewFile()
        val fos = FileOutputStream(file.absoluteFile)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
    }


    /*fun uploadImagemLivro(isbn: String) {
        val fileReference = getFileReference(isbn)
        val task = fileReference.putFile(_imagemLivro!!.value!!)
        task
            .addOnSuccessListener {
                _msg.value = "Imagem Carregada com Sucesso."
            }
            .addOnFailureListener {
                _msg.value = "Falhou: ${it.message}"
            }
    }

    private fun getFileReference(isbn: String): StorageReference {
        val file =
        // FirebaseStorage
        val firebaseStorage = FirebaseStorage.getInstance()

        // Apontar para a raiz -> referencia
        val storageReference = firebaseStorage.reference

        //Apontar para o arquivo que recebera os dados
        // livros/isbn.png
        val fileReference = storageReference
            .child("livros/$isbn.png")
        return fileReference
    }

    fun downloadImageLivro(isbn: String) {
        val file = File.createTempFile("livro", ".png")
        val fileReference = getFileReference(isbn)
        val task = fileReference.getFile(file)
        task
            .addOnSuccessListener {
                _imagemLivro.value = file.toUri()
            }
            .addOnFailureListener {
                _msg.value = "Falhou: ${it.message}"
            }
    }*/
}