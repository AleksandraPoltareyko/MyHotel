package com.example.myhotel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class ImageHotelAdapter(private val pictures: List<String>): RecyclerView.Adapter<ImageHotelHolder> (){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHotelHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(viewType, parent, false)
        return ImageHotelHolder(view, this)
    }

    override fun getItemViewType(position: Int)=  R.layout.page_image

    override fun onBindViewHolder(holder: ImageHotelHolder, position: Int) = holder.bind(pictures[position])

    override fun getItemCount() = pictures.size
}