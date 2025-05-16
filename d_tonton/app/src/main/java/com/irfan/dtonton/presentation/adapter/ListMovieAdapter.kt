package com.irfan.dtonton.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.irfan.core.common.Constant
import com.irfan.core.common.loadImage
import com.irfan.dtonton.databinding.ItemColumnMovieBinding
import com.irfan.dtonton.presentation.model.MovieCardPModel

class ListMovieAdapter(
    private val type: String,
    private val listMovie: List<MovieCardPModel>,
    private val onTap: (movie: MovieCardPModel, bindingItem: ItemColumnMovieBinding) -> Unit,
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
        holder.binding.apply {
            val id = movie.id.toString()
            movie.posterPath?.let { posterPath ->
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

        fun bind(movie: MovieCardPModel) {
            binding.root.setOnClickListener {
                onTap(movie, binding)
            }
        }
    }

}