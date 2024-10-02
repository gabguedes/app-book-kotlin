package br.com.fiap

import android.app.Application

class BookApplication : Application() {

    val database by lazy { BookDatabase.getDatabase( this) }
    val repository by lazy { BookRepository( database.bookDao()) }
}