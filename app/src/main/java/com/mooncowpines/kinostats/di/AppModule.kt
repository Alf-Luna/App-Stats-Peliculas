package com.mooncowpines.kinostats.di

import com.mooncowpines.kinostats.data.remote.KinoStatsApi
import com.mooncowpines.kinostats.data.repositoryImpl.AuthRepositoryImpl
import com.mooncowpines.kinostats.data.repositoryImpl.MockAuthRepositoryImpl
import com.mooncowpines.kinostats.data.repositoryImpl.MockMovieRepositoryImpl
import com.mooncowpines.kinostats.data.repositoryImpl.MockReviewRepositoryImpl
import com.mooncowpines.kinostats.data.repositoryImpl.MockStatsRepositoryImpl
import com.mooncowpines.kinostats.data.repositoryImpl.MovieRepositoryImpl
import com.mooncowpines.kinostats.data.repositoryImpl.ReviewRepositoryImpl
import com.mooncowpines.kinostats.data.repositoryImpl.StatsRepositoryImpl
import com.mooncowpines.kinostats.domain.repository.AuthRepository
import com.mooncowpines.kinostats.domain.repository.MovieRepository
import com.mooncowpines.kinostats.domain.repository.ReviewRepository
import com.mooncowpines.kinostats.domain.repository.StatsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val USE_MOCKS = true

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://10.0.2.2:8080/api/v1")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideKinoStatsApi(retrofit: Retrofit): KinoStatsApi {
        return retrofit.create(KinoStatsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: KinoStatsApi): AuthRepository {
        return if (USE_MOCKS) {
            MockAuthRepositoryImpl()
        } else {
            AuthRepositoryImpl(api)
        }
    }


    @Provides
    @Singleton
    fun provideMovieRepository(api: KinoStatsApi): MovieRepository {
        return if (USE_MOCKS) {
            MockMovieRepositoryImpl()
        } else {
            MovieRepositoryImpl(api)
        }
    }

    @Provides
    @Singleton
    fun provideReviewRepository(api: KinoStatsApi): ReviewRepository {
        return if (USE_MOCKS)
        {
            MockReviewRepositoryImpl()
        } else {
            ReviewRepositoryImpl(api)
        }
    }

    @Provides
    @Singleton
    fun provideStatsRepository(api: KinoStatsApi): StatsRepository {
        return if (USE_MOCKS)
        {
            MockStatsRepositoryImpl()
        } else {
            StatsRepositoryImpl(api)
        }
    }
}