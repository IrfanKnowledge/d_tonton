package com.irfan.dtonton.data.datasource.movie

import com.google.gson.Gson
import com.irfan.core.common.MyLogger
import com.irfan.core.common.ResultState
import com.irfan.core.common.SingleEvent
import com.irfan.dtonton.data.model.ErrorResponseModel
import com.irfan.dtonton.data.model.movie.MovieDetailModel
import com.irfan.dtonton.data.model.movie.MovieModel
import com.irfan.dtonton.data.utils.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

interface MovieRemoteDataSource {
    fun getListMovieNowPlaying(): Flow<ResultState<List<MovieModel>>>
    fun getListMoviePopular(): Flow<ResultState<List<MovieModel>>>
    fun getListMovieTopRated(): Flow<ResultState<List<MovieModel>>>
    fun getMovieDetail(id: Int): Flow<ResultState<MovieDetailModel>>
    fun getMovieDetailListRecommendation(id: Int): Flow<ResultState<List<MovieModel>>>
}

@Singleton
class MovieRemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    MovieRemoteDataSource {
    override fun getListMovieNowPlaying(): Flow<ResultState<List<MovieModel>>> {
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
                        ErrorResponseModel::class.java
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

    override fun getListMoviePopular(): Flow<ResultState<List<MovieModel>>> {
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
                        ErrorResponseModel::class.java
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

    override fun getListMovieTopRated(): Flow<ResultState<List<MovieModel>>> {
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
                        ErrorResponseModel::class.java
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

    override fun getMovieDetail(id: Int): Flow<ResultState<MovieDetailModel>> {
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
                        ErrorResponseModel::class.java
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

    override fun getMovieDetailListRecommendation(id: Int): Flow<ResultState<List<MovieModel>>> {
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
                        ErrorResponseModel::class.java
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