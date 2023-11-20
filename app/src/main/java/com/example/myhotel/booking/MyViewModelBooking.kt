package com.example.myhotel.booking

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myhotel.booking.data.Booking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyViewModelBooking:ViewModel() {
    var mutableLiveDataBooking: MutableLiveData<Booking>? = null
    fun getPosts() : MutableLiveData<Booking>? {
        mutableLiveDataBooking = MutableLiveData()
        loadMore()
        return mutableLiveDataBooking
    }

    private fun loadMore() {

        RestClientBooking.instance().get().enqueue(object : Callback<Booking> {
            override fun onResponse(call: Call<Booking>, response: Response<Booking>) {
                if(response.code() < 400) {
                    mutableLiveDataBooking!!.value = response.body()
                }
            }

            override fun onFailure(call: Call<Booking>, t: Throwable) {
                Log.d("happy", "onFailure: ${t.message}")
            }
        }
        )
    }
}