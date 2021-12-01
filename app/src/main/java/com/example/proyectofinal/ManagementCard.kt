package com.example.proyectofinal

import android.content.Context
import android.widget.Toast
import java.io.Serializable

class ManagementCard:Serializable {
    var context:Context
    var tinyDB:TinyDB
    constructor(context: Context){
        this.context=context
        this.tinyDB=TinyDB(context)
    }
    fun insertFood(plato:plato_lista){
        val listFood:ArrayList<plato_lista> = getListCard()
        var existAlready:Boolean=false
        var n=0
        for (i in listFood.indices) {
            if (listFood[i].title == plato.title) {
                existAlready = true
                n = i
                break
            }
        }
        if(existAlready){
            listFood.get(n).numberInCart=plato.numberInCart
        }else{
            listFood.add(plato)
        }
        tinyDB.putListObject("CardList",listFood)
        Toast.makeText(context, "Added To Your Card", Toast.LENGTH_SHORT).show()
    }
    fun getListCard(): ArrayList<plato_lista> {
        return tinyDB.getListObject("CardList")
    }
    fun plusNumberFood( listfood:ArrayList<plato_lista>,position:Int,changeNumberItemsListener:ChangeNumberItemsListener){
        listfood[position].numberInCart=listfood[position].numberInCart+1
        tinyDB.putListObject("CardList",listfood)
        changeNumberItemsListener.changed()
    }
    fun minusNumberFood( listfood:ArrayList<plato_lista>,position:Int,changeNumberItemsListener:ChangeNumberItemsListener){
        val food:plato_lista=listfood[position]
        if(food.numberInCart===1){
            listfood.removeAt(position)
        }else{
            listfood[position].numberInCart=listfood[position].numberInCart-1
        }
        tinyDB.putListObject("CardList",listfood)
        changeNumberItemsListener.changed()
    }
    fun getTotal():Double{
        val listFood2:ArrayList<plato_lista> = getListCard()
        var fee:Double=0.0
        for (i in listFood2.indices) {
            fee = fee + listFood2[i].fee * listFood2[i].numberInCart
        }
        return fee
    }

}