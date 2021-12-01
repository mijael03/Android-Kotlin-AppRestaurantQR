package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.load
import kotlinx.android.synthetic.main.activity_pedido_carrito.*
import kotlinx.android.synthetic.main.activity_plato_detalle.*
import kotlinx.android.synthetic.main.activity_reserva_detalle.*

class Reserva_Detalle : AppCompatActivity() {
    private lateinit var reserva:Reserva
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserva_detalle)
        val bundle:Bundle?=intent.extras
        if(bundle!=null) {
            reserva = bundle.getSerializable("reserva") as Reserva
            reserv_pic.load(reserva.qrimage)
            reserv_date.text=reserva.date
            reserv_hour.text=reserva.hour
            reserv_message.text=reserva.message
            reserv_tables.text=reserva.tables
        }
    }
}