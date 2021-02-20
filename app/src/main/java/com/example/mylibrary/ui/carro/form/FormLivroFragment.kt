package com.example.mylibrary.ui.carro.form

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapRegionDecoder
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mylibrary.R
import com.example.mylibrary.database.AppDatabase
import com.example.mylibrary.model.Livro
import com.example.mylibrary.model.LivroUtil
import kotlinx.android.synthetic.main.form_livro_fragment.*
import java.io.File
import java.io.FileOutputStream

class FormLivroFragment : Fragment() {

    private lateinit var formLivroViewModel: FormLivroViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireActivity().application
        val appDatabase = AppDatabase.getInstance(application)
        val livroDao = appDatabase.livroDao()
        val formLivroViewModelFactory = FormLivroViewModelFactory(livroDao, application)

        formLivroViewModel = ViewModelProvider(this, formLivroViewModelFactory)
            .get(FormLivroViewModel::class.java)

        formLivroViewModel.status.observe(viewLifecycleOwner) { status ->
            if (status)
                findNavController().popBackStack()
        }

        formLivroViewModel.msg.observe(viewLifecycleOwner) { msg ->
            if (!msg.isNullOrBlank())
                Toast.makeText(
                    requireContext(),
                    msg,
                    Toast.LENGTH_LONG
                ).show()
        }

        formLivroViewModel.imagemLivro.observe(viewLifecycleOwner) {
            if (it != null)
                imageViewFormLivroFoto.setImageURI(it)
        }

        val view = inflater.inflate(R.layout.form_livro_fragment, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (LivroUtil.livroSelecionado != null)
            preencherFormulario(LivroUtil.livroSelecionado!!)

        btnFormSalvar.setOnClickListener {
            val titulo = editTextFormLivroTitulo.text.toString()
            val autores = editTextFormLivroAutores.text.toString()
            val editora = editTextFormLivroEditora.text.toString()
            val generos = editTextFormLivroGeneros.text.toString()
            val isbn = editTextFormLivroISBN.text.toString()
            val anoLancamento = editTextFormLivroAnoLancamento.text.toString()

            formLivroViewModel.store(titulo, autores, editora, generos, isbn, anoLancamento)
        }

        imageViewFormLivroFoto.setOnClickListener {
            selecionarImagem()
        }
    }

    private fun preencherFormulario(livro: Livro){
        editTextFormLivroTitulo.setText(livro.titulo)
        editTextFormLivroAutores.setText(livro.autor)
        editTextFormLivroEditora.setText(livro.editora)
        editTextFormLivroGeneros.setText(livro.generos)
        editTextFormLivroISBN.setText(livro.isbn)
        editTextFormLivroAnoLancamento.setText(livro.anoLancamento)
        //formLivroViewModel.setImagemLivro()

        btnFormSalvar.text = "Atualizar"
    }

    private fun selecionarImagem() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(intent, 1)
    }

    /*private fun lerConteudoArquivo(foto: Bitmap) {
        val fis = requireActivity().openFileInput(foto.toString())
        val conteudo = fis.bufferedReader().readText()
        imageViewFormLivroFoto.setImageBitmap(foto)
    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val foto: Uri = data!!.data!!
            formLivroViewModel.setImagemLivro(foto)
        }
    }
}