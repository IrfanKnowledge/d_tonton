package com.irfan.dtonton.di

import com.google.android.play.core.splitcompat.SplitCompatApplication
import com.irfan.core.common.Constant
import com.irfan.dtonton.BuildConfig
import com.irfan.dtonton.data.datasource.movie.MovieRemoteDataSource
import com.irfan.dtonton.data.datasource.movie.MovieRemoteDataSourceImpl
import com.irfan.dtonton.data.repository.MovieRepositoryImpl
import com.irfan.dtonton.data.utils.ApiService
import com.irfan.dtonton.domain.repository.MovieRepository
import com.irfan.dtonton.domain.usecase.MovieUseCase
import com.irfan.dtonton.domain.usecase.MovieUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)

        val authInterceptor: Interceptor = Interceptor { chain ->
            val requestOriginal = chain.request()
            val urlOriginal = requestOriginal.url

            val newUrl = urlOriginal.newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build()

            val requestQueryParams = requestOriginal.newBuilder()
                .url(newUrl)
                .build()

            chain.proceed(requestQueryParams)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()

        return client
    }

    @Provides
    fun provideApiService(client: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)
    }
}

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun provideMovieRemoteDataSource(moveiRemoteDataSourceImpl: MovieRemoteDataSourceImpl): MovieRemoteDataSource
}

@Module(includes = [DataSourceModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository
}

@Module(includes = [RepositoryModule::class])
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @ViewModelScoped
    @Binds
    abstract fun provideMovieUseCase(movieUseCaseImpl: MovieUseCaseImpl): MovieUseCase
}

@HiltAndroidApp
class DTontonApplication : SplitCompatApplication() {

}