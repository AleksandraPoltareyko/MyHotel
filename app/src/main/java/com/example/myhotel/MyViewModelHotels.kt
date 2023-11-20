package com.example.myhotel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myhotel.data.Hotels
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyViewModelHotels : ViewModel(){

    var mutableLiveDataHotels: MutableLiveData<Hotels>? = null
    fun getPosts() : MutableLiveData<Hotels>? {
        mutableLiveDataHotels = MutableLiveData()
        loadMore()
        return mutableLiveDataHotels
    }

    private fun loadMore() {

        RestClientHotels.instance().get().enqueue(object : Callback<Hotels> {
            override fun onResponse(call: Call<Hotels>, response: Response<Hotels>) {
                if(response.code() < 400) {
                    mutableLiveDataHotels!!.value = response.body()
                }
            }

            override fun onFailure(call: Call<Hotels>, t: Throwable) {
                Log.d("happy", "onFailure: ${t.message}")
            }
        }
        )
    }

}