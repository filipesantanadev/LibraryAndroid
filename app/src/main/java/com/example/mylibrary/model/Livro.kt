package com.example.mylibrary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Livro(
    val titulo: String,
    val autor: String,
    val editora: String,
    val generos: String,
    val isbn: String,
    val anoLancamento: String,

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
)