package com.example.mylibrary.adapter

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mylibrary.R
import com.example.mylibrary.model.Livro
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.recycler_list_livro.view.*

class RecyclerListLivro(
    private val livros: List<Livro>,
    private val actionClick: (Livro) -> Unit
) : RecyclerView.Adapter<RecyclerListLivro.LivroViewHolder>() {

    class LivroViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {
        val textTitulo: TextView = itemView.textViewListLivroTitulo
        val textAutor: TextView = itemView.textViewListLivroAutor
        val textGenero: TextView = itemView.textViewListLivroGenero
        val imageFoto: ImageView = itemView.imageViewListLivroFoto
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivroViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recycler_list_livro,
                parent, false
            )
        return LivroViewHolder(view)
    }

    override fun onBindViewHolder(holder: LivroViewHolder, position: Int) {
        val livro = livros[position]
        holder.textTitulo.text = livro.titulo
        holder.textAutor.text = livro.autor
        holder.textGenero.text = livro.generos
        downloadImageLivro(livro.isbn, holder.imageFoto)

        holder.itemView.setOnClickListener {
            actionClick(livro)
        }
    }

    override fun getItemCount(): Int = livros.size

    private fun downloadImageLivro(
        isbn: String, imageView: ImageView) {
        val firebaseStorage = FirebaseStorage.getInstance()
        val storageReference = firebaseStorage.reference
        val fileReference = storageReference
            .child("livros/$isbn.png")
        val task = fileReference.getBytes(1024*1024)
        task
            .addOnSuccessListener {
                val bitmap = BitmapFactory
                    .decodeByteArray(it, 0, it.size)
                imageView.setImageBitmap(bitmap)
            }
            .addOnFailureListener{
                Log.i("RecyclerListLivro", "${it.message}")
            }
    }

}