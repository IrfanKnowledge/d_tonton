package com.irfan.dtonton.di

import com.google.android.play.core.splitcompat.SplitCompatApplication
import com.irfan.core.domain.usecase.MovieUseCase
import com.irfan.core.domain.usecase.MovieUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Singleton
    @Binds
    abstract fun provideMovieUseCase(movieUseCaseImpl: MovieUseCaseImpl): MovieUseCase
}

@HiltAndroidApp
class DTontonApplication : SplitCompatApplication()