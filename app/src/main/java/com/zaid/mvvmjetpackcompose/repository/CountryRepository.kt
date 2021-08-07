package com.zaid.mvvmjetpackcompose.repository

import com.zaid.mvvmjetpackcompose.data.CountriesApi
import com.zaid.mvvmjetpackcompose.data.remote.responses.CountriesMain
import com.zaid.mvvmjetpackcompose.data.remote.responses.CountriesMainItem
import com.zaid.mvvmjetpackcompose.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@ActivityScoped
class CountryRepository @Inject constructor(
    private val countriesApi: CountriesApi
) {
    suspend fun getCountriesList() :Resource<CountriesMainItem>{
        return try {
            val response = countriesApi.getCountiesList()
            if (response.isSuccessful) {
                response.body()?.let { response ->
                    return@let Resource.Success(response)
                } ?: Resource.Error("An unknown error occurred")
            } else {
                Resource.Error("An unknown error occurred")
            }
        }catch (e : Exception){
            return Resource.Error("Something went wrong : $e")
        }
    }
}