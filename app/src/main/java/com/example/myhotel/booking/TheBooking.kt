package com.example.myhotel.booking

import com.example.myhotel.booking.data.Booking
import retrofit2.Call
import retrofit2.http.GET

//https://run.mocky.io
// /v3/63866c74-d593-432c-af8e-f279d1a8d2ff
interface TheBooking {
    @GET("/v3/63866c74-d593-432c-af8e-f279d1a8d2ff")
    fun get(): Call<Booking>
}