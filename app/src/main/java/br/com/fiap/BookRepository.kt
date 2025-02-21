package br.com.fiap

import androidx.lifecycle.LiveData
import br.com.fiap.dao.BookDao

class BookRepository(private val bookDao: BookDao) {

    val allBooks: LiveData<List<Book>> = bookDao.getAllBooks()

    suspend fun insert(book: Book){
        bookDao.insert(book)
    }

    suspend fun update(book: Book){
        bookDao.update(book)
    }

    suspend fun delete(book: Book){
        bookDao.delete(book)
    }

}