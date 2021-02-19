package com.example.mylibrary.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mylibrary.database.dao.ClienteDao
import com.example.mylibrary.database.dao.LivroDao
import com.example.mylibrary.model.Cliente
import com.example.mylibrary.model.Livro

@Database(
    entities = [
        Livro::class,
        Cliente::class
    ], version = 1 )
abstract class AppDatabase : RoomDatabase() {

    abstract fun livroDao(): LivroDao
    abstract fun getClientDao(): ClienteDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room
                    .databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "database.db"
                    )
                    .build()
            }
            return INSTANCE!!
        }
    }
}