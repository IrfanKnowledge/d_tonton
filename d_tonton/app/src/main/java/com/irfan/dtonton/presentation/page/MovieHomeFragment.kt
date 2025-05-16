package com.irfan.dtonton.presentation.page

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.irfan.core.common.Constant
import com.irfan.core.common.MyLogger
import com.irfan.core.common.ResultState
import com.irfan.core.common.SingleEvent
import com.irfan.dtonton.common.DataMapperHelper
import com.irfan.dtonton.databinding.FragmentMovieHomeBinding
import com.irfan.dtonton.databinding.ItemColumnMovieBinding
import com.irfan.dtonton.presentation.adapter.ListMovieAdapter
import com.irfan.dtonton.presentation.model.MovieCardPModel
import com.irfan.dtonton.presentation.view_model.MovieHomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.irfan.core.R as core

@AndroidEntryPoint
class MovieHomeFragment : Fragment() {
    private lateinit var _binding: FragmentMovieHomeBinding
    private val binding get() = _binding

    private val movieHomeViewModel: MovieHomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onRefresh()

        showRecyclerViewNowPlaying(view)
        showRecyclerViewPopular(view)
        showRecyclerViewTopRated(view)

        binding.movieHomeSwipeRefresh.setOnRefreshListener {
            binding.movieHomeSwipeRefresh.isRefreshing = false
            onRefresh()
        }
    }

    private fun showRecyclerViewNowPlaying(view: View) {
        movieHomeViewModel.listMovieNowPlaying.observe(viewLifecycleOwner) { resultState ->
            when (resultState) {
                is ResultState.Initial -> showLoadingNowPlaying(false)
                is ResultState.Loading -> showLoadingNowPlaying(true)
                is ResultState.NoData -> {
                    showLoadingNowPlaying(false)
                    showSnackBarSingleEvent(
                        view,
                        resultState.data,
                        resources.getString(core.string.result_state_no_data),
                    )
                }

                is ResultState.HasData -> {
                    showLoadingNowPlaying(false)
                    val data =
                        DataMapperHelper.mapListMovieEntityToListMovieCardPModel(resultState.data)

                    binding.apply {
                        movieHomeRvNowPlaying.layoutManager = rvLayoutManager()
                        if (movieHomeRvNowPlaying.itemDecorationCount <= 0) {
                            movieHomeRvNowPlaying.addItemDecoration(rvItemDecoration())
                        }
                        movieHomeRvNowPlaying.adapter = ListMovieAdapter(
                            type = "movieNowPlaying",
                            data,
                            onTap = ::onTapRecyclerView,
                        )
                    }
                }

                is ResultState.Error -> {
                    showLoadingNowPlaying(false)
                    showSnackBarSingleEvent(view, resultState.message)
                }
            }
        }
    }

    private fun showRecyclerViewPopular(view: View) {
        movieHomeViewModel.listMoviePopular.observe(viewLifecycleOwner) { resultState ->
            when (resultState) {
                is ResultState.Initial -> showLoadingPopular(false)
                is ResultState.Loading -> showLoadingPopular(true)
                is ResultState.NoData -> {
                    showLoadingPopular(false)
                    showSnackBarSingleEvent(
                        view,
                        resultState.data,
                        resources.getString(core.string.result_state_no_data),
                    )
                }

                is ResultState.HasData -> {
                    showLoadingPopular(false)
                    val data =
                        DataMapperHelper.mapListMovieEntityToListMovieCardPModel(resultState.data)
                    binding.apply {
                        movieHomeRvPopular.layoutManager = rvLayoutManager()
                        if (movieHomeRvPopular.itemDecorationCount <= 0) {
                            movieHomeRvPopular.addItemDecoration(rvItemDecoration())
                        }
                        movieHomeRvPopular.adapter = ListMovieAdapter(
                            type = "moviePopular",
                            data,
                            onTap = ::onTapRecyclerView,
                        )
                    }
                }

                is ResultState.Error -> {
                    showLoadingNowPlaying(false)
                    showSnackBarSingleEvent(view, resultState.message)
                }
            }
        }
    }

    private fun showRecyclerViewTopRated(view: View) {
        movieHomeViewModel.listMovieTopRated.observe(viewLifecycleOwner) { resultState ->
            when (resultState) {
                is ResultState.Initial -> showLoadingTopRated(false)
                is ResultState.Loading -> showLoadingTopRated(true)
                is ResultState.NoData -> {
                    showLoadingTopRated(false)
                    showSnackBarSingleEvent(
                        view,
                        resultState.data,
                        resources.getString(core.string.result_state_no_data),
                    )
                }

                is ResultState.HasData -> {
                    showLoadingTopRated(false)
                    val data =
                        DataMapperHelper.mapListMovieEntityToListMovieCardPModel(resultState.data)

                    binding.apply {
                        movieHomeRvTopRated.layoutManager = rvLayoutManager()
                        if (movieHomeRvTopRated.itemDecorationCount <= 0) {
                            movieHomeRvTopRated.addItemDecoration(rvItemDecoration())
                        }
                        movieHomeRvTopRated.adapter = ListMovieAdapter(
                            type = "movieTopRated",
                            data,
                            onTap = ::onTapRecyclerView,
                        )
                    }
                }

                is ResultState.Error -> {
                    showLoadingTopRated(false)
                    showSnackBarSingleEvent(view, resultState.message)
                }
            }
        }
    }

    private fun rvLayoutManager(): LinearLayoutManager {
        return LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false,
        )
    }

    private fun rvItemDecoration(): RecyclerView.ItemDecoration {
        return object :
            RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State,
            ) {
                val spacing =
                    resources.getDimensionPixelSize(core.dimen.movie_home_rv_gap_8dp)

                if (parent.getChildAdapterPosition(view) != 0) {
                    outRect.left = spacing
                }
            }
        }
    }

    private fun showLoadingNowPlaying(isLoading: Boolean) {
        binding.apply {
            movieHomeRvNowPlaying.visibility = if (isLoading) View.GONE else View.VISIBLE
            movieHomeProgressBarNowPlaying.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun showLoadingPopular(isLoading: Boolean) {
        binding.apply {
            movieHomeRvPopular.visibility = if (isLoading) View.GONE else View.VISIBLE
            movieHomeProgressBarPopular.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun showLoadingTopRated(isLoading: Boolean) {
        binding.apply {
            movieHomeRvTopRated.visibility = if (isLoading) View.GONE else View.VISIBLE
            movieHomeProgressBarTopRated.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun showSnackBarSingleEvent(view: View, event: SingleEvent<Unit>, message: String) {
        event.getContentIfNotHandled()?.let {
            Snackbar.make(
                view,
                message,
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun showSnackBarSingleEvent(view: View, message: SingleEvent<String>) {
        message.getContentIfNotHandled()?.let {
            Snackbar.make(
                view,
                it,
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun onTapRecyclerView(
        movieCardPModel: MovieCardPModel,
        bindingItem: ItemColumnMovieBinding,
    ) {
        MyLogger.d(
            TAG,
            "onTap, id: ${movieCardPModel.id}, poster: ${movieCardPModel.posterPath}"
        )
        MyLogger.d(
            TAG,
            "onTap, imgUrl: ${Constant.BASE_IMAGE_URL}${movieCardPModel.posterPath}"
        )

        val extras = FragmentNavigatorExtras(
            bindingItem.itemColumnMovieImage to "movie_detail_img_movie_transition",
        )

        // todo: onTap
    }

    private fun onRefresh() {
        movieHomeViewModel.fetchListMovieNowPlaying()
        movieHomeViewModel.fetchListMoviePopular()
        movieHomeViewModel.fetchListMovieTopRated()
    }

    companion object {
        const val TAG = "MovieHomeFragment"
    }
}