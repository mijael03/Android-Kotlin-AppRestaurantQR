package com.example.proyectofinal

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CategoryAdapter(val listacategorias:ArrayList<Category>,val context: Context,val listaplatos:ArrayList<plato_lista>): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val fcategorypic=itemView.findViewById<ImageView>(R.id.categoryPic)
        val fcategoryname=itemView.findViewById<TextView>(R.id.categoryName)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val v= LayoutInflater.from(parent?.context).inflate(R.layout.elementos_categoria,parent,false)
        return CategoryAdapter.ViewHolder(v)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {

        var picurl:String=""
        holder?.fcategoryname.text=listacategorias[position].name
        when(position){
            0-> picurl="cat_1"
            1-> picurl="soup"
            2-> picurl="segundo"
            3-> picurl="dessert"

        }
        val imageid= holder.itemView.context.resources.getIdentifier(picurl,"drawable",holder.itemView.context.packageName)
        Glide.with(holder.itemView.context).load(imageid).into(holder.fcategorypic)
        holder.itemView.setOnClickListener(){
            val key=listacategorias[position].name
            val lista=recorrer(key)
            val llamaractividad= Intent(context, ListaPlatos::class.java)
            llamaractividad.putExtra("resultados",lista)
            llamaractividad.putExtra("key","category")
            holder.itemView.context.startActivity(llamaractividad)
        }
    }
    fun recorrer(keyword:String):ArrayList<plato_lista>{
        val results:ArrayList<plato_lista> = ArrayList()
        for(plato in listaplatos){
            val coincidencia:String=plato.category

            if(coincidencia.contains(keyword)){
                Log.e("error",coincidencia)
                results.add(plato)
            }
        }
        return results
    }
    override fun getItemCount(): Int {
        return listacategorias.size
    }
}