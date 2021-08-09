package com.zaid.mvvmjetpackcompose.ui

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.zaid.mvvmjetpackcompose.data.remote.responses.CountriesMain
import com.zaid.mvvmjetpackcompose.data.remote.responses.CountriesMainItem
import com.zaid.mvvmjetpackcompose.repository.CountryRepository
import com.zaid.mvvmjetpackcompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val repository: CountryRepository
):ViewModel(){

    var countriesList = mutableStateOf<List<CountriesMainItem>>(listOf())
    var loadError = mutableStateOf("")
    var isloading = mutableStateOf(false)

    init {
        getCountriesList()
    }

    private fun getCountriesList(){
        viewModelScope.launch {
            isloading.value = true
            when(val result = repository.getCountriesList()){
                is Resource.Success ->{
                    println("JPC VM_SUCCESS : ${result.data!![0]}")
                    countriesList.value = result.data
                    loadError.value = ""
                    isloading.value = false
                }
                is Resource.Error -> {
                    println("JPC VM_ERROR : ${result.error}")
                    loadError.value = result.error!!
                    isloading.value = false
                }
            }
        }
    }


    fun calcDominantColor(drawable:Drawable,onFinish:(Color) -> Unit){
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888,true)

        Palette.from(bmp).generate{palette->
            palette?.dominantSwatch?.rgb?.let { colorVal->
                onFinish(Color(colorVal))
            }
        }
    }
}