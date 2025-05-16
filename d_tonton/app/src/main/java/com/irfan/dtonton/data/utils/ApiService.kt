package com.irfan.dtonton.data.utils

import com.irfan.dtonton.data.model.movie.MovieDetailModel
import com.irfan.dtonton.data.model.movie.MovieResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("movie/now_playing")
    suspend fun getListMovieNowPlaying(
    ): Response<MovieResponseModel>

    @GET("movie/popular")
    suspend fun getListMoviePopular(
    ): Response<MovieResponseModel>

    @GET("movie/top_rated")
    suspend fun getListMovieTopRated(
    ): Response<MovieResponseModel>

    @GET("movie/{id}")
    suspend fun getMovieDetail(
        @Path("id") id: Int,
    ): Response<MovieDetailModel>
}