package com.irfan.favorite.presentation.view_model_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.irfan.core.domain.usecase.MovieUseCase
import com.irfan.favorite.presentation.view_model.WatchlistMovieViewModel
import javax.inject.Inject

class WatchlistMovieViewModelFactory @Inject constructor(private val movieUseCase: MovieUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WatchlistMovieViewModel::class.java)) {
            return WatchlistMovieViewModel(movieUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}