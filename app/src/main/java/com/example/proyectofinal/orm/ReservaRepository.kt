package com.example.proyectofinal.orm

import com.orm.SugarRecord

class ReservaRepository {
    fun crear(code_reserve:String){
        var reserva=Code_Reserva(code_reserve)
        SugarRecord.save(reserva)
    }
    fun listar():List<Code_Reserva>{
        var reservas=SugarRecord.listAll(Code_Reserva::class.java)
        return reservas
    }
}