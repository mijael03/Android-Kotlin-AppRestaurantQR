package com.example.proyectofinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_lista_platos.*

class ListaPlatos : AppCompatActivity() {
    private lateinit var adapter:ListAdapter
    private lateinit var lista:ArrayList<plato_lista>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_platos)
        val bundle:Bundle?=intent.extras
        lista_platos.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        lista_platos.layoutManager = LinearLayoutManager(this)
        if(bundle?.getString("key")=="search"){
            resultados(bundle)
        }else if(bundle?.getString("key")=="category"){
            resultados(bundle)
        }
        lista_platos.adapter=ListAdapter( lista ,applicationContext)

    }
    fun resultados(bundle: Bundle){
        lista = bundle.getSerializable("resultados") as ArrayList<plato_lista>
    }

}