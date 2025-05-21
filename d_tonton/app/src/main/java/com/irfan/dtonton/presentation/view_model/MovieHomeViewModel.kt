package com.irfan.dtonton.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.irfan.core.common.MyLogger
import com.irfan.core.common.ResultState
import com.irfan.dtonton.domain.entity.movie.MovieEntity
import com.irfan.dtonton.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieHomeViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {
    private val _listMovieNowPlaying =
        MediatorLiveData<ResultState<List<MovieEntity>>>()
    val listMovieNowPlaying: LiveData<ResultState<List<MovieEntity>>> = _listMovieNowPlaying

    private val _listMoviePopular =
        MediatorLiveData<ResultState<List<MovieEntity>>>()
    val listMoviePopular: LiveData<ResultState<List<MovieEntity>>> = _listMoviePopular

    private val _listMovieTopRated =
        MediatorLiveData<ResultState<List<MovieEntity>>>()
    val listMovieTopRated: LiveData<ResultState<List<MovieEntity>>> = _listMovieTopRated

    init {
        MyLogger.d(TAG, "init")
        onRefresh()
    }

    private fun fetchListMovieNowPlaying() {
        _listMovieNowPlaying.addSource(movieUseCase.getListMovieNowPlaying().asLiveData()) {
            _listMovieNowPlaying.value = it
        }
    }

    private fun fetchListMoviePopular() {
        _listMoviePopular.addSource(movieUseCase.getListMoviePopular().asLiveData()) {
            _listMoviePopular.value = it
        }
    }

    private fun fetchListMovieTopRated() {
        _listMovieTopRated.addSource(movieUseCase.getListMovieTopRated().asLiveData()) {
            _listMovieTopRated.value = it
        }
    }

    fun onRefresh() {
        fetchListMovieNowPlaying()
        fetchListMoviePopular()
        fetchListMovieTopRated()
    }

    override fun onCleared() {
        super.onCleared()
        MyLogger.d(TAG, "onCleared")
    }

    companion object {
        const val TAG = "MovieHomeViewModel"
    }
}