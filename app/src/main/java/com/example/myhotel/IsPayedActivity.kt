package com.example.myhotel

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class IsPayedActivity:AppCompatActivity() {

    private val order: TextView by lazy { findViewById(R.id.order_number) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_is_payed)
        title = "Заказ оплачен"
        order.text = "Подтверждение заказа №${(1..10000).random()} может занять некоторое время (от 1 часа до суток). Как только мы получим ответ от туроператора, вам на почту придет уведомление."

    }

    fun finished(view: View){
        val childIntent = Intent(this, MainActivity::class.java)
        startActivity(childIntent)
    }


}