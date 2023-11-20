package com.example.myhotel

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ImageHotelHolder(itemView: View, adapter: ImageHotelAdapter): RecyclerView.ViewHolder(itemView) {

    private val image:ImageView = itemView.findViewById(R.id.picture)

    public fun bind(picture: String){
        Picasso
            .get()
            .load(picture)
            .into(image)
    }
}