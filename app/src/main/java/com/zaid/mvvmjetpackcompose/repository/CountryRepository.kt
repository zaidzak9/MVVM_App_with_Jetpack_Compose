package com.zaid.mvvmjetpackcompose.repository

import com.zaid.mvvmjetpackcompose.data.CountriesApi
import com.zaid.mvvmjetpackcompose.data.remote.responses.CountriesMain
import com.zaid.mvvmjetpackcompose.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class CountryRepository @Inject constructor(
    private val countriesApi: CountriesApi
) {
    suspend fun getCountriesList() :Resource<CountriesMain>{
        val response = try {
            countriesApi.getCountiesList()
        }catch (e : Exception){
            return Resource.Error("Something went wrong : $e")
        }
        return Resource.Success(response)
    }
}