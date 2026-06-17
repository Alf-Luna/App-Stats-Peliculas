package com.mooncowpines.kinostats.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.mooncowpines.kinostats.data.local.SessionManager
import com.mooncowpines.kinostats.data.remote.AuthApi
import com.mooncowpines.kinostats.data.remote.HomeApi
import com.mooncowpines.kinostats.data.remote.ListApi
import com.mooncowpines.kinostats.data.remote.MovieApi
import com.mooncowpines.kinostats.data.remote.LogApi
import com.mooncowpines.kinostats.data.remote.StatsApi
import com.mooncowpines.kinostats.data.repository.HomeRepositoryImpl
import com.mooncowpines.kinostats.data.repositoryImpl.AuthRepositoryImpl
import com.mooncowpines.kinostats.data.repositoryImpl.ListRepositoryImpl
import com.mooncowpines.kinostats.data.repositoryImpl.MovieRepositoryImpl
import com.mooncowpines.kinostats.data.repositoryImpl.LogRepositoryImpl
import com.mooncowpines.kinostats.data.repositoryImpl.StatsRepositoryImpl
import com.mooncowpines.kinostats.domain.repository.AuthRepository
import com.mooncowpines.kinostats.domain.repository.HomeRepository
import com.mooncowpines.kinostats.domain.repository.ListRepository
import com.mooncowpines.kinostats.domain.repository.MovieRepository
import com.mooncowpines.kinostats.domain.repository.LogRepository
import com.mooncowpines.kinostats.domain.repository.StatsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideEncryptedSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val prefName = "kinostats_prefs"

        return try {
            EncryptedSharedPreferences.create(
                context,
                prefName,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (e: Exception) {

            context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
                .edit()
                .clear()
                .apply()

            val dir = File(context.applicationInfo.dataDir, "shared_prefs")
            val prefFile = File(dir, "$prefName.xml")
            if (prefFile.exists()) {
                prefFile.delete()
            }

            EncryptedSharedPreferences.create(
                context,
                prefName,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }
    }


    @Provides
    @Singleton
    fun provideSessionManager(prefs: SharedPreferences): SessionManager {
        return SessionManager(prefs)
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(sessionManager: SessionManager): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val path = request.url.encodedPath

            if (path.contains("/login") || path.contains("/users/add")) {
                return@Interceptor chain.proceed(request)
            }

            val requestBuilder = request.newBuilder()
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
            .baseUrl("http://3.214.228.214:8080")
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
    fun provideHomeApi(retrofit: Retrofit): HomeApi {
        return retrofit.create(HomeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLogApi(retrofit: Retrofit): LogApi {
        return retrofit.create(LogApi::class.java)
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
        return AuthRepositoryImpl(api, sessionManager)
    }

    @Provides
    @Singleton
    fun provideHomeRepository(api: HomeApi): HomeRepository {
        return HomeRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(api: MovieApi): MovieRepository {
        return MovieRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideLogRepository(api: LogApi): LogRepository {
        return LogRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideStatsRepository(api: StatsApi): StatsRepository {
        return StatsRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideListRepository(api: ListApi): ListRepository {
        return ListRepositoryImpl(api)
    }
}
