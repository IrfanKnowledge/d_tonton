package com.irfan.dtonton.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.irfan.core.common.MyLogger
import com.irfan.core.common.ResultState
import com.irfan.dtonton.common.DataMapperHelper
import com.irfan.dtonton.domain.usecase.MovieUseCase
import com.irfan.dtonton.presentation.model.MovieCardPModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieHomeViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {
    private val _listMovieNowPlaying =
        MediatorLiveData<ResultState<List<MovieCardPModel>>>()
    val listMovieNowPlaying: LiveData<ResultState<List<MovieCardPModel>>> = _listMovieNowPlaying

    private val _listMoviePopular =
        MediatorLiveData<ResultState<List<MovieCardPModel>>>()
    val listMoviePopular: LiveData<ResultState<List<MovieCardPModel>>> = _listMoviePopular

    private val _listMovieTopRated =
        MediatorLiveData<ResultState<List<MovieCardPModel>>>()
    val listMovieTopRated: LiveData<ResultState<List<MovieCardPModel>>> = _listMovieTopRated

    init {
        MyLogger.d(TAG, "init")
        onRefresh()
    }

    private fun fetchListMovieNowPlaying() {
        _listMovieNowPlaying.addSource(movieUseCase.getListMovieNowPlaying().asLiveData()) {
            val result = when (it) {
                is ResultState.Initial -> ResultState.Initial
                is ResultState.Loading -> ResultState.Loading
                is ResultState.NoData -> ResultState.NoData(it.data)
                is ResultState.HasData -> ResultState.HasData(
                    DataMapperHelper.mapListMovieEntityToListMovieCardPModel(it.data)
                )

                is ResultState.Error -> ResultState.Error(it.message)
            }
            _listMovieNowPlaying.value = result
        }
    }

    private fun fetchListMoviePopular() {
        _listMoviePopular.addSource(movieUseCase.getListMoviePopular().asLiveData()) {
            val result = when (it) {
                is ResultState.Initial -> ResultState.Initial
                is ResultState.Loading -> ResultState.Loading
                is ResultState.NoData -> ResultState.NoData(it.data)
                is ResultState.HasData -> ResultState.HasData(
                    DataMapperHelper.mapListMovieEntityToListMovieCardPModel(it.data)
                )

                is ResultState.Error -> ResultState.Error(it.message)
            }
            _listMoviePopular.value = result
        }
    }

    private fun fetchListMovieTopRated() {
        _listMovieTopRated.addSource(movieUseCase.getListMovieTopRated().asLiveData()) {
            val result = when (it) {
                is ResultState.Initial -> ResultState.Initial
                is ResultState.Loading -> ResultState.Loading
                is ResultState.NoData -> ResultState.NoData(it.data)
                is ResultState.HasData -> ResultState.HasData(
                    DataMapperHelper.mapListMovieEntityToListMovieCardPModel(it.data)
                )

                is ResultState.Error -> ResultState.Error(it.message)
            }
            _listMovieTopRated.value = result
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