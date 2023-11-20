package com.example.myhotel.rooms

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RestClientRooms {
    private val serviceRooms: TheRoom

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
        serviceRooms = retrofit.create(TheRoom::class.java)
    }

    companion object {
        const val BASE_URL = "https://run.mocky.io/"

        private val instance = RestClientRooms()

        fun instance(): TheRoom {
            return instance.serviceRooms
        }
    }
}