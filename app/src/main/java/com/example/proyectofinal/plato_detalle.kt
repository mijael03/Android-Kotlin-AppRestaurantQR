package com.example.proyectofinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import coil.load
import kotlinx.android.synthetic.main.activity_plato_detalle.*

class plato_detalle : AppCompatActivity() {
    private var quantity=1
    private lateinit var plato:plato_lista
    private lateinit var card:ManagementCard
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plato_detalle)
        card= ManagementCard(applicationContext)
        val bundle:Bundle?=intent.extras
        if(bundle!=null){
            plato= bundle.getSerializable("plato") as plato_lista
            titleTxt.setText(plato.title)
            priceTxt.setText(plato.fee.toString())
            foodPic.load(plato.pic)
            descriptionTxt.setText(plato.description)

        }
        plusBtn.setOnClickListener(){
            quantity=quantity+1
            numberOrderTxt.setText(quantity.toString())
        }
        minusBtn.setOnClickListener(){
            if(quantity>1){
                quantity=quantity-1
            }
            numberOrderTxt.setText(quantity.toString())
        }
        addToCardBtn.setOnClickListener(){
            plato.numberInCart=quantity
            card.insertFood(plato)
            Toast.makeText(applicationContext,"Añadido", Toast.LENGTH_SHORT).show()

        }
    }
}