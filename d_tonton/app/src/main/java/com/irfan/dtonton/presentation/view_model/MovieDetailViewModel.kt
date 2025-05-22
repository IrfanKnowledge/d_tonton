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
import com.irfan.dtonton.common.DataMapperHelper
import com.irfan.dtonton.domain.usecase.MovieUseCase
import com.irfan.dtonton.presentation.model.MovieCardPModel
import com.irfan.dtonton.presentation.model.MovieDetailPModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val movieUseCase: MovieUseCase) :
    ViewModel() {
    private val _movieDetail = MediatorLiveData<ResultState<MovieDetailPModel>>()
    val movieDetail: LiveData<ResultState<MovieDetailPModel>> = _movieDetail

    private val _listMovieRecommendation = MediatorLiveData<ResultState<List<MovieCardPModel>>>()
    val listMovieRecommendation: LiveData<ResultState<List<MovieCardPModel>>> =
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
            val result = when (it) {
                is ResultState.Initial -> ResultState.Initial
                is ResultState.Loading -> ResultState.Loading
                is ResultState.NoData -> ResultState.NoData(it.data)
                is ResultState.HasData -> ResultState.HasData(
                    DataMapperHelper.mapMovieDetailEntityToMovieDetailPModel(it.data)
                )

                is ResultState.Error -> ResultState.Error(it.message)
            }
            _movieDetail.value = result
        }
    }

    private fun fetchMovieDetailListRecommendation(id: Int) {
        _listMovieRecommendation.addSource(
            movieUseCase.getMovieDetailListRecommendation(id).asLiveData()
        ) {
            val result = when (it) {
                is ResultState.Initial -> ResultState.Initial
                is ResultState.Loading -> ResultState.Loading
                is ResultState.NoData -> ResultState.NoData(it.data)
                is ResultState.HasData -> ResultState.HasData(
                    DataMapperHelper.mapListMovieEntityToListMovieCardPModel(it.data)
                )

                is ResultState.Error -> ResultState.Error(it.message)
            }
            _listMovieRecommendation.value = result
        }
    }

    fun insertWatchlistMovie(movie: MovieDetailPModel) {
        viewModelScope.launch {
            _stateInsertWatchlistMovie.value = ResultState.Loading
            _stateInsertWatchlistMovie.value = movieUseCase.insertWatchlistMovie(
                DataMapperHelper.mapMovieDetailPModelToMovieEntity(movie)
            )
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