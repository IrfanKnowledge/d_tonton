package com.irfan.dtonton.presentation.page

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.irfan.core.R
import com.irfan.core.common.Constant
import com.irfan.core.common.MyLogger
import com.irfan.core.common.ResultState
import com.irfan.core.common.SnackBarHelper.showSnackBarSingleEvent
import com.irfan.core.common.loadImage
import com.irfan.dtonton.common.DataMapperHelper
import com.irfan.dtonton.common.RvHelper.rvItemDecoration
import com.irfan.dtonton.common.RvHelper.rvLayoutManager
import com.irfan.dtonton.databinding.FragmentMovieDetailBinding
import com.irfan.dtonton.databinding.ItemColumnMovieBinding
import com.irfan.dtonton.domain.entity.movie.MovieDetailEntity
import com.irfan.dtonton.presentation.adapter.ListMovieAdapter
import com.irfan.dtonton.presentation.model.MovieCardPModel
import com.irfan.dtonton.presentation.view_model.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import com.irfan.core.R as core

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private val movieDetailViewModel: MovieDetailViewModel by viewModels()

    private var idMovieDetail: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        val animation =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = animation
        postponeEnterTransition(400, TimeUnit.MILLISECONDS)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = MovieDetailFragmentArgs.fromBundle(arguments as Bundle)
        idMovieDetail = args.id


        if (!movieDetailViewModel.isInitRefresh) {
            movieDetailViewModel.isInitRefresh = true
            onRefresh()
        }

        showMovieDetail(view)
        showRecyclerViewRecommendations(view)

        binding.apply {
            movieDetailSwipeRefresh.setOnRefreshListener {
                binding.movieDetailSwipeRefresh.isRefreshing = false
                onRefresh()
            }

            movieDetailToolbar.setNavigationOnClickListener {
                view.findNavController().navigateUp()
            }
        }
    }

    private fun showMovieDetail(view: View) {
        movieDetailViewModel.movieDetail.observe(viewLifecycleOwner) { resultState ->
            when (resultState) {
                is ResultState.Initial -> showLoadingImgToBeforeRecommendations(false)
                is ResultState.Loading -> showLoadingImgToBeforeRecommendations(true)
                is ResultState.NoData -> {
                    showLoadingImgToBeforeRecommendations(false)
                    showSnackBarSingleEvent(
                        view,
                        resultState.data,
                        resources.getString(R.string.result_state_no_data),
                    )
                }

                is ResultState.HasData -> {
                    showLoadingImgToBeforeRecommendations(false)
                    val data =
                        DataMapperHelper.mapMovieDetailEntityToMovieDetailPModel(resultState.data)

                    binding.apply {
                        movieDetailImgMovie.loadImage(Constant.BASE_IMAGE_URL + data.posterPath)
                        movieDetailTvTitle.text = data.title
                        movieDetailTvGenre.text = data.listGenre?.joinToString()
                        movieDetailTvDuration.text = data.runtime.toString()
                        movieDetailTvRatingValue.text =
                            getString(core.string.rating_value, data.voteAverage, data.voteCount)
                        movieDetailTvDescription.text = data.overview
                    }

                    showStateIsWatchlist(view, resultState.data)
                }

                is ResultState.Error -> {
                    showLoadingImgToBeforeRecommendations(false)
                    showSnackBarSingleEvent(view, resultState.message)
                }
            }
        }
    }

    private fun showStateIsWatchlist(view: View, moveiDetail: MovieDetailEntity) {
        movieDetailViewModel.stateIsWatchlistMovie.observe(viewLifecycleOwner) { resultState ->
            MyLogger.d(TAG, "stateIsWatchlistMovie: $resultState")
            when (resultState) {
                is ResultState.Initial -> showLoadingBtnWatchlist(false)
                is ResultState.Loading -> showLoadingBtnWatchlist(true)
                is ResultState.NoData -> {
                    showLoadingBtnWatchlist(false)
                    showSnackBarSingleEvent(
                        view,
                        resultState.data,
                        resources.getString(R.string.result_state_no_data),
                    )
                }

                is ResultState.HasData -> {
                    showLoadingBtnWatchlist(false)

                    setIconBtnWatchlist(resultState.data)

                    binding.apply {
                        movieDetailBtnWatchlist.setOnClickListener {
                            installFavoriteModule {
                                onTapBtnWatchlist(resultState.data, moveiDetail)
                            }
                        }
                    }

                    showStateInsertWatchlist(view)
                    showStateDeleteWatchlist(view)
                }

                is ResultState.Error -> {
                    showLoadingRecommendation(false)
                    showSnackBarSingleEvent(view, resultState.message)
                }
            }
        }
    }

    private fun showStateInsertWatchlist(view: View) {
        movieDetailViewModel.stateInsertWatchlistMovie.observe(viewLifecycleOwner) { resultState ->
            MyLogger.d(TAG, "stateInsertWatchlistMovie: $resultState")
            when (resultState) {
                is ResultState.Initial -> showLoadingBtnWatchlist(false)
                is ResultState.Loading -> showLoadingBtnWatchlist(true)
                is ResultState.NoData -> {
                    showLoadingBtnWatchlist(false)
                    showSnackBarSingleEvent(
                        view,
                        resultState.data,
                        resources.getString(R.string.result_state_no_data),
                    )
                }

                is ResultState.HasData -> {
                    showLoadingBtnWatchlist(false)
                    movieDetailViewModel.singleEventHasDataInsertWatchlistMovie.observe(
                        viewLifecycleOwner
                    ) {
                        it.getContentIfNotHandled()?.let {
                            movieDetailViewModel.isWatchlistMovie(idMovieDetail)

                            Snackbar.make(
                                view,
                                resources.getString(R.string.watchlist_added),
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                is ResultState.Error -> {
                    showLoadingBtnWatchlist(false)
                    showSnackBarSingleEvent(view, resultState.message)
                }
            }
        }
    }

    private fun showStateDeleteWatchlist(view: View) {
        movieDetailViewModel.stateDeleteWatchlistMovie.observe(viewLifecycleOwner) { resultState ->
            MyLogger.d(TAG, "stateDeleteWatchlistMovie: $resultState")
            when (resultState) {
                is ResultState.Initial -> showLoadingBtnWatchlist(false)
                is ResultState.Loading -> showLoadingBtnWatchlist(true)
                is ResultState.NoData -> {
                    showLoadingBtnWatchlist(false)
                    showSnackBarSingleEvent(
                        view,
                        resultState.data,
                        resources.getString(R.string.result_state_no_data),
                    )
                }

                is ResultState.HasData -> {
                    showLoadingBtnWatchlist(false)
                    movieDetailViewModel.singleEventHasDataDeleteWatchlistMovie.observe(
                        viewLifecycleOwner
                    ) {
                        it.getContentIfNotHandled()?.let {
                            movieDetailViewModel.isWatchlistMovie(idMovieDetail)

                            Snackbar.make(
                                view,
                                resources.getString(R.string.watchlist_removed),
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                is ResultState.Error -> {
                    showLoadingBtnWatchlist(false)
                    showSnackBarSingleEvent(view, resultState.message)
                }
            }
        }
    }

    private fun showRecyclerViewRecommendations(view: View) {
        movieDetailViewModel.listMovieRecommendation.observe(viewLifecycleOwner) { resultState ->
            when (resultState) {
                is ResultState.Initial -> showLoadingRecommendation(false)
                is ResultState.Loading -> showLoadingRecommendation(true)
                is ResultState.NoData -> {
                    showLoadingRecommendation(false)
                    showSnackBarSingleEvent(
                        view,
                        resultState.data,
                        resources.getString(R.string.result_state_no_data),
                    )
                }

                is ResultState.HasData -> {
                    showLoadingRecommendation(false)
                    val data =
                        DataMapperHelper.mapListMovieEntityToListMovieCardPModel(resultState.data)

                    binding.apply {
                        movieDetailRvRecommendations.layoutManager =
                            rvLayoutManager(requireActivity())
                        if (movieDetailRvRecommendations.itemDecorationCount <= 0) {
                            movieDetailRvRecommendations.addItemDecoration(
                                rvItemDecoration(
                                    resources.getDimensionPixelSize(core.dimen.movie_home_rv_gap_8dp)
                                )
                            )
                        }
                        movieDetailRvRecommendations.adapter = ListMovieAdapter(
                            type = "movieRecommendation",
                            data,
                            onTap = { movieCardPModel, bindingItem ->
                                onTapRecyclerView(view, movieCardPModel, bindingItem)
                            },
                        )
                    }
                }

                is ResultState.Error -> {
                    showLoadingRecommendation(false)
                    showSnackBarSingleEvent(view, resultState.message)
                }
            }
        }
    }

    private fun showLoadingImgToBeforeRecommendations(isLoading: Boolean) {
        binding.apply {
            movieDetailGroupImgToBeforeRecommendations.visibility =
                if (isLoading) View.INVISIBLE else View.VISIBLE
            movieDetailProgressBarImgToBeforeRecommendations.visibility =
                if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun showLoadingBtnWatchlist(isLoading: Boolean) {
        binding.apply {
            movieDetailBtnWatchlist.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
            movieDetailProgressBarWatchlist.visibility =
                if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun showLoadingRecommendation(isLoading: Boolean) {
        binding.apply {
            movieDetailRvRecommendations.visibility =
                if (isLoading) View.INVISIBLE else View.VISIBLE
            movieDetailProgressBarRecommendations.visibility =
                if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun setIconBtnWatchlist(isWatchlist: Boolean) {
        binding.movieDetailBtnWatchlist.icon = ResourcesCompat.getDrawable(
            resources,
            if (isWatchlist) core.drawable.ic_check_24_white else core.drawable.ic_add_24_white,
            requireActivity().theme,
        )
    }

    private fun onTapRecyclerView(
        view: View,
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

        val toMovieDetailFragment = MovieDetailFragmentDirections.actionMovieDetailFragmentSelf(movieCardPModel.id ?: 0)

        val extras = FragmentNavigatorExtras(
            bindingItem.itemColumnMovieImage to "movie_detail_img_movie_transition",
        )

        view.findNavController().navigate(toMovieDetailFragment, extras)
    }

    private fun onRefresh() {
        movieDetailViewModel.onRefresh(idMovieDetail)
    }

    private fun installFavoriteModule(onInstalled: () -> Unit) {
        MyLogger.d(TAG, "installFavoriteModule")
        val splitInstallManager = SplitInstallManagerFactory.create(requireActivity())
        val moduleFavorite = "favorite"
        if (splitInstallManager.installedModules.contains(moduleFavorite)) {
            MyLogger.d(TAG, "already installed")
            onInstalled()
        } else {
            MyLogger.d(TAG, "not installed")
            val request = SplitInstallRequest.newBuilder()
                .addModule(moduleFavorite)
                .build()
            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    Toast.makeText(
                        requireActivity(),
                        "Success installing module",
                        Toast.LENGTH_SHORT
                    ).show()
                    onInstalled()
                }
                .addOnFailureListener {
                    Toast.makeText(requireActivity(), "Error installing module", Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }

    private fun onTapBtnWatchlist(isWatchlist: Boolean, moveiDetail: MovieDetailEntity) {
        MyLogger.d(TAG, "onTapBtnWatchlist")
        val args = MovieDetailFragmentArgs.fromBundle(arguments as Bundle)
        val id = args.id
        if (isWatchlist) {
            movieDetailViewModel.deleteWatchlistMovie(id)
        } else {
            movieDetailViewModel.insertWatchlistMovie(
                DataMapperHelper.mapMovieDetailEntityToMovieEntity(
                    moveiDetail
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "MovieDetailFragment"
    }
}