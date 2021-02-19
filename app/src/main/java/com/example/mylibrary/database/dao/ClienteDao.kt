package com.example.mylibrary.database.dao

import androidx.room.*
import com.example.mylibrary.model.Cliente
import com.example.mylibrary.model.ClienteWithLivro

@Dao
interface ClienteDao {

    @Insert
    suspend fun insert(cliente: Cliente)

    @Update
    suspend fun update(cliente: Cliente)

    @Query("SELECT * FROM Cliente WHERE nome = :nome")
    suspend fun find(nome: String): Cliente

    @Query("SELECT * FROM Cliente WHERE id = :id")
    suspend fun find(id: Long): Cliente

    @Delete
    suspend fun delete(cliente: Cliente)

    @Query("SELECT * FROM Cliente")
    suspend fun listAll(): List<Cliente>

    @Transaction
    @Query("SELECT * FROM Livro WHERE id = :id")
    suspend fun findWithLivro(id: Long): ClienteWithLivro
}
