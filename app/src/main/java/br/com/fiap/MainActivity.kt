package br.com.fiap

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.fiap.databinding.ActivityMainBinding
import br.com.fiap.databinding.DialogEditBookBinding
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            (application as
                    BookApplication).repository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpLogo()
        setUpListeners()
        setUpRecyclerView()
    }

    private fun setUpListeners() {
        binding.buttonAddBook.setOnClickListener {
            val bookTitle = binding.editTextBookTitle.text.toString()
            val bookAuthor =
                binding.editTextBookAuthor.text.toString()
            val bookImageUrl = binding.editTextBookImageUrl.text.toString()
            if (bookTitle.isNotBlank() && bookAuthor.isNotBlank() && bookImageUrl.isNotBlank()) {
                mainViewModel.insert(
                    Book(
                        titulo = bookTitle,
                        autor = bookAuthor,
                        urlImagem = bookImageUrl
                    )
                )
                binding.editTextBookTitle.text.clear()
                binding.editTextBookAuthor.text.clear()
                binding.editTextBookImageUrl.text.clear()
                binding.textViewErrorTitle.text = ""
                binding.textViewErrorAuthor.text = ""
                binding.textViewErrorImageUrl.text = ""
                binding.editTextBookTitle.requestFocus()
            }else{
                if(bookTitle.isBlank()) binding.textViewErrorTitle.text = "Campo Requerido"
                if(bookAuthor.isBlank()) binding.textViewErrorAuthor.text = "Campo Requerido"
                if(bookImageUrl.isBlank()) binding.textViewErrorImageUrl.text = "Campo Requerido"
            }
        }
    }

    private fun setUpRecyclerView() {
        val adapter = MainListAdapter(
            onEditClick = { book ->
                showEditDialog(book)
            },
            onDeleteClick = { book -> mainViewModel.delete(book) }
        )
        binding.recyclerViewBooks.adapter = adapter
        binding.recyclerViewBooks.layoutManager = LinearLayoutManager(this)
        // Adicionar Divider
        val dividerItemDecoration = DividerItemDecoration(
            binding.recyclerViewBooks.context,
            (binding.recyclerViewBooks.layoutManager as
                    LinearLayoutManager).orientation
        )
        binding.recyclerViewBooks.addItemDecoration(dividerItemDecoration)
        mainViewModel.allBooks.observe(this) { books ->
            books?.let { adapter.setBooks(it) }
        }
    }

    private fun showEditDialog(book: Book) {
        val dialogBinding = DialogEditBookBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogBinding.root)
        // Preenche os campos de texto com os dados do jogo atual
        dialogBinding.editTextBookTitle.setText(book.titulo)
        dialogBinding.editTextBookAuthor.setText(book.autor)
        dialogBinding.editTextBookImageUrl.setText(book.urlImagem)
        builder.setTitle("Editar Livro")
        builder.setPositiveButton("Salvar") { _, _ ->
            val updatedBook = book.copy(
                titulo = dialogBinding.editTextBookTitle.text.toString(),
                autor = dialogBinding.editTextBookAuthor.text.toString(),
                urlImagem = dialogBinding.editTextBookImageUrl.text.toString()
            )
            mainViewModel.update(updatedBook)
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    private fun setUpLogo() {
        Glide
            .with(this)
            .load("https://static.vecteezy.com/system/resources/thumbnails/011/660/026/small_2x/book-hand-drawn-sketch-png.png")
            .into(binding.imageLogo)
    }
}