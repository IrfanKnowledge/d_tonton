package com.irfan.favorite.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.irfan.core.utils.MyLogger
import com.irfan.core.utils.ResultState
import com.irfan.core.domain.usecase.MovieUseCase
import com.irfan.favorite.utils.DataMapperHelper
import com.irfan.favorite.presentation.model.WatchlistCardListUiModel

class WatchlistMovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    private val _listWatchlistMovie = MediatorLiveData<ResultState<List<WatchlistCardListUiModel>>>()
    val listWatchlistMovie: LiveData<ResultState<List<WatchlistCardListUiModel>>> =
        _listWatchlistMovie

    init {
        MyLogger.d(TAG, "init")
        fetchListWatchlistMovie()
    }

    private fun fetchListWatchlistMovie() {
        _listWatchlistMovie.addSource(movieUseCase.getWatchlistMovie().asLiveData()) {
            val result = when (it) {
                is ResultState.Initial -> ResultState.Initial
                is ResultState.Loading -> ResultState.Loading
                is ResultState.NoData -> ResultState.NoData(it.data)
                is ResultState.HasData -> ResultState.HasData(
                    DataMapperHelper.mapListMovieEntityToListWatchlistCardListUiModel(it.data)
                )

                is ResultState.Error -> ResultState.Error(it.message)
            }
            _listWatchlistMovie.value = result
        }
    }

    fun onRefresh() {
        fetchListWatchlistMovie()
    }

    companion object {
        const val TAG = "WatchlistMovieViewModel"
    }
}