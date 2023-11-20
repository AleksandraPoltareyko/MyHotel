package com.example.myhotel.rooms.data


import com.google.gson.annotations.SerializedName

data class Rooms(
    @SerializedName("rooms")
    val rooms: List<Room>
)