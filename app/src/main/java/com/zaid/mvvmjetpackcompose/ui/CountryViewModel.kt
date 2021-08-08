package com.zaid.mvvmjetpackcompose.ui

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.palette.graphics.Palette
import com.zaid.mvvmjetpackcompose.repository.CountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val repository: CountryRepository
):ViewModel(){

    fun calcDominantColor(drawable:Drawable,onFinish:(Color) -> Unit){
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888,true)

        Palette.from(bmp).generate{palette->
            palette?.dominantSwatch?.rgb?.let { colorVal->
                onFinish(Color(colorVal))
            }
        }
    }
}