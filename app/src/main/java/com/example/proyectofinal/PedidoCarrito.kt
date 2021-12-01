package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_pedido_carrito.*

class PedidoCarrito : AppCompatActivity() {
    private lateinit var carrito:ManagementCard
    var tax:Double=0.00
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido_carrito)
        carrito= ManagementCard(applicationContext)
        cargarCarrito()
        calculateCard()
        card_btn1.setOnClickListener()
        {
            val intent: Intent = Intent(this,PedidoCarrito::class.java)
            startActivity(intent)
        }
        homeBtn1.setOnClickListener(){
            val intent: Intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        btnCheckout.setOnClickListener(){
            val intent: Intent = Intent(this,form_delivery::class.java)
            intent.putExtra("platos",carrito.getListCard())
            startActivity(intent)
        }
    }
   fun cargarCarrito(){

       lista_platos.addItemDecoration(
            DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL)
        )
        lista_platos.layoutManager= LinearLayoutManager(this)
        val adapter=CardAdapter(carrito.getListCard(),applicationContext, object:ChangeNumberItemsListener{
            override fun changed() {
                calculateCard()
            }
        })
        lista_platos.adapter=adapter
        if(carrito.getListCard().isEmpty()){
            emptyTxt.setVisibility(View.VISIBLE)
            scrollView4.setVisibility(View.GONE)
        }else{
            emptyTxt.setVisibility(View.GONE)
            scrollView4.setVisibility(View.VISIBLE)
        }
    }
    fun calculateCard(){
        val percentTax=0.02
        val delivery=10.0
        tax=Math.round((carrito.getTotal()*percentTax)*100.0)/100.0
        val total:Double=Math.round((carrito.getTotal()+tax+delivery)*100.0)/100.0
        val itemtotal=(carrito.getTotal()*100.0)/100.0
        totalFeeTxt.text="$"+itemtotal
        taxTxt.text="$"+tax
        deliveryTxt.text="$"+delivery
        totalTxt.text="$"+total

    }
}