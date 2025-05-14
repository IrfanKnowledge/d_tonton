package com.irfan.dtonton.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.irfan.core.common.ResultState
import com.irfan.dtonton.domain.entity.movie.MovieEntity
import com.irfan.dtonton.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (private val movieUseCase: MovieUseCase) : ViewModel() {
    private val _listMovie = MediatorLiveData<ResultState<List<MovieEntity>>>(ResultState.Initial)
    val listMovie: LiveData<ResultState<List<MovieEntity>>> = _listMovie

    fun fetchListMovie() {
        _listMovie.addSource(movieUseCase.getListMovieNowPlaying().asLiveData()) {
            _listMovie.value = it
        }
    }
}