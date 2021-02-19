package com.example.mylibrary

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mylibrary.database.AppDatabase
import com.example.mylibrary.database.dao.LivroDao
import com.example.mylibrary.model.Livro
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class LivroDatabaseTest {
    private lateinit var appDatabase: AppDatabase       // Conecta com a base
    private lateinit var livroDao: LivroDao
    private val livro = Livro(
        titulo = "Dom Casmurro",
        autor = "Machado de Assis",
        editora = "Principis",
        generos = "Realismo Literário",
        isbn = "978-8594318602",
        anoLancamento = "2019",
        -1)

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room
            .inMemoryDatabaseBuilder(
                context,
                AppDatabase::class.java
            ).allowMainThreadQueries()
            .build()
        livroDao = appDatabase.livroDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        appDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun inserirEConsultarPorNomeLivro() = runBlocking {
        livroDao.insert(livro)
        val livroResult = livroDao.find("Dom Casmurro")
        assertEquals(livro.titulo, livroResult.titulo)
    }
}