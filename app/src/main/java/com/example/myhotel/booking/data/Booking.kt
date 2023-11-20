package com.example.myhotel.booking.data


import com.google.gson.annotations.SerializedName

data class Booking(
    @SerializedName("arrival_country")
    val arrivalCountry: String,
    @SerializedName("departure")
    val departure: String,
    @SerializedName("fuel_charge")
    val fuelCharge: Int,
    @SerializedName("horating")
    val horating: Int,
    @SerializedName("hotel_adress")
    val hotelAdress: String,
    @SerializedName("hotel_name")
    val hotelName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("number_of_nights")
    val numberOfNights: Int,
    @SerializedName("nutrition")
    val nutrition: String,
    @SerializedName("rating_name")
    val ratingName: String,
    @SerializedName("room")
    val room: String,
    @SerializedName("service_charge")
    val serviceCharge: Int,
    @SerializedName("tour_date_start")
    val tourDateStart: String,
    @SerializedName("tour_date_stop")
    val tourDateStop: String,
    @SerializedName("tour_price")
    val tourPrice: Int
)