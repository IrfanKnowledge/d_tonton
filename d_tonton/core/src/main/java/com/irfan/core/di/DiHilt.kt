package com.irfan.core.di

import android.content.Context
import androidx.room.Room
import com.irfan.core.BuildConfig
import com.irfan.core.data.datasource.movie.MovieLocalDataSource
import com.irfan.core.data.datasource.movie.MovieLocalDataSourceImpl
import com.irfan.core.data.datasource.movie.MovieRemoteDataSource
import com.irfan.core.data.datasource.movie.MovieRemoteDataSourceImpl
import com.irfan.core.data.repository.MovieRepositoryImpl
import com.irfan.core.data.utils.local.DTontonDatabase
import com.irfan.core.data.utils.local.WatchlistDao
import com.irfan.core.data.utils.remote.ApiService
import com.irfan.core.domain.repository.MovieRepository
import com.irfan.core.utils.Constant
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)

        val authInterceptor = Interceptor { chain ->
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

        val hostName = Constant.HOST_NAME

        val certificatePinner = CertificatePinner.Builder()
            .add(hostName, "sha256/${Constant.PIN_SHA256}")
            .build()

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
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

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): DTontonDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("dtonton".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(context, DTontonDatabase::class.java, "dtonton.db")
            .fallbackToDestructiveMigration(false)
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    fun provideWatchlistDao(database: DTontonDatabase): WatchlistDao = database.watchlistDao()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun provideMovieRemoteDataSource(movieRemoteDataSourceImpl: MovieRemoteDataSourceImpl): MovieRemoteDataSource

    @Binds
    abstract fun provideMovieLocalDataSource(movieLocalDataSourceImpl: MovieLocalDataSourceImpl): MovieLocalDataSource
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository
}