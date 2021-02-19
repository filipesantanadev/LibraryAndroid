package com.example.mylibrary.model

import androidx.room.Embedded
import androidx.room.Relation

class ClienteWithLivro(
    @Embedded val livro: Livro,
    @Relation(
            parentColumn = "id",
            entityColumn = "livroId"
    )
    val cliente: Cliente
)