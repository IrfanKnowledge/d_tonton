package com.irfan.favorite.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.irfan.core.common.ResultState
import com.irfan.dtonton.domain.entity.movie.MovieEntity
import com.irfan.dtonton.domain.usecase.MovieUseCase

class WatchlistMovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    private val _listWatchlistMovie = MediatorLiveData<ResultState<List<MovieEntity>>>()
    val listWatchlistMovie: LiveData<ResultState<List<MovieEntity>>> = _listWatchlistMovie

    init {
        fetchListWatchlistMovie()
    }

    private fun fetchListWatchlistMovie() {
        _listWatchlistMovie.addSource(movieUseCase.getWatchlistMovie().asLiveData()) {
            _listWatchlistMovie.value = it
        }
    }

    fun onRefresh() {
        fetchListWatchlistMovie()
    }
}