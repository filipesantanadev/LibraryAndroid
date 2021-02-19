@file:Suppress("DEPRECATION")

package com.example.mylibrary

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mylibrary.database.AppDatabase
import com.example.mylibrary.database.dao.ClienteDao
import com.example.mylibrary.model.Cliente
import com.example.mylibrary.model.Livro
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ClienteDatabaseTest {
    private lateinit var appDatabase: AppDatabase   // Conecta com a base
    private lateinit var clienteDao: ClienteDao
    private val cliente = Cliente("Santana", -1)

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room
            .inMemoryDatabaseBuilder(
                context,
                AppDatabase::class.java
            ).allowMainThreadQueries()
            .build()
        clienteDao = appDatabase.getClientDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        appDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun inserirEConsultarPorNomeCliente() = runBlocking {
        clienteDao.insert(cliente)
        val clienteResult = clienteDao.find("Santana")
        assertEquals(cliente.nome, clienteResult.nome)
    }

    @Test
    @Throws(Exception::class)
    fun inserirEConsultarPorIdCliente() = runBlocking {
        clienteDao.insert(cliente)
        val clienteResult = clienteDao.find(1)
        assertEquals(cliente.nome, clienteResult.nome)
    }

    @Test
    @Throws(Exception::class)
    fun atualizarEConsultarCliente() = runBlocking {
        clienteDao.insert(cliente)
        cliente.nome = "Filipe"
        cliente.id = 1
        clienteDao.update(cliente)
        val clienteResult = clienteDao.find(id = 1)
        assertEquals(cliente.nome, clienteResult.nome)
    }

    @Test
    @Throws(Exception::class)
    fun excluirCliente() = runBlocking {
        clienteDao.insert(cliente)
        cliente.id = 1
        clienteDao.delete(cliente)
        val clienteResult = clienteDao.find(id = 1)
        assertNull(clienteResult)
    }

    @Test
    @Throws(Exception::class)
    fun listarClientes() = runBlocking {
        clienteDao.insert(cliente)
        clienteDao.insert(Cliente("José", -1))
        clienteDao.insert(Cliente("Maria", -1))
        val clientes = clienteDao.listAll()
        assertEquals(3, clientes.size)
    }

    @Test
    @Throws(Exception::class)
    fun verificarLivroDoCliente() = runBlocking {
        val livro = Livro(
            titulo = "Dom Casmurro",
            autor = "Machado de Assis",
            editora = "Principis",
            generos = "Realismo Literário",
            isbn = "978-8594318602",
            anoLancamento = "2019"
        )
        appDatabase.livroDao().insert(livro)
        cliente.livroId = 1
        clienteDao.insert(cliente)
        val clienteResult = clienteDao.findWithLivro(1)
        assertEquals(livro.titulo, clienteResult.livro.titulo)
    }
}