package com.irfan.dtonton.presentation.page

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.irfan.core.utils.Constant
import com.irfan.core.utils.MyLogger
import com.irfan.core.utils.ResultState
import com.irfan.core.utils.SnackBarHelper.showSnackBarSingleEvent
import com.irfan.dtonton.utils.RvHorizontalHelper.rvItemDecoration
import com.irfan.dtonton.utils.RvHorizontalHelper.rvLayoutManager
import com.irfan.dtonton.databinding.FragmentMovieHomeBinding
import com.irfan.dtonton.databinding.ItemColumnMovieBinding
import com.irfan.dtonton.presentation.adapter.ListMovieAdapter
import com.irfan.dtonton.presentation.model.MovieCardUiModel
import com.irfan.dtonton.presentation.view_model.MovieHomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import com.irfan.core.R as core

@AndroidEntryPoint
class MovieHomeFragment : Fragment() {
    private var _binding: FragmentMovieHomeBinding? = null
    private val binding get() = _binding!!

    private val movieHomeViewModel: MovieHomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        val animation =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition = animation
        postponeEnterTransition(200, TimeUnit.MILLISECONDS)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showRecyclerViewNowPlaying(view)
        showRecyclerViewPopular(view)
        showRecyclerViewTopRated(view)

        binding.apply {
            movieHomeSwipeRefresh.setOnRefreshListener {
                movieHomeSwipeRefresh.isRefreshing = false
                movieHomeViewModel.onRefresh()
            }
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
                    val data = resultState.data

                    binding.apply {
                        movieHomeRvNowPlaying.layoutManager = rvLayoutManager(requireActivity())
                        if (movieHomeRvNowPlaying.itemDecorationCount <= 0) {
                            movieHomeRvNowPlaying.addItemDecoration(
                                rvItemDecoration(
                                    resources.getDimensionPixelSize(
                                        core.dimen.movie_home_rv_gap_8dp
                                    )
                                )
                            )
                        }
                        movieHomeRvNowPlaying.adapter = ListMovieAdapter(
                            type = TYPE_NOW_PLAYING,
                            data,
                            onTap = { movieCardPModel, bindingItem ->
                                onTapRecyclerView(view, movieCardPModel, bindingItem)
                            },
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
                    val data = resultState.data
                    binding.apply {
                        movieHomeRvPopular.layoutManager = rvLayoutManager(requireActivity())
                        if (movieHomeRvPopular.itemDecorationCount <= 0) {
                            movieHomeRvPopular.addItemDecoration(
                                rvItemDecoration(
                                    resources.getDimensionPixelSize(
                                        core.dimen.movie_home_rv_gap_8dp
                                    )
                                )
                            )
                        }
                        movieHomeRvPopular.adapter = ListMovieAdapter(
                            type = TYPE_POPULAR,
                            data,
                            onTap = { movieCardPModel, bindingItem ->
                                onTapRecyclerView(view, movieCardPModel, bindingItem)
                            },
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
                    val data = resultState.data

                    binding.apply {
                        movieHomeRvTopRated.layoutManager = rvLayoutManager(requireActivity())
                        if (movieHomeRvTopRated.itemDecorationCount <= 0) {
                            movieHomeRvTopRated.addItemDecoration(
                                rvItemDecoration(
                                    resources.getDimensionPixelSize(
                                        core.dimen.movie_home_rv_gap_8dp
                                    )
                                )
                            )
                        }
                        movieHomeRvTopRated.adapter = ListMovieAdapter(
                            type = TYPE_TOP_RATED,
                            data,
                            onTap = { movieCardPModel, bindingItem ->
                                onTapRecyclerView(view, movieCardPModel, bindingItem)
                            },
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

    private fun showLoadingNowPlaying(isLoading: Boolean) {
        binding.apply {
            movieHomeRvNowPlaying.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
            movieHomeProgressBarNowPlaying.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun showLoadingPopular(isLoading: Boolean) {
        binding.apply {
            movieHomeRvPopular.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
            movieHomeProgressBarPopular.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun showLoadingTopRated(isLoading: Boolean) {
        binding.apply {
            movieHomeRvTopRated.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
            movieHomeProgressBarTopRated.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun onTapRecyclerView(
        view: View,
        movieCardUiModel: MovieCardUiModel,
        bindingItem: ItemColumnMovieBinding,
    ) {
        MyLogger.d(
            TAG,
            "onTap, id: ${movieCardUiModel.id}, poster: ${movieCardUiModel.posterPath}"
        )
        MyLogger.d(
            TAG,
            "onTap, imgUrl: ${Constant.BASE_IMAGE_URL}${movieCardUiModel.posterPath}"
        )

        val id = movieCardUiModel.id
        val toMovieDetailFragment =
            MovieHomeFragmentDirections.actionNavMovieHomeToMovieDetailFragment(id ?: 0)

        val extras = FragmentNavigatorExtras(
            bindingItem.itemColumnMovieImage to MOVIE_DETAIL_IMG_MOVIE_TRANSITION,
        )

        view.findNavController().navigate(toMovieDetailFragment, extras)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "MovieHomeFragment"
        const val TYPE_NOW_PLAYING = "movieNowPlaying"
        const val TYPE_POPULAR = "moviePopular"
        const val TYPE_TOP_RATED = "movieTopRated"
        const val MOVIE_DETAIL_IMG_MOVIE_TRANSITION = "movie_detail_img_movie_transition"
    }
}