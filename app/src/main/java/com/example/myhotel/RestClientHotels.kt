package com.example.myhotel

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RestClientHotels {

    private val serviceHotels: TheHotel

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
        serviceHotels = retrofit.create(TheHotel::class.java)
    }

    companion object {
        const val BASE_URL = "https://run.mocky.io/"

        private val instance = RestClientHotels()

        fun instance(): TheHotel {
            return instance.serviceHotels
        }
    }
}