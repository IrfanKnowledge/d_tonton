package com.irfan.dtonton.data.datasource.movie

import com.google.gson.Gson
import com.irfan.core.common.MyLogger
import com.irfan.core.common.ResultState
import com.irfan.core.common.SingleEvent
import com.irfan.dtonton.data.model.ErrorResponseModel
import com.irfan.dtonton.data.model.movie.MovieDetailModel
import com.irfan.dtonton.data.model.movie.MovieModel
import com.irfan.dtonton.data.utils.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

interface MovieRemoteDataSource {
    suspend fun getListMovieNowPlaying(): Flow<ResultState<List<MovieModel>>>
    suspend fun getListMoviePopular(): Flow<ResultState<List<MovieModel>>>
    suspend fun getListMovieTopRated(): Flow<ResultState<List<MovieModel>>>
    suspend fun getMovieDetail(id: Int): Flow<ResultState<MovieDetailModel>>
}

@Singleton
class MovieRemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    MovieRemoteDataSource {
    override suspend fun getListMovieNowPlaying(): Flow<ResultState<List<MovieModel>>> {
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

    override suspend fun getListMoviePopular(): Flow<ResultState<List<MovieModel>>> {
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

    override suspend fun getListMovieTopRated(): Flow<ResultState<List<MovieModel>>> {
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

    override suspend fun getMovieDetail(id: Int): Flow<ResultState<MovieDetailModel>> {
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

    companion object {
        private const val TAG = "MovieRemoteDataSource"

//        @Volatile
//        private var instance: MovieRemoteDataSourceImpl? = null
//
//        fun getInstance(apiService: ApiService): MovieRemoteDataSourceImpl =
//            instance ?: synchronized(this) {
//                instance ?: MovieRemoteDataSourceImpl(apiService)
//            }.also { instance = it }
    }
}