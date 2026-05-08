package com.mooncowpines.kinostats.di

import com.mooncowpines.kinostats.data.repositoryImpl.MockAuthRepositoryImpl
import com.mooncowpines.kinostats.data.repositoryImpl.MockMovieRepositoryImpl
import com.mooncowpines.kinostats.data.repositoryImpl.MockReviewRepositoryImpl
import com.mooncowpines.kinostats.data.repositoryImpl.MockStatsRepositoryImpl
import com.mooncowpines.kinostats.domain.repository.AuthRepository
import com.mooncowpines.kinostats.domain.repository.MovieRepository
import com.mooncowpines.kinostats.domain.repository.ReviewRepository
import com.mooncowpines.kinostats.domain.repository.StatsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository {
        return MockAuthRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideMovieRepository(): MovieRepository {
        return MockMovieRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideReviewRepository(): ReviewRepository {
        return MockReviewRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideStatsRepository(): StatsRepository {
        return MockStatsRepositoryImpl()
    }
}