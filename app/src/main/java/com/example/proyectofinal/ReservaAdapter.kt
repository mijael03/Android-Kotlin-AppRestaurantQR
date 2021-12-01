package com.example.proyectofinal

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

class ReservaAdapter(val listareservas:ArrayList<Reserva>,val context: Context): RecyclerView.Adapter<ReservaAdapter.ViewHolder>(){
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val fpic=itemView.findViewById<ImageView>(R.id.image_reserva)
        val fecha=itemView.findViewById<TextView>(R.id.date_reserva)
        val fhora=itemView.findViewById<TextView>(R.id.hour_reserva)
        val quantity=itemView.findViewById<TextView>(R.id.reservaquantity)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservaAdapter.ViewHolder {
        val v= LayoutInflater.from(parent?.context).inflate(com.example.proyectofinal.R.layout.elementos_reserva,parent,false)
        return ReservaAdapter.ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ReservaAdapter.ViewHolder, position: Int) {
        holder?.fpic?.load(R.drawable.reserva)
        holder?.fecha.text=listareservas[position].date
        holder?.fhora.text=listareservas[position].hour
        holder?.quantity.text=listareservas[position].people

        holder.itemView.setOnClickListener(){
            val intent  : Intent = Intent(context,Reserva_Detalle::class.java)
            intent.putExtra("reserva",listareservas[position])
            holder.itemView.context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return listareservas.size
    }

}