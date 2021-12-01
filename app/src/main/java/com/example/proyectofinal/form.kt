package com.example.proyectofinal

import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.proyectofinal.orm.ReservaRepository
import kotlinx.android.synthetic.main.activity_form.*
import java.sql.Date
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class form : AppCompatActivity() {
    private lateinit var code_reserve:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        reserva_date.setOnClickListener(){

            showDatePickerDialog()
        }
        btn_Form.setOnClickListener(){
            hacerReserva()
            val reporeserva=ReservaRepository()
            reporeserva.crear(code_reserve)
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    fun showDatePickerDialog(){
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }
    fun onDateSelected(day:Int,month:Int,year:Int){

        val newday=parse(year)
        val newmonth=parse(month+1)

        reserva_date.setText("$day-$newmonth-$newday")
    }

    fun hacerReserva(){
        AsyncTask.execute {
            val nombre = reserva_name.text.toString()
            val phone = reserva_phone.text.toString()
            val correo= reserva_email.text.toString()
            val nrotables = reserva_tables.text.toString()
            val nropeople = reserva_people.text.toString()
            val fecha = reserva_date.text.toString()
            val message = reserva_message.text.toString()
            val date=LocalDateTime.now()
            val formatter=DateTimeFormatter.ofPattern("HH:mm")
            val rsrv_hour=date.format(formatter)
            val queue = Volley.newRequestQueue(this)
            var url = getString(R.string.urlAPI) + "/reservas/"
            val postRequest: StringRequest = object : StringRequest(
                Request.Method.POST, url,
                Response.Listener { response -> // response
//                    code_reserve= response.get(0).toString()
                    Log.e("error",response.get(1).toString())
                    Toast.makeText(
                        applicationContext,
                        "Registro agregado correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                },
                Response.ErrorListener { response ->// error
                    Log.e("key",response.toString())
                    Toast.makeText(
                        applicationContext,
                        "Se produjo un error al guardar los datos",
                        Toast.LENGTH_LONG
                    ).show()
                }
            ) {
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> =
                        HashMap()
                    params["name"] = nombre
                    params["phone"] = phone
                    params["email"] = correo
                    params["tables"] = nrotables
                    params["people"] = nropeople
                    params["rsrv_date"] = fecha
                    params["rsrv_hour"] = rsrv_hour
                    params["message"] = message
                    Log.e("s", params.toString())
                    return params

                }
            }
            queue.add(postRequest)
        }
    }
    fun parse(n:Int):String{
        if(n<=9){
            return "0"+n.toString()
        }else{
            return n.toString()
        }

    }
}