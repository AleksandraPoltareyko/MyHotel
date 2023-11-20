package com.example.myhotel.rooms

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myhotel.rooms.data.Rooms
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyViewModelRooms: ViewModel() {
    var mutableLiveDataRooms: MutableLiveData<Rooms>? = null
    fun getPosts() : MutableLiveData<Rooms>? {
        mutableLiveDataRooms = MutableLiveData()
        loadMore()
        return mutableLiveDataRooms
    }

    private fun loadMore() {

        RestClientRooms.instance().get().enqueue(object : Callback<Rooms> {
            override fun onResponse(call: Call<Rooms>, response: Response<Rooms>) {
                if(response.code() < 400) {
                    mutableLiveDataRooms!!.value = response.body()
                }
            }

            override fun onFailure(call: Call<Rooms>, t: Throwable) {
                Log.d("happy", "onFailure: ${t.message}")
            }
        }
        )
    }
}