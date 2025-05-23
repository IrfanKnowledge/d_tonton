package com.irfan.dtonton.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.irfan.core.utils.Constant
import com.irfan.core.utils.loadImage
import com.irfan.dtonton.databinding.ItemColumnMovieBinding
import com.irfan.dtonton.presentation.model.MovieCardUiModel

class ListMovieAdapter(
    private val type: String,
    private val listMovie: List<MovieCardUiModel>,
    private val onTap: (movie: MovieCardUiModel, bindingItem: ItemColumnMovieBinding) -> Unit,
) : RecyclerView.Adapter<ListMovieAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ListMovieAdapter.ListViewHolder {
        val binding =
            ItemColumnMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListMovieAdapter.ListViewHolder, position: Int) {
        val movie = listMovie[position]
        val id = movie.id.toString()
        movie.posterPath?.let { posterPath ->
            holder.binding.apply {
                itemColumnMovieImage.loadImage("${Constant.BASE_IMAGE_URL}$posterPath")
                itemColumnMovieImage.transitionName =
                    "item_column_movie_image_${type}_${id}_$position"
            }
        }
        holder.bind(movie)
    }

    override fun getItemCount(): Int = listMovie.size

    inner class ListViewHolder(val binding: ItemColumnMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieCardUiModel) {
            binding.root.setOnClickListener {
                onTap(movie, binding)
            }
        }
    }

}