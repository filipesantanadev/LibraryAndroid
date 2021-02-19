package com.example.mylibrary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Cliente(
    var nome: String,
    var livroId: Long,
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
)