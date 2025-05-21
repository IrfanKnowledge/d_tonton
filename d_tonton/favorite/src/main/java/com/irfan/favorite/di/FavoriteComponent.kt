package com.irfan.favorite.di

import android.content.Context
import com.irfan.dtonton.di.FavoriteModuleDependencies
import com.irfan.favorite.presentation.page.WatchlistMovieFragment
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteComponent {
    fun inject(fragment: WatchlistMovieFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModuleDependencies: FavoriteModuleDependencies): Builder
        fun build(): FavoriteComponent

    }
}