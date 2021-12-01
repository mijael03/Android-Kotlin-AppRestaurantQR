package com.example.proyectofinal

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.android.synthetic.main.activity_form.reserva_name
import kotlinx.android.synthetic.main.activity_form_delivery.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class form_delivery : AppCompatActivity() {
    private val id:Int=0
    private lateinit var lista:ArrayList<plato_lista>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_delivery)
        val bundle:Bundle?=intent.extras
        lista = bundle?.getSerializable("platos") as ArrayList<plato_lista>
        btn_form_delivery.setOnClickListener(){
            hacerDelivery()
            for(plato in lista){
                sendDetail(id,plato.id,plato.numberInCart)
            }
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    fun hacerDelivery(){
            AsyncTask.execute {
                val nombre = delivery_name.text.toString()
                val phone = delivery_phone.text.toString()
                val correo= delivery_email.text.toString()
                val address = delivery_addres.text.toString()
                val queue = Volley.newRequestQueue(this)
                var url = getString(R.string.urlAPI) + "/pedidos/"
                val postRequest: StringRequest = object : StringRequest(
                    Request.Method.POST, url,
                    Response.Listener { response -> // response
                        Log.e("delivery id",response.get(1).toString())
                        Toast.makeText(
                            applicationContext,
                            "Registro agregado correctamente",
                            Toast.LENGTH_LONG
                        ).show()
                    },
                    Response.ErrorListener { response ->// error
                        Log.e("error",response.toString())
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
                        params["address"] = address
                        Log.e("s", params.toString())
                        return params

                    }
                }
                queue.add(postRequest)
            }

    }
    fun sendDetail(idpedido:Int,idproducto:Int,quantity:Int){

        AsyncTask.execute {

            val queue = Volley.newRequestQueue(this)
            var url = getString(R.string.urlAPI) + "/pedidos/$idpedido/detalles"
            val postRequest: StringRequest = object : StringRequest(
                Request.Method.POST, url,
                Response.Listener { response -> // response

                    Toast.makeText(
                        applicationContext,
                        "Registro agregado correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                },
                Response.ErrorListener { response ->// error
                    Log.e("error",response.toString())
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
                    params["idpedido"] = idpedido.toString()
                    params["product"] = idproducto.toString()
                    params["quantity"] = quantity.toString()
                    Log.e("s", params.toString())
                    return params

                }
            }
            queue.add(postRequest)
        }
    }
}