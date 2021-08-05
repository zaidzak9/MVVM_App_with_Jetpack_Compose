package com.zaid.mvvmjetpackcompose.di

import com.zaid.mvvmjetpackcompose.data.CountriesApi
import com.zaid.mvvmjetpackcompose.repository.CountryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(Singleton::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCountryApi(): CountriesApi {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://restcountries.eu/rest/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(CountriesApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(
        countriesApi: CountriesApi
    ) = CountryRepository(countriesApi)
}