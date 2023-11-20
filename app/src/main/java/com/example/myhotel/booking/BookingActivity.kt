package com.example.myhotel.booking

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myhotel.IsPayedActivity
import com.example.myhotel.R
import com.example.myhotel.booking.data.Booking
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern

class BookingActivity:AppCompatActivity() {

    private val name: TextView by lazy { findViewById(R.id.name_booking) }
    private val rating: TextView by lazy { findViewById(R.id.rating_booking) }
    private val adress: TextView by lazy { findViewById(R.id.adress_booking) }
    private val tableLayout: TableLayout by lazy { findViewById(R.id.table_layout_booking) }
    private val tableLayoutPrice: TableLayout by lazy { findViewById(R.id.table_layout_booking_price) }
    private val phone:EditText by lazy {findViewById(R.id.edit_phone)}
    private val email:EditText by lazy {findViewById(R.id.edit_email)}
    private val tourists:LinearLayout by lazy {findViewById(R.id.tourists)}
    private val buttonIsPayed:Button by lazy {findViewById(R.id.button_is_payed)}
    private val listOfTouristsView: MutableList<View> = mutableListOf()
    private val email_til:TextInputLayout by lazy {findViewById(R.id.edit_email_til)}
    private val numbersBook = mapOf(
        Pair(1,"Первый"),
        Pair(2,"Второй"),
        Pair(3,"Третий"),
        Pair(4,"Четвертый"),
        Pair(5,"Пятый"),
        Pair(6,"Шестой"),
        Pair(7,"Седьмой"),
        Pair(8,"Восьмой"),
        Pair(9,"Девятый"),
        Pair(10,"Десятый")

    )

    private var countTourists: Int = 0
    private var tourPrice: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)
        title = "Бронирование"

        createBooking()
    }

    private fun createBooking() {
        val model = ViewModelProvider(this)[MyViewModelBooking::class.java]
        model.getPosts()?.observe(this, Observer<Booking>{
            if (it != null){
                name.text = it.hotelName
                rating.text = it.ratingName
                adress.text = it.hotelAdress
                tableLayout.addView(createTableRow("Вылет из",it.departure))
                tableLayout.addView(createTableRow("Страна, город",it.arrivalCountry))
                tableLayout.addView(createTableRow("Даты","${it.tourDateStart} - ${it.tourDateStop}"))
                tableLayout.addView(createTableRow("Кол-во ночей", it.numberOfNights.toString()))
                tableLayout.addView(createTableRow("Отель",it.hotelName))
                tableLayout.addView(createTableRow("Номер",it.room))
                tableLayout.addView(createTableRow("Питание",it.nutrition))

                email.addTextChangedListener(object:TextWatcher{
                    var isValid = false

                    override fun afterTextChanged(editableText: Editable) {
                        isValid = isValidEmail(editableText)
                        if(!isValid){
                            email.setBackgroundResource( R.drawable.for_edit)
                            email_til.setBackgroundResource( R.drawable.for_edit)
                        }else{
                            email.setBackgroundResource( R.drawable.for_edit_norm)
                            email_til.setBackgroundResource( R.drawable.for_edit_norm)
                        }
                    }

                    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit

                    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = Unit

                        private val EMAIL_PATTERN = Pattern.compile(
                            Patterns.EMAIL_ADDRESS.pattern()
                        )

                        fun isValidEmail(email: CharSequence?): Boolean {
                            return email != null && EMAIL_PATTERN.matcher(email).matches()
                        }


                })

                phone.addTextChangedListener(object:TextWatcher{
                    private var mSelfChange = false
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) { }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (s == null || mSelfChange) {
                            return
                        }

                        val preparedStr = s.replace(Regex("(\\D*)"), "")
                        var resultStr = ""
                        preparedStr.indices.last
                        for (i in preparedStr.indices) {
                            resultStr = when (i) {
                                0 -> resultStr.plus("+7 (")
                                1 -> resultStr.plus(preparedStr[i])
                                2 -> resultStr.plus(preparedStr[i])
                                3 -> resultStr.plus(preparedStr[i])
                                4 -> resultStr.plus(") ".plus(preparedStr[i]))
                                5 -> resultStr.plus(preparedStr[i])
                                6 -> resultStr.plus(preparedStr[i])
                                7 -> resultStr.plus("-".plus(preparedStr[i]))
                                8 -> resultStr.plus(preparedStr[i])
                                9 -> resultStr.plus("-".plus(preparedStr[i]))
                                10 -> resultStr.plus(preparedStr[i])
                                else -> resultStr
                            }
                        }

                        mSelfChange = true

                        val oldSelectionPos = phone.selectionStart
                        val isEdit = phone.selectionStart != phone.length()
                        phone.setText(resultStr)
                        if (isEdit) {
                            phone.setSelection(if (oldSelectionPos > resultStr.length) resultStr.length else oldSelectionPos)
                        } else {
                            phone.setSelection(resultStr.length)
                        }
                        mSelfChange = false
                    }

                    override fun afterTextChanged(s: Editable?) {

                    }

                })
                addTourist()
                tableLayoutPrice.addView(createTableRowPrice("Тур","${it.tourPrice} ₽"))
                tableLayoutPrice.addView(createTableRowPrice("Топливный сбор","${it.fuelCharge} ₽"))
                tableLayoutPrice.addView(createTableRowPrice("Сервисный сбор","${it.serviceCharge} ₽"))
                tourPrice = it.tourPrice + it.fuelCharge + it.serviceCharge
                tableLayoutPrice.addView(createTableRowPrice("К оплате","$tourPrice ₽"))
                buttonIsPayed.text = "Оплатить $tourPrice ₽"
            }
        })
    }

    private fun createTableRowPrice(name: String, param: String): TableRow {
        val tableRow = TableRow(this)
        val paramR = TableRow.LayoutParams(
            0,
            TableRow.LayoutParams.WRAP_CONTENT,
            0.5f
        )
        val tv = TextView(this)
        tv.text = name
        tv.setTextColor(Color.parseColor("#828796"))
        tv.textSize = 16f

        tableRow.addView(tv,paramR)
        val paramR2 = TableRow.LayoutParams(
            0,
            TableRow.LayoutParams.WRAP_CONTENT,
            0.5f
        )

        val tvParam = TextView(this)
        tvParam.text = param
        tvParam.setTextColor(Color.parseColor("#000000"))
        tvParam.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_END
        tvParam.textSize = 16f

        tableRow.addView(tvParam,paramR2)
        return tableRow
    }

    private fun createTableRow(name: String, param: String): TableRow {
        val tableRow = TableRow(this)
        val paramR = TableRow.LayoutParams(
            0,
            TableRow.LayoutParams.WRAP_CONTENT,
            0.4f
        )
        val tv = TextView(this)
        tv.text = name
        tv.setTextColor(Color.parseColor("#828796"))


        tableRow.addView(tv,paramR)
        val paramR2 = TableRow.LayoutParams(
            0,
            TableRow.LayoutParams.WRAP_CONTENT,
            0.6f
        )
        val tvParam = TextView(this)
        tvParam.text = param
        tvParam.setTextColor(Color.parseColor("#000000"))


        tableRow.addView(tvParam,paramR2)

        return tableRow
    }

    private fun addTourist(){

       countTourists = countTourists + 1
        if (countTourists<=10) {
            val inflate = LayoutInflater.from(this)
            val tourist_all = inflate.inflate(R.layout.tourist_all, null)
            tourist_all.findViewById<TextView>(R.id.tourist_number_big).text = "${numbersBook[countTourists]} турист"
            val tas = tourist_all.findViewById<TextView>(R.id.tourist_number_small)
            tas.text = "${numbersBook[countTourists]} турист"
            tourist_all.findViewById<RelativeLayout>(R.id.tourist_all_small).visibility = View.GONE
            listOfTouristsView.add(tourist_all)
            tourists.addView(tourist_all)
        }else{
            Toast.makeText(this, "Нельзя добавиьть более 10 туристов", Toast.LENGTH_SHORT).show()
        }

    }
    fun addTourist(view: View){
        addTourist()
    }

    fun hideTourist(view: View) {
        val tas = view.parent.parent
        if (tas is View) {
            tas.findViewById<RelativeLayout>(R.id.tourist_all_small).visibility = View.VISIBLE
            tas.findViewById<RelativeLayout>(R.id.tourist_all_big).visibility = View.GONE
            tas.findViewById<LinearLayout>(R.id.tourist_all_info).visibility = View.GONE
        }
    }

    fun seekTourist(view: View) {
        val tas = view.parent.parent
        if (tas is View) {
            tas.findViewById<RelativeLayout>(R.id.tourist_all_small).visibility = View.GONE
            tas.findViewById<RelativeLayout>(R.id.tourist_all_big).visibility = View.VISIBLE
            tas.findViewById<LinearLayout>(R.id.tourist_all_info).visibility = View.VISIBLE
        }
    }

    fun isPayed(view: View){
        var isOk = true
        for (viewAll in listOfTouristsView){
            val vName = viewAll.findViewById<EditText>(R.id.tourist_name)
            val vNameTIL = viewAll.findViewById<TextInputLayout>(R.id.tourist_name_til)
            if (vName.text.isEmpty()){
                isOk = false
                vName.setBackgroundResource( R.drawable.for_edit)
                vNameTIL.setBackgroundResource( R.drawable.for_edit)
            }else{
                vName.setBackgroundResource( R.drawable.for_edit_norm)
                vNameTIL.setBackgroundResource( R.drawable.for_edit_norm)
            }
            val vLastName = viewAll.findViewById<EditText>(R.id.tourist_lastname)
            val vLastNameTIL = viewAll.findViewById<TextInputLayout>(R.id.tourist_lastname_til)
            if (vLastName.text.isEmpty()){
                isOk = false
                vLastName.setBackgroundResource( R.drawable.for_edit)
                vLastNameTIL.setBackgroundResource( R.drawable.for_edit)
            }else{
                vLastName.setBackgroundResource( R.drawable.for_edit_norm)
                vLastNameTIL.setBackgroundResource( R.drawable.for_edit_norm)
            }
            val vBirthDate = viewAll.findViewById<EditText>(R.id.tourist_birth_date)
            val vBirthDate_TIL = viewAll.findViewById<TextInputLayout>(R.id.tourist_birth_date_til)
            if (vBirthDate.text.isEmpty()){
                isOk = false
                vBirthDate.setBackgroundResource( R.drawable.for_edit)
                vBirthDate_TIL.setBackgroundResource( R.drawable.for_edit)
            }else{
                vBirthDate.setBackgroundResource( R.drawable.for_edit_norm)
                vBirthDate_TIL.setBackgroundResource( R.drawable.for_edit_norm)
            }
            val vNationality = viewAll.findViewById<EditText>(R.id.tourist_nationality)
            val vNationality_TIL = viewAll.findViewById<TextInputLayout>(R.id.tourist_nationality_til)
            if (vNationality.text.isEmpty()){
                isOk = false
                vNationality.setBackgroundResource( R.drawable.for_edit)
                vNationality_TIL.setBackgroundResource( R.drawable.for_edit)
            }else{
                vNationality.setBackgroundResource( R.drawable.for_edit_norm)
                vNationality_TIL.setBackgroundResource( R.drawable.for_edit_norm)
            }
            val vPassport= viewAll.findViewById<EditText>(R.id.tourist_passport)
            val vPassport_TIL= viewAll.findViewById<TextInputLayout>(R.id.tourist_passport_til)
            if (vPassport.text.isEmpty()){
                isOk = false
                vPassport.setBackgroundResource( R.drawable.for_edit)
                vPassport_TIL.setBackgroundResource( R.drawable.for_edit)
            }else{
                vPassport.setBackgroundResource( R.drawable.for_edit_norm)
                vPassport_TIL.setBackgroundResource( R.drawable.for_edit_norm)
            }
            val vPassportDateEnd = viewAll.findViewById<EditText>(R.id.tourist_passport_date_end)
            val vPassportDateEnd_TIL = viewAll.findViewById<TextInputLayout>(R.id.tourist_passport_date_end_til)
            if (vPassportDateEnd.text.isEmpty()){
                isOk = false
                vPassportDateEnd.setBackgroundResource( R.drawable.for_edit)
                vPassportDateEnd_TIL.setBackgroundResource( R.drawable.for_edit)
            }else{
                vPassportDateEnd.setBackgroundResource( R.drawable.for_edit_norm)
                vPassportDateEnd_TIL.setBackgroundResource( R.drawable.for_edit_norm)
            }
        }

        if (isOk) {
            val childIntent = Intent(this, IsPayedActivity::class.java)
            startActivity(childIntent)
        }else{
            Toast.makeText(this, "Введены не все данные туристов!", Toast.LENGTH_SHORT).show()
        }
    }



}