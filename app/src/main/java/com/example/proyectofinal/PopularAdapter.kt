package com.example.proyectofinal

import android.R
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import androidx.recyclerview.widget.RecyclerView
import coil.load



class PopularAdapter(val listaplatos:ArrayList<plato_lista>,val context: Context): RecyclerView.Adapter<PopularAdapter.ViewHolder>(){
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val fpic= itemView.findViewById<ImageView>(com.example.proyectofinal.R.id.pic)
        val ftitle=itemView.findViewById<TextView>(com.example.proyectofinal.R.id.title)
        val fprice=itemView.findViewById<TextView>(com.example.proyectofinal.R.id.price)
        val addbtn=itemView.findViewById<TextView>(com.example.proyectofinal.R.id.addBtn)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularAdapter.ViewHolder {
        val v= LayoutInflater.from(parent?.context).inflate(com.example.proyectofinal.R.layout.elementos_popular,parent,false)
        return PopularAdapter.ViewHolder(v)
    }

    override fun onBindViewHolder(holder: PopularAdapter.ViewHolder, position: Int) {
        holder?.fpic?.load(listaplatos[position].pic)
        holder?.ftitle.text=listaplatos[position].title
        holder?.fprice.text=listaplatos[position].fee.toString()

        holder.itemView.setOnClickListener(){
            val intent  :Intent =Intent(context,plato_detalle::class.java)
            intent.putExtra("plato",listaplatos[position])
            holder.itemView.context.startActivity(intent)
        }

        holder.addbtn.setOnClickListener(){
            Toast.makeText(context,"Añadido",Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return listaplatos.size
    }

}