package com.example.myhotel.booking

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RestClientBooking {

    private val serviceBooking: TheBooking

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
        serviceBooking = retrofit.create(TheBooking::class.java)
    }

    companion object {
        const val BASE_URL = "https://run.mocky.io/"

        private val instance = RestClientBooking()

        fun instance(): TheBooking {
            return instance.serviceBooking
        }
    }
}