package com.example.myhotel

import com.example.myhotel.data.Hotels
import retrofit2.Call
import retrofit2.http.GET

//https://run.mocky.io/v3/d144777c-a67f-4e35-867a-cacc3b827473

interface TheHotel {
    @GET("/v3/d144777c-a67f-4e35-867a-cacc3b827473")
    fun get(): Call<Hotels>
}