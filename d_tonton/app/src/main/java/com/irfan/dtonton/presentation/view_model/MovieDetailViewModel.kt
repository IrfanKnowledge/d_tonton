package com.irfan.dtonton.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.irfan.core.common.ResultState
import com.irfan.dtonton.domain.entity.movie.MovieDetailEntity
import com.irfan.dtonton.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor (private val movieUseCase: MovieUseCase) : ViewModel() {
    private val _movieDetail = MediatorLiveData<ResultState<MovieDetailEntity>>(ResultState.Initial)
    val movieDetail: LiveData<ResultState<MovieDetailEntity>> = _movieDetail

    fun fetchMovieDetail(id: Int) {
        _movieDetail.addSource(movieUseCase.getMovieDetail(id).asLiveData()) {
            _movieDetail.value = it
        }
    }
}