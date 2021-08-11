package com.zaid.mvvmjetpackcompose.data
import com.zaid.mvvmjetpackcompose.data.remote.responses.CountriesMain
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CountriesApi {
    @GET("all")
    suspend fun getCountiesList():Response<CountriesMain>

    @GET("name/{country}")
    suspend fun getCountry(
        @Path("country") country: String
    ):Response<CountriesMain>
}