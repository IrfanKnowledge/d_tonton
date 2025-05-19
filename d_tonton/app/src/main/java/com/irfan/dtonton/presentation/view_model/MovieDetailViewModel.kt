package com.irfan.dtonton.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.irfan.core.common.MyLogger
import com.irfan.core.common.ResultState
import com.irfan.dtonton.domain.entity.movie.MovieDetailEntity
import com.irfan.dtonton.domain.entity.movie.MovieEntity
import com.irfan.dtonton.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val movieUseCase: MovieUseCase) :
    ViewModel() {
    private val _movieDetail = MediatorLiveData<ResultState<MovieDetailEntity>>(ResultState.Initial)
    val movieDetail: LiveData<ResultState<MovieDetailEntity>> = _movieDetail

    private val _listMovieRecommendation = MediatorLiveData<ResultState<List<MovieEntity>>>()
    val listMovieRecommendation: LiveData<ResultState<List<MovieEntity>>> =
        _listMovieRecommendation

    private val _insertWatchlistMovie = MutableLiveData<ResultState<Boolean>>()
    val insertWatchlistMovie: LiveData<ResultState<Boolean>> = _insertWatchlistMovie

    private val _deleteWatchlistMovie = MutableLiveData<ResultState<Boolean>>()
    val deleteWatchlistMovie: LiveData<ResultState<Boolean>> = _deleteWatchlistMovie

    private val _isWatchlistMovie = MutableLiveData<ResultState<Boolean>>()
    val isWatchlistMovie: LiveData<ResultState<Boolean>> = _isWatchlistMovie

    var isInitRefresh = false

    private fun fetchMovieDetail(id: Int) {
        _movieDetail.addSource(movieUseCase.getMovieDetail(id).asLiveData()) {
            _movieDetail.value = it
        }
    }

    private fun fetchMovieDetailListRecommendation(id: Int) {
        _listMovieRecommendation.addSource(
            movieUseCase.getMovieDetailListRecommendation(id).asLiveData()
        ) {
            _listMovieRecommendation.value = it
        }
    }

    fun insertWatchlistMovie(movie: MovieEntity) {
        viewModelScope.launch {
            _insertWatchlistMovie.value = ResultState.Loading
            _insertWatchlistMovie.value = movieUseCase.insertWatchlistMovie(movie)
        }
    }

    fun deleteWatchlistMovie(id: Int) {
        viewModelScope.launch {
            _deleteWatchlistMovie.value = ResultState.Loading
            _deleteWatchlistMovie.value = movieUseCase.deleteWatchlistMovie(id)
        }
    }

    fun isWatchlistMovie(id: Int) {
        viewModelScope.launch {
            _isWatchlistMovie.value = ResultState.Loading
            _isWatchlistMovie.value = movieUseCase.isWatchlistMovie(id)
        }
    }

    fun onRefresh(id: Int) {
        fetchMovieDetail(id)
        fetchMovieDetailListRecommendation(id)
    }

    override fun onCleared() {
        super.onCleared()
        MyLogger.d(TAG, "onCleared")
    }

    companion object {
        const val TAG = "MovieDetailViewModel"
    }
}