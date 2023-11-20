package com.example.myhotel


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import com.example.myhotel.data.Hotels
import com.example.myhotel.rooms.RoomsActivity
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator


class MainActivity : AppCompatActivity() {

    companion object{
        val HOTEL_NAME = "HOTEL_NAME"

    }

    private val list: RecyclerView by lazy { findViewById(R.id.pager) }
    private val indicator by lazy { findViewById<ScrollingPagerIndicator>(R.id.indicator) }
    private val rating:TextView by lazy { findViewById(R.id.rating) }
    private val name:TextView by lazy { findViewById(R.id.name) }
    private val adress:TextView by lazy { findViewById(R.id.adress) }
    private val price:TextView by lazy { findViewById(R.id.price) }
    private val priceForIt:TextView by lazy { findViewById(R.id.price_for_it) }
    private val relative:RelativeLayout by lazy { findViewById(R.id.relative) }
    private val description:TextView by lazy { findViewById(R.id.description) }
    private val list_button: LinearLayout by lazy { findViewById(R.id.buttons) }
    private var hotelName:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Отель"
        createHotel()
        createButtons()
    }


    private fun createButtons(){
        createButton(R.drawable.emoji_happy,"Удобства")
        createButton(R.drawable.tick_square,"Что включено")
        createButton(R.drawable.close_square,"Что не включено")


    }

    private fun createButton(imageT: Int, nameT: String){
        val inflate = LayoutInflater.from(this)
        val button = inflate.inflate(R.layout.buttin_item,null)
        val name = button.findViewById<TextView>(R.id.name_button)
        val image = button.findViewById<ImageView>(R.id.image_button)
        name.text = nameT
        image.setImageResource(imageT)

        list_button.addView(button)
    }

    private fun createHotel() {
        val model = ViewModelProvider(this)[MyViewModelHotels::class.java]
        model.getPosts()?.observe(this, Observer<Hotels>{
            if (it != null){

                val adapter = ImageHotelAdapter(it.imageUrls)
                list.adapter = adapter
                list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                indicator.attachToRecyclerView(list)

                val snapHelper = LinearSnapHelper()
                snapHelper.attachToRecyclerView(list)

                rating.text = "${it.rating} ${it.ratingName}"
                name.text = it.name
                hotelName = it.name
                adress.text = it.adress
                price.text = "от ${it.minimalPrice} ₽"
                priceForIt.text = " ${it.priceForIt} "
                var first = true
                var id = 1
                var nearTo: View = relative

                for (peculiarity in it.aboutTheHotel.peculiarities) {
                    val textView = TextView(this)
                    val param = RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                    )
                    param.setMargins(0,0,0,20)

                    if (first) {
                        if (nearTo == relative) {
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
                    textView.textSize = 16f
                    id += 1
                    textView.text = peculiarity
                    textView.setBackgroundResource(R.drawable.rounded_gray)
                    textView.maxWidth = relative.width/2
                    textView.setTextColor(Color.parseColor("#828796"))

                    relative.addView(textView,param)

                    first = !first

                }
                description.text = it.aboutTheHotel.description
            }
        })
    }


    fun openRooms(view: View) {
        val childIntent = Intent(this, RoomsActivity::class.java)
        childIntent.putExtra(
            HOTEL_NAME, hotelName
        )
        startActivity(childIntent)
    }

}




