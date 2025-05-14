package com.irfan.dtonton.data.repository

import com.irfan.core.common.ResultState
import com.irfan.dtonton.common.DataMapperHelper
import com.irfan.dtonton.data.datasource.movie.MovieRemoteDataSource
import com.irfan.dtonton.domain.entity.movie.MovieDetailEntity
import com.irfan.dtonton.domain.entity.movie.MovieEntity
import com.irfan.dtonton.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(private val movieRemoteDataSource: MovieRemoteDataSource) :
    MovieRepository {
    override fun getListMovieNowPlaying(): Flow<ResultState<List<MovieEntity>>> {
        return flow {
            emit(ResultState.Loading)
            val dataModel = movieRemoteDataSource.getListMovieNowPlaying()
            dataModel.collect { result ->
                when (result) {
                    is ResultState.Initial -> emit(ResultState.Initial)
                    is ResultState.Loading -> emit(ResultState.Loading)
                    is ResultState.NoData -> emit(ResultState.NoData)
                    is ResultState.HasData -> emit(
                        ResultState.HasData(
                            DataMapperHelper.mapListMovieModelToListMovieEntity(
                                result.data
                            )
                        )
                    )

                    is ResultState.Error -> emit(ResultState.Error(result.error))
                }
            }
        }
    }

    override fun getMovieDetail(id: Int): Flow<ResultState<MovieDetailEntity>> {
        return flow {
            emit(ResultState.Loading)
            val dataModel = movieRemoteDataSource.getMovieDetail(id)
            dataModel.collect { result ->
                when (result) {
                    is ResultState.Initial -> emit(ResultState.Initial)
                    is ResultState.Loading -> emit(ResultState.Loading)
                    is ResultState.NoData -> emit(ResultState.NoData)
                    is ResultState.HasData -> emit(
                        ResultState.HasData(
                            DataMapperHelper.mapMovieDetailModelToMovieDetailEntity(
                                result.data
                            )
                        )
                    )

                    is ResultState.Error -> emit(ResultState.Error(result.error))
                }
            }
        }
    }

    companion object {
        const val TAG = "MovieRepository"

//        @Volatile
//        private var instance: MovieRepositoryImpl? = null
//        fun getInstance(movieRemoteDataSource: MovieRemoteDataSource): MovieRepositoryImpl =
//            instance ?: synchronized(this) {
//                instance ?: MovieRepositoryImpl(movieRemoteDataSource)
//            }.also { instance = it }
    }
}