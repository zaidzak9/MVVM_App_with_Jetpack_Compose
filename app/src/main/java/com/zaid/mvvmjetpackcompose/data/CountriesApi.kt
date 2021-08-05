package com.zaid.mvvmjetpackcompose.data

import com.zaid.mvvmjetpackcompose.data.remote.responses.CountriesMain
import retrofit2.http.GET

interface CountriesApi {
    @GET("all")
    suspend fun getCountiesList():CountriesMain
}