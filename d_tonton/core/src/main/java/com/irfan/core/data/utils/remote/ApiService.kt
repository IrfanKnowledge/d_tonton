package com.irfan.core.data.utils.remote

import com.irfan.core.data.model.movie.MovieDetailResponse
import com.irfan.core.data.model.movie.MovieWrapperResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("movie/now_playing")
    suspend fun getListMovieNowPlaying(
    ): Response<MovieWrapperResponse>

    @GET("movie/popular")
    suspend fun getListMoviePopular(
    ): Response<MovieWrapperResponse>

    @GET("movie/top_rated")
    suspend fun getListMovieTopRated(
    ): Response<MovieWrapperResponse>

    @GET("movie/{id}")
    suspend fun getMovieDetail(
        @Path("id") id: Int,
    ): Response<MovieDetailResponse>

    @GET("movie/{id}/recommendations")
    suspend fun getMovieDetailListRecommendation(
        @Path("id") id: Int,
    ): Response<MovieWrapperResponse>
}