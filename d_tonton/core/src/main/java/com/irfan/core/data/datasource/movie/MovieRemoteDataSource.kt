package com.irfan.core.data.datasource.movie

import com.google.gson.Gson
import com.irfan.core.data.model.ErrorResponse
import com.irfan.core.data.model.movie.MovieDetailResponse
import com.irfan.core.data.model.movie.MovieResponse
import com.irfan.core.data.utils.remote.ApiService
import com.irfan.core.utils.MyLogger
import com.irfan.core.utils.ResultState
import com.irfan.core.utils.SingleEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

interface MovieRemoteDataSource {
    fun getListMovieNowPlaying(): Flow<ResultState<List<MovieResponse>>>
    fun getListMoviePopular(): Flow<ResultState<List<MovieResponse>>>
    fun getListMovieTopRated(): Flow<ResultState<List<MovieResponse>>>
    fun getMovieDetail(id: Int): Flow<ResultState<MovieDetailResponse>>
    fun getMovieDetailListRecommendation(id: Int): Flow<ResultState<List<MovieResponse>>>
}

@Singleton
class MovieRemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    MovieRemoteDataSource {
    override fun getListMovieNowPlaying(): Flow<ResultState<List<MovieResponse>>> {
        return flow {
            try {
                emit(ResultState.Loading)
                val response = apiService.getListMovieNowPlaying()
                if (response.isSuccessful) {
                    val data = response.body()?.results
                    if (data.isNullOrEmpty()) {
                        emit(ResultState.NoData(SingleEvent(Unit)))
                    } else {
                        emit(ResultState.HasData(data))
                    }
                } else {
                    val errorResponseModel = Gson().fromJson(
                        response.errorBody()?.string(),
                        ErrorResponse::class.java
                    )
                    val message = errorResponseModel.message ?: ""
                    MyLogger.d(TAG, message)
                    emit(ResultState.Error(SingleEvent(message)))
                }
            } catch (e: Exception) {
                MyLogger.e(TAG, e.message.toString())
                emit(ResultState.Error(SingleEvent(e.message.toString())))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getListMoviePopular(): Flow<ResultState<List<MovieResponse>>> {
        return flow {
            try {
                emit(ResultState.Loading)
                val response = apiService.getListMoviePopular()
                if (response.isSuccessful) {
                    val data = response.body()?.results
                    if (data.isNullOrEmpty()) {
                        emit(ResultState.NoData(SingleEvent(Unit)))
                    } else {
                        emit(ResultState.HasData(data))
                    }
                } else {
                    val errorResponseModel = Gson().fromJson(
                        response.errorBody()?.string(),
                        ErrorResponse::class.java
                    )
                    val message = errorResponseModel.message ?: ""
                    MyLogger.d(TAG, message)
                    emit(ResultState.Error(SingleEvent(message)))
                }
            } catch (e: Exception) {
                MyLogger.e(TAG, e.message.toString())
                emit(ResultState.Error(SingleEvent(e.message.toString())))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getListMovieTopRated(): Flow<ResultState<List<MovieResponse>>> {
        return flow {
            try {
                emit(ResultState.Loading)
                val response = apiService.getListMovieTopRated()
                if (response.isSuccessful) {
                    val data = response.body()?.results
                    if (data.isNullOrEmpty()) {
                        emit(ResultState.NoData(SingleEvent(Unit)))
                    } else {
                        emit(ResultState.HasData(data))
                    }
                } else {
                    val errorResponseModel = Gson().fromJson(
                        response.errorBody()?.string(),
                        ErrorResponse::class.java
                    )
                    val message = errorResponseModel.message ?: ""
                    MyLogger.d(TAG, message)
                    emit(ResultState.Error(SingleEvent(message)))
                }
            } catch (e: Exception) {
                MyLogger.e(TAG, e.message.toString())
                emit(ResultState.Error(SingleEvent(e.message.toString())))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getMovieDetail(id: Int): Flow<ResultState<MovieDetailResponse>> {
        return flow {
            try {
                emit(ResultState.Loading)
                val response = apiService.getMovieDetail(id)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data == null) {
                        emit(ResultState.NoData(SingleEvent(Unit)))
                    } else {
                        emit(ResultState.HasData(data))
                    }
                } else {
                    val errorResponseModel = Gson().fromJson(
                        response.errorBody()?.string(),
                        ErrorResponse::class.java
                    )
                    val message = errorResponseModel.message ?: ""
                    MyLogger.d(TAG, message)
                    emit(ResultState.Error(SingleEvent(message)))
                }
            } catch (e: Exception) {
                MyLogger.e(TAG, e.message.toString())
                emit(ResultState.Error(SingleEvent(e.message.toString())))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getMovieDetailListRecommendation(id: Int): Flow<ResultState<List<MovieResponse>>> {
        return flow {
            try {
                emit(ResultState.Loading)
                val response = apiService.getMovieDetailListRecommendation(id)
                if (response.isSuccessful) {
                    val data = response.body()?.results
                    if (data.isNullOrEmpty()) {
                        emit(ResultState.NoData(SingleEvent(Unit)))
                    } else {
                        emit(ResultState.HasData(data))
                    }
                } else {
                    val errorResponseModel = Gson().fromJson(
                        response.errorBody()?.string(),
                        ErrorResponse::class.java
                    )
                    val message = errorResponseModel.message ?: ""
                    MyLogger.d(TAG, message)
                    emit(ResultState.Error(SingleEvent(message)))
                }
            } catch (e: Exception) {
                MyLogger.e(TAG, e.message.toString())
                emit(ResultState.Error(SingleEvent(e.message.toString())))
            }
        }.flowOn(Dispatchers.IO)
    }

    companion object {
        private const val TAG = "MovieRemoteDataSource"
    }
}