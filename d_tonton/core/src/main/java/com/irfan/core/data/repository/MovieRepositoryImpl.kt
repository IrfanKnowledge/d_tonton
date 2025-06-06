package com.irfan.core.data.repository

import com.irfan.core.utils.MyLogger
import com.irfan.core.utils.ResultState
import com.irfan.core.utils.SingleEvent
import com.irfan.core.utils.WatchlistTypeEnum
import com.irfan.core.utils.DataMapperHelper
import com.irfan.core.data.datasource.movie.MovieLocalDataSource
import com.irfan.core.data.datasource.movie.MovieRemoteDataSource
import com.irfan.core.domain.entity.movie.MovieDetail
import com.irfan.core.domain.entity.movie.Movie
import com.irfan.core.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource,
) :
    MovieRepository {

    override fun getListMovieNowPlaying(): Flow<ResultState<List<Movie>>> {
        return movieRemoteDataSource.getListMovieNowPlaying().map { result ->
            when (result) {
                is ResultState.Initial -> ResultState.Initial
                is ResultState.Loading -> ResultState.Loading
                is ResultState.NoData -> ResultState.NoData(result.data)
                is ResultState.HasData -> ResultState.HasData(
                    DataMapperHelper.mapListMovieModelToListMovieEntity(
                        result.data
                    )
                )

                is ResultState.Error -> ResultState.Error(result.message)
            }
        }
    }

    override fun getListMoviePopular(): Flow<ResultState<List<Movie>>> {
        return movieRemoteDataSource.getListMoviePopular().map { result ->
            when (result) {
                is ResultState.Initial -> ResultState.Initial
                is ResultState.Loading -> ResultState.Loading
                is ResultState.NoData -> ResultState.NoData(result.data)
                is ResultState.HasData -> ResultState.HasData(
                    DataMapperHelper.mapListMovieModelToListMovieEntity(
                        result.data
                    )
                )

                is ResultState.Error -> ResultState.Error(result.message)
            }
        }
    }

    override fun getListMovieTopRated(): Flow<ResultState<List<Movie>>> {
        return movieRemoteDataSource.getListMovieTopRated().map { result ->
            when (result) {
                is ResultState.Initial -> ResultState.Initial
                is ResultState.Loading -> ResultState.Loading
                is ResultState.NoData -> ResultState.NoData(result.data)
                is ResultState.HasData -> ResultState.HasData(
                    DataMapperHelper.mapListMovieModelToListMovieEntity(
                        result.data
                    )
                )

                is ResultState.Error -> ResultState.Error(result.message)
            }
        }
    }

    override fun getMovieDetail(id: Int): Flow<ResultState<MovieDetail>> {
        return movieRemoteDataSource.getMovieDetail(id).map { result ->
            when (result) {
                is ResultState.Initial -> ResultState.Initial
                is ResultState.Loading -> ResultState.Loading
                is ResultState.NoData -> ResultState.NoData(result.data)
                is ResultState.HasData -> ResultState.HasData(
                    DataMapperHelper.mapMovieDetailModelToMovieDetailEntity(
                        result.data
                    )
                )

                is ResultState.Error -> ResultState.Error(result.message)
            }
        }
    }

    override fun getMovieDetailListRecommendation(id: Int): Flow<ResultState<List<Movie>>> {
        return movieRemoteDataSource.getMovieDetailListRecommendation(id).map { result ->
            when (result) {
                is ResultState.Initial -> ResultState.Initial
                is ResultState.Loading -> ResultState.Loading
                is ResultState.NoData -> ResultState.NoData(result.data)
                is ResultState.HasData -> ResultState.HasData(
                    DataMapperHelper.mapListMovieModelToListMovieEntity(
                        result.data
                    )
                )

                is ResultState.Error -> ResultState.Error(result.message)
            }
        }
    }

    override fun getWatchlistMovie(): Flow<ResultState<List<Movie>>> {
        return flow {
            try {
                emit(ResultState.Loading)
                val dataModel = movieLocalDataSource.getWatchlistMovie()
                dataModel.collect { resultList ->
                    if (resultList.isEmpty()) {
                        emit(ResultState.NoData(SingleEvent(Unit)))
                    } else {
                        emit(
                            ResultState.HasData(
                                DataMapperHelper.mapListWatchlistTableToListMovieEntity(resultList),
                            ),
                        )
                    }
                }
            } catch (e: Exception) {
                MyLogger.e(TAG, e.message.toString())
                emit(ResultState.Error(SingleEvent(e.message.toString())))
            }
        }
    }

    override suspend fun insertWatchlistMovie(movie: Movie): ResultState<Boolean> {
        return try {
            val watchlistTable =
                DataMapperHelper.mapMovieEntityToWatchlistTable(movie, WatchlistTypeEnum.MOVIE)
            val rowsId = movieLocalDataSource.insertWatchlist(watchlistTable)
            if (rowsId > 0) {
                ResultState.HasData(true)
            } else {
                ResultState.Error(SingleEvent(FAILED_TO_ADD_TO_WATCHLIST_MESSAGE))
            }
        } catch (e: Exception) {
            MyLogger.e(TAG, e.message.toString())
            ResultState.Error(SingleEvent(e.message.toString()))
        }
    }

    override suspend fun deleteWatchlistMovie(id: Int): ResultState<Boolean> {
        return try {
            val rowAffected = movieLocalDataSource.deleteWatchlist(id)
            if (rowAffected > 0) {
                ResultState.HasData(true)
            } else {
                ResultState.Error(SingleEvent(FAILED_TO_DELETE_FROM_WATCHLIST_MESSAGE))
            }
        } catch (e: Exception) {
            MyLogger.e(TAG, e.message.toString())
            ResultState.Error(SingleEvent(e.message.toString()))
        }
    }


    override suspend fun isWatchlistMovie(id: Int): ResultState<Boolean> {
        return try {
            val isWatchlistExist = movieLocalDataSource.isWatchlistExist(id)
            ResultState.HasData(isWatchlistExist)
        } catch (e: Exception) {
            MyLogger.e(TAG, e.message.toString())
            ResultState.Error(SingleEvent(e.message.toString()))
        }
    }

    companion object {
        const val TAG = "MovieRepository"
        private const val FAILED_TO_ADD_TO_WATCHLIST_MESSAGE = "Failed to add to watchlist"
        private const val FAILED_TO_DELETE_FROM_WATCHLIST_MESSAGE =
            "Failed to delete from watchlist"
    }
}