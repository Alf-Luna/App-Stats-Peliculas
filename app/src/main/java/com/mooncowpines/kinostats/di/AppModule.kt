package com.mooncowpines.kinostats.di

import com.mooncowpines.kinostats.data.local.SessionManager
import com.mooncowpines.kinostats.data.remote.AuthApi
import com.mooncowpines.kinostats.data.remote.ListApi
import com.mooncowpines.kinostats.data.remote.MovieApi
import com.mooncowpines.kinostats.data.remote.ReviewApi
import com.mooncowpines.kinostats.data.remote.StatsApi
import com.mooncowpines.kinostats.data.repositoryImpl.AuthRepositoryImpl
import com.mooncowpines.kinostats.data.repositoryImpl.ListRepositoryImpl
import com.mooncowpines.kinostats.data.repositoryImpl.MockAuthRepositoryImpl
import com.mooncowpines.kinostats.data.repositoryImpl.MockMovieRepositoryImpl
import com.mooncowpines.kinostats.data.repositoryImpl.MockReviewRepositoryImpl
import com.mooncowpines.kinostats.data.repositoryImpl.MockStatsRepositoryImpl
import com.mooncowpines.kinostats.data.repositoryImpl.MovieRepositoryImpl
import com.mooncowpines.kinostats.data.repositoryImpl.ReviewRepositoryImpl
import com.mooncowpines.kinostats.data.repositoryImpl.StatsRepositoryImpl
import com.mooncowpines.kinostats.domain.repository.AuthRepository
import com.mooncowpines.kinostats.domain.repository.ListRepository
import com.mooncowpines.kinostats.domain.repository.MovieRepository
import com.mooncowpines.kinostats.domain.repository.ReviewRepository
import com.mooncowpines.kinostats.domain.repository.StatsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val USE_MOCKS_USERS = false
    private const val USE_MOCKS_MOVIES = true
    private const val USE_MOCKS_REVIEWS = true
    private const val USE_MOCKS_STATS = true

    @Provides
    @Singleton
    fun provideAuthInterceptor(sessionManager: SessionManager): Interceptor {
        return Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()

            sessionManager.fetchAuthToken()?.let { token ->
                requestBuilder.addHeader("Authorization", token)
            }

            chain.proceed(requestBuilder.build())
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    fun provideReviewApi(retrofit: Retrofit): ReviewApi {
        return retrofit.create(ReviewApi::class.java)
    }

    @Provides
    @Singleton
    fun provideStatsApi(retrofit: Retrofit): StatsApi {
        return retrofit.create(StatsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideListApi(retrofit: Retrofit): ListApi {
        return retrofit.create(ListApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthApi, sessionManager: SessionManager): AuthRepository {
        return if (USE_MOCKS_USERS) {
            MockAuthRepositoryImpl()
        } else {
            AuthRepositoryImpl(api, sessionManager)
        }
    }


    @Provides
    @Singleton
    fun provideMovieRepository(api: MovieApi): MovieRepository {
        return if (USE_MOCKS_MOVIES) {
            MockMovieRepositoryImpl()
        } else {
            MovieRepositoryImpl(api)
        }
    }

    @Provides
    @Singleton
    fun provideReviewRepository(api: ReviewApi): ReviewRepository {
        return if (USE_MOCKS_REVIEWS)
        {
            MockReviewRepositoryImpl()
        } else {
            ReviewRepositoryImpl(api)
        }
    }

    @Provides
    @Singleton
    fun provideStatsRepository(api: StatsApi): StatsRepository {
        return if (USE_MOCKS_STATS)
        {
            MockStatsRepositoryImpl()
        } else {
            StatsRepositoryImpl(api)
        }
    }

    @Provides
    @Singleton
    fun provideListRepository(api: ListApi): ListRepository {
        return ListRepositoryImpl(api)
    }
}