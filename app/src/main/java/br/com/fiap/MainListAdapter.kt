package br.com.fiap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.databinding.BookItemBinding
import com.bumptech.glide.Glide

class MainListAdapter(
    private val onEditClick: (Book) -> Unit,
    private val onDeleteClick: (Book) -> Unit
) : RecyclerView.Adapter<MainListAdapter.GameViewHolder>() {
    private var books: List<Book> = emptyList()

    class GameViewHolder(val binding: BookItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            GameViewHolder {
        val binding =
            BookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val currentBook = books[position]
        holder.binding.textViewBookTitle.text = currentBook.titulo
        holder.binding.textViewBookAuthor.text = currentBook.autor
        Glide
            .with(holder.itemView.context)
            .load(currentBook.urlImagem)
            .error(R.drawable.baseline_image_not_supported_24)
            .into(holder.binding.imageViewBookImage)
        holder.binding.buttonEdit.setOnClickListener {
            onEditClick(currentBook)
        }
        holder.binding.buttonDelete.setOnClickListener {
            onDeleteClick(currentBook)
        }
    }

    override fun getItemCount() = books.size
    fun setBooks(books: List<Book>) {
        this.books = books
        notifyDataSetChanged()
    }

}