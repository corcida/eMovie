package com.rappi.emovie.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rappi.emovie.data.model.TopRatedMovieRemoteDataSource
import com.rappi.emovie.data.model.TopRatedMovieService
import com.rappi.emovie.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("Authorization", "Bearer " + Constants.token)
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(Constants.url)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder()
        .setLenient()
        .create()

    @Provides
    fun provideTopRatedMovieService(retrofit: Retrofit):
            TopRatedMovieService = retrofit.create(TopRatedMovieService::class.java)

    @Provides
    fun provideTopRatedRemoteDataSource(service: TopRatedMovieService):
            TopRatedMovieRemoteDataSource = TopRatedMovieRemoteDataSource(service)

}