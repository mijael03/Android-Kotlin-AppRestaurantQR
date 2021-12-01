package com.example.proyectofinal

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.proyectofinal.orm.ReservaRepository
import kotlinx.android.synthetic.main.activity_lista_reserva.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException

class Lista_Reserva : AppCompatActivity() {
    var repo=ReservaRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_reserva)

    }
    fun cargarListaReservas(){
        var llenarreservas= ArrayList<Reserva>()
        var listaidreservas=repo.listar()
        for (reserva in listaidreservas){
            AsyncTask.execute {
                val queue = Volley.newRequestQueue(applicationContext)
                val url = getString(R.string.urlAPI) + "/reservas/${reserva.code_reserve}"
                val stringRequest = JsonObjectRequest(Request.Method.GET,url,null,
                    { response ->
                        try {
                           var name=response.getString("name")
                            var phone=response.getString("phone")
                            var email=response.getString("email")
                            var tables=response.getString("tables")
                            var people=response.getString("people")
                            var datereserve=response.getString("rsrv_date")
                            var hourreserve=response.getString("rsrv_hour")
                            var message=response.getString("message")
                            var qr_image=getString(R.string.urlStatic)+response.getString("qr_image")

                            llenarreservas.add(Reserva(reserva.code_reserve,name,phone,email,tables,people,datereserve,hourreserve,message,qr_image))
                            val adapter = ReservaAdapter(llenarreservas, applicationContext)
                            lista_reservas.adapter = adapter
                        } catch (e: JSONException) {

                            Toast.makeText(
                                applicationContext,
                                "Error al obtener los datos",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }, {

                        Toast.makeText(
                            applicationContext,
                            "Verifique que esta conectado a internet",
                            Toast.LENGTH_LONG
                        ).show()
                    })
                queue.add(stringRequest)
            }
        }
    }

}