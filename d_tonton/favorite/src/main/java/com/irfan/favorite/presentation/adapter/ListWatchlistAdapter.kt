package com.irfan.favorite.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.irfan.core.utils.Constant
import com.irfan.core.utils.loadImage
import com.irfan.favorite.databinding.ItemRowWatchlistBinding
import com.irfan.favorite.presentation.model.WatchlistCardListUiModel

class ListWatchlistAdapter(
    private val listWatchlist: List<WatchlistCardListUiModel>,
    private val onTap: (watchlistCardListUiModel: WatchlistCardListUiModel, bindingItem: ItemRowWatchlistBinding) -> Unit,
) : RecyclerView.Adapter<ListWatchlistAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ListViewHolder {
        val binding =
            ItemRowWatchlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListWatchlistAdapter.ListViewHolder, position: Int) {
        val watchlist = listWatchlist[position]
        val id = watchlist.id.toString()

        holder.binding.apply {
            watchlist.posterPath?.let { posterPath ->
                itemRowWatchlistImg.loadImage("${Constant.BASE_IMAGE_URL}$posterPath")
                itemRowWatchlistImg.transitionName =
                    "item_row_watchlist_img_${id}_$position"
            }
            itemRowWatchlistTvTitle.text = watchlist.title
            itemRowWatchlistTvTitle.transitionName =
                "item_row_watchlist_tv_title_${id}_$position"
            itemRowWatchlistTvDescription.text = watchlist.overview
            itemRowWatchlistTvDescription.transitionName =
                "item_row_watchlist_tv_description_${id}_$position"
        }
        holder.bind(watchlist)
    }

    override fun getItemCount(): Int = listWatchlist.size

    inner class ListViewHolder(val binding: ItemRowWatchlistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(watchlistCardListUiModel: WatchlistCardListUiModel) {
            binding.root.setOnClickListener {
                onTap(watchlistCardListUiModel, binding)
            }
        }
    }
}