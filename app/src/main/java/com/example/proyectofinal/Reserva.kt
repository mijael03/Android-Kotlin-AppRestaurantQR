package com.example.proyectofinal

import java.io.Serializable

data class Reserva(
    val code_reserve:String,
    val name:String,
    val phone:String,
    val email:String,
    val tables:String,
    val people:String,
    val date:String,
    val hour:String,
    val message:String,
    val qrimage:String):Serializable {
}