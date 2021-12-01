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

class CardAdapter(
    var listaplatos:ArrayList<plato_lista>,
    var context: Context,
    var changenumberitems:ChangeNumberItemsListener
): RecyclerView.Adapter<CardAdapter.ViewHolder>(){
    var cart:ManagementCard
    init {
        cart= ManagementCard(context)
    }
    constructor(listaplatos:ArrayList<plato_lista>, context: Context, changenumberitem:ChangeNumberItemsListener, cart: ManagementCard):this(listaplatos,context,changenumberitem) {
        this.listaplatos=listaplatos
        this.context=context
        this.changenumberitems=changenumberitem
        this.cart=cart
    }
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val fpic= itemView.findViewById<ImageView>(R.id.image_reserva)
        val feeEachItem=itemView.findViewById<TextView>(R.id.feeEachItem)
        val ftitle=itemView.findViewById<TextView>(R.id.date_reserva)
        val ftotalEachItem=itemView.findViewById<TextView>(R.id.reservaquantity)
        val num=itemView.findViewById<TextView>(R.id.numberItemTxt)
        val plusitem=itemView.findViewById<ImageView>(R.id.plusCardBtn)
        val minusitem=itemView.findViewById<ImageView>(R.id.minusCardBtn)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardAdapter.ViewHolder {
        val v= LayoutInflater.from(parent?.context).inflate(R.layout.elementos_card,parent,false)
        return CardAdapter.ViewHolder(v)
    }

    override fun onBindViewHolder(holder: CardAdapter.ViewHolder, position: Int) {
        holder?.fpic?.load(listaplatos[position].pic)
        holder?.ftitle.text=listaplatos[position].title
        holder?.feeEachItem.text=listaplatos[position].fee.toString()
        holder?.ftotalEachItem.text=(Math.round((listaplatos[position].fee * listaplatos[position].numberInCart)*100.0) / 100.0).toString()
        holder?.num.text=listaplatos[position].numberInCart.toString()
        holder.itemView.setOnClickListener(){
            val intent: Intent = Intent(context,plato_detalle::class.java)
            intent.putExtra("plato",listaplatos[position])
            holder.itemView.context.startActivity(intent)
        }

        holder.plusitem.setOnClickListener(){
            cart.plusNumberFood(listaplatos,position, object : ChangeNumberItemsListener {
                override fun changed() {
                    notifyDataSetChanged()
                    changenumberitems.changed()
                }
            })
        }
        holder.minusitem.setOnClickListener(){
            cart.minusNumberFood(listaplatos,position, object : ChangeNumberItemsListener {
                override fun changed() {
                    notifyDataSetChanged()
                    changenumberitems.changed()
                }
            })
        }
    }

    override fun getItemCount(): Int {
        return listaplatos.size
    }

}