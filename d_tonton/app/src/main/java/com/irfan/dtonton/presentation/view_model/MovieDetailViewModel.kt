package com.irfan.dtonton.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.irfan.core.common.MyLogger
import com.irfan.core.common.ResultState
import com.irfan.core.common.SingleEvent
import com.irfan.dtonton.domain.entity.movie.MovieDetailEntity
import com.irfan.dtonton.domain.entity.movie.MovieEntity
import com.irfan.dtonton.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val movieUseCase: MovieUseCase) :
    ViewModel() {
    private val _movieDetail = MediatorLiveData<ResultState<MovieDetailEntity>>()
    val movieDetail: LiveData<ResultState<MovieDetailEntity>> = _movieDetail

    private val _listMovieRecommendation = MediatorLiveData<ResultState<List<MovieEntity>>>()
    val listMovieRecommendation: LiveData<ResultState<List<MovieEntity>>> =
        _listMovieRecommendation

    private val _stateInsertWatchlistMovie = MutableLiveData<ResultState<Boolean>>()
    val stateInsertWatchlistMovie: LiveData<ResultState<Boolean>> = _stateInsertWatchlistMovie

    private val _singleEventHasDataInsertWatchlistMovie = MutableLiveData<SingleEvent<Unit>>()
    val singleEventHasDataInsertWatchlistMovie: LiveData<SingleEvent<Unit>> =
        _singleEventHasDataInsertWatchlistMovie

    private val _stateDeleteWatchlistMovie = MutableLiveData<ResultState<Boolean>>()
    val stateDeleteWatchlistMovie: LiveData<ResultState<Boolean>> = _stateDeleteWatchlistMovie

    private val _singleEventHasDataDeleteWatchlistMovie = MutableLiveData<SingleEvent<Unit>>()
    val singleEventHasDataDeleteWatchlistMovie: LiveData<SingleEvent<Unit>> =
        _singleEventHasDataDeleteWatchlistMovie

    private val _stateIsWatchlistMovie = MutableLiveData<ResultState<Boolean>>()
    val stateIsWatchlistMovie: LiveData<ResultState<Boolean>> = _stateIsWatchlistMovie

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
            _stateInsertWatchlistMovie.value = ResultState.Loading
            _stateInsertWatchlistMovie.value = movieUseCase.insertWatchlistMovie(movie)
            if (_stateInsertWatchlistMovie.value is ResultState.HasData) {
                _singleEventHasDataInsertWatchlistMovie.value = SingleEvent(Unit)
            }
        }
    }

    fun deleteWatchlistMovie(id: Int) {
        viewModelScope.launch {
            _stateDeleteWatchlistMovie.value = ResultState.Loading
            _stateDeleteWatchlistMovie.value = movieUseCase.deleteWatchlistMovie(id)
            if (_stateDeleteWatchlistMovie.value is ResultState.HasData) {
                _singleEventHasDataDeleteWatchlistMovie.value = SingleEvent(Unit)
            }
        }
    }

    fun isWatchlistMovie(id: Int) {
        viewModelScope.launch {
            _stateIsWatchlistMovie.value = ResultState.Loading
            _stateIsWatchlistMovie.value = movieUseCase.isWatchlistMovie(id)
        }
    }

    fun onRefresh(id: Int) {
        fetchMovieDetail(id)
        fetchMovieDetailListRecommendation(id)
        isWatchlistMovie(id)
    }

    override fun onCleared() {
        super.onCleared()
        MyLogger.d(TAG, "onCleared")
    }

    companion object {
        const val TAG = "MovieDetailViewModel"
    }
}