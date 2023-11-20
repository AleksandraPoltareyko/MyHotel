package com.example.myhotel.rooms

import com.example.myhotel.rooms.data.Rooms
import retrofit2.Call
import retrofit2.http.GET

//https://run.mocky.io/
// v3/8b532701-709e-4194-a41c-1a903af00195

interface TheRoom {
    @GET("/v3/8b532701-709e-4194-a41c-1a903af00195")
    fun get(): Call<Rooms>
}