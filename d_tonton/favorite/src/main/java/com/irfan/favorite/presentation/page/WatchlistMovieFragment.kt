package com.irfan.favorite.presentation.page

import android.graphics.Rect
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.irfan.core.common.MyLogger
import com.irfan.core.common.ResultState
import com.irfan.core.common.SnackBarHelper.showSnackBarSingleEvent
import com.irfan.dtonton.di.FavoriteModuleDependencies
import com.irfan.favorite.databinding.FragmentWatchlistMovieBinding
import com.irfan.favorite.databinding.ItemRowWatchlistBinding
import com.irfan.favorite.di.DaggerFavoriteComponent
import com.irfan.favorite.presentation.adapter.ListWatchlistAdapter
import com.irfan.favorite.presentation.model.WatchlistCardListPModel
import com.irfan.favorite.presentation.view_model.WatchlistMovieViewModel
import com.irfan.favorite.presentation.view_model_factory.WatchlistMovieViewModelFactory
import dagger.hilt.android.EntryPointAccessors
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import com.irfan.core.R as core

class WatchlistMovieFragment : Fragment() {
    private var _binding: FragmentWatchlistMovieBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: WatchlistMovieViewModelFactory

    private val watchlistMovieViewModel: WatchlistMovieViewModel by viewModels {
        factory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        DaggerFavoriteComponent.builder()
            .context(requireActivity())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireActivity().applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        _binding = FragmentWatchlistMovieBinding.inflate(inflater, container, false)
        val view = binding.root

        val animation =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition = animation
        postponeEnterTransition(200, TimeUnit.MILLISECONDS)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showRecyclerViewWatchlist(view)

        binding.apply {
            watchlistMovieSwipeRefresh.setOnRefreshListener {
                watchlistMovieSwipeRefresh.isRefreshing = false
                watchlistMovieViewModel.onRefresh()
            }
            watchlistMovieToolbar.setNavigationOnClickListener {
                view.findNavController().navigateUp()
            }
        }
    }

    private fun showRecyclerViewWatchlist(view: View) {
        watchlistMovieViewModel.listWatchlistMovie.observe(viewLifecycleOwner) { resultState ->
            MyLogger.d(TAG, "resultState: $resultState")
            when (resultState) {
                is ResultState.Initial -> showLoading(false)
                is ResultState.Loading -> showLoading(true)
                is ResultState.NoData -> {
                    showLoading(false)
                    showSnackBarSingleEvent(
                        view,
                        resultState.data,
                        resources.getString(core.string.result_state_no_data),
                    )
                }

                is ResultState.HasData -> {
                    MyLogger.d(TAG, "ResultState.HasData, length: ${resultState.data.size}")
                    showLoading(false)
                    val data = resultState.data
                    binding.apply {
                        watchlistMovieRv.layoutManager = LinearLayoutManager(
                            requireActivity(),
                            LinearLayoutManager.VERTICAL,
                            false,
                        )
                        if (watchlistMovieRv.itemDecorationCount <= 0) {
                            watchlistMovieRv.addItemDecoration(
                                object :
                                    RecyclerView.ItemDecoration() {
                                    override fun getItemOffsets(
                                        outRect: Rect,
                                        view: View,
                                        parent: RecyclerView,
                                        state: RecyclerView.State,
                                    ) {
                                        val position = parent.getChildAdapterPosition(view)
                                        if (position >= 0) {
                                            outRect.top =
                                                resources.getDimensionPixelSize(core.dimen.watchlist_rv_gap_8dp)
                                        }
                                        if (position == data.lastIndex) {
                                            outRect.bottom =
                                                resources.getDimensionPixelSize(core.dimen.watchlist_rv_gap_8dp)
                                        }
                                    }
                                },
                            )
                        }
                        watchlistMovieRv.adapter = ListWatchlistAdapter(
                            data,
                            onTap = { watchlistCardListPModel, bindingItem ->
                                onTapRecyclerView(view, watchlistCardListPModel, bindingItem)
                            },
                        )
                    }
                }

                is ResultState.Error -> {
                    showLoading(false)
                    showSnackBarSingleEvent(view, resultState.message)
                }
            }
        }
    }

    private fun onTapRecyclerView(
        view: View,
        watchlistCardListPModel: WatchlistCardListPModel,
        bindingItem: ItemRowWatchlistBinding,
    ) {
        MyLogger.d(TAG, "onTap, id: ${watchlistCardListPModel.id}")
        MyLogger.d(TAG, "onTap, poster: ${watchlistCardListPModel.posterPath}")

        val id = watchlistCardListPModel.id
        val toMovieDetailFragment =
            WatchlistMovieFragmentDirections.actionNavWatchlistMovieToMovieDetailFragment(id ?: 0)

        bindingItem.apply {
            val extras = FragmentNavigatorExtras(
                itemRowWatchlistImg to TRANSITION_NAME_IMAGE,
                itemRowWatchlistTvTitle to TRANSITION_NAME_TITLE,
                itemRowWatchlistTvDescription to TRANSITION_NAME_OVERVIEW,
            )

            view.findNavController().navigate(toMovieDetailFragment, extras)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            watchlistMovieProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            watchlistMovieRv.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "WatchlistMovieFragment"
        const val TRANSITION_NAME_IMAGE = "movie_detail_img_movie_transition"
        const val TRANSITION_NAME_TITLE = "movie_detail_tv_title_transition"
        const val TRANSITION_NAME_OVERVIEW = "movie_detail_tv_overview_transition"
    }
}