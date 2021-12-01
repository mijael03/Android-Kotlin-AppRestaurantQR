package com.example.proyectofinal

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load

class ListAdapter(val listaplatos:ArrayList<plato_lista>,val context: Context): RecyclerView.Adapter<ListAdapter.ViewHolder>(){
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val fpic= itemView.findViewById<ImageView>(R.id.image_reserva)
        val fname=itemView.findViewById<TextView>(R.id.date_reserva)
        val fprice=itemView.findViewById<TextView>(R.id.reservaquantity)
        val fcategory=itemView.findViewById<TextView>(R.id.hour_reserva)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ViewHolder {
        val v= LayoutInflater.from(parent?.context).inflate(R.layout.elementos_plato_lista,parent,false)
        return ListAdapter.ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
        holder?.fpic?.load(listaplatos[position].pic)
        holder?.fname.text=listaplatos[position].title
        holder?.fprice.text=listaplatos[position].fee.toString()
        holder?.fcategory.text=listaplatos[position].category

        holder.itemView.setOnClickListener(){
            val intent  : Intent = Intent(context,plato_detalle::class.java)
            intent.putExtra("plato",listaplatos[position])
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listaplatos.size
    }

}