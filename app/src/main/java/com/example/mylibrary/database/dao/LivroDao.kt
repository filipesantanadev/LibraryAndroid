package com.example.mylibrary.database.dao

import androidx.room.*
import com.example.mylibrary.model.Cliente
import com.example.mylibrary.model.ClienteWithLivro
import com.example.mylibrary.model.Livro

@Dao
interface LivroDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg livro: Livro)

    @Update suspend fun update(livro: Livro)
    @Delete suspend fun delete(livro: Livro)

    @Query("SELECT * FROM Livro WHERE titulo = :titulo")
    suspend fun find(titulo: String): Livro

    @Query("SELECT * FROM Livro WHERE id = :key")
    suspend fun get(key: Long): ClienteWithLivro

    @Query("SELECT * FROM Livro")
    suspend fun all(): List<Livro>
}
