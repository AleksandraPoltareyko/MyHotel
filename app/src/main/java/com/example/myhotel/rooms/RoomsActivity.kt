package com.example.myhotel.rooms

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TableLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myhotel.ImageHotelAdapter
import com.example.myhotel.MainActivity
import com.example.myhotel.R
import com.example.myhotel.booking.BookingActivity
import com.example.myhotel.rooms.data.Rooms
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator


class RoomsActivity:AppCompatActivity() {

    val table: TableLayout by lazy { findViewById(R.id.tableLayout) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rooms)

        val text:String = intent.getStringExtra(MainActivity.HOTEL_NAME) ?: ""
        title = text

        createTable()
    }

    private fun createTable() {

        val inflate = LayoutInflater.from(this)
        val model = ViewModelProvider(this)[MyViewModelRooms::class.java]
        model.getPosts()?.observe(this, Observer<Rooms>{
            if (it != null){
                for (room in it.rooms){
                    val tableRow = inflate.inflate(R.layout.table_row_room,null)
                    val nameRoom = tableRow.findViewById<TextView>(R.id.room_name)
                    val relativeRoom = tableRow.findViewById<RelativeLayout>(R.id.relative_room)
                    val listRoom = tableRow.findViewById<RecyclerView>(R.id.pager_room)
                    val indicator = tableRow.findViewById<ScrollingPagerIndicator>(R.id.indicator_room)
                    val price = tableRow.findViewById<TextView>(R.id.price_room)
                    val pricePer = tableRow.findViewById<TextView>(R.id.price_per_room)

                    val adapter = ImageHotelAdapter(room.imageUrls)
                    listRoom.adapter = adapter
                    listRoom.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    indicator.attachToRecyclerView(listRoom)

                    val snapHelper = LinearSnapHelper()
                    snapHelper.attachToRecyclerView(listRoom)

                    nameRoom.text = room.name
                    var first = true
                    var id = 1
                    var nearTo: View = relativeRoom
                    for (peculiarity in room.peculiarities){
                        val textView = TextView(this)
                        val param = RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT
                        )
                        param.setMargins(10,0,10,10)
                        if (first) {
                            if (nearTo == relativeRoom) {
                                param.addRule(RelativeLayout.ALIGN_PARENT_LEFT)

                            } else {
                                param.addRule(RelativeLayout.BELOW, nearTo.id)
                            }
                            nearTo = textView
                        } else {
                            param.addRule(RelativeLayout.RIGHT_OF, nearTo.id)
                            param.addRule(RelativeLayout.ALIGN_TOP, nearTo.id)

                        }

                        textView.id = id
                       id += 1
                        textView.text = peculiarity
                        textView.setTextColor(Color.parseColor("#828796"))
                        textView.setBackgroundResource(R.drawable.rounded_gray)
                        relativeRoom.addView(textView,param)

                        first = !first
                    }
                    price.text = "${room.price.toString()} ₽   "
                    pricePer.text = room.pricePer

                    table.addView(tableRow)
                }
            }
        })
    }

    fun booking(view: View) {
        val childIntent = Intent(this, BookingActivity::class.java)
        startActivity(childIntent)
    }

}