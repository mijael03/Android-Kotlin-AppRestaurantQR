package com.example.proyectofinal

import android.content.Intent
import android.content.res.Configuration
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.widget.ToolbarWidgetWrapper
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.elementos_popular.view.*
import org.json.JSONException
import java.io.Serializable
import java.lang.Exception

class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    private var listaplatos:ArrayList<plato_lista> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navigationView:NavigationView=findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        setSupportActionBar(findViewById(R.id.app_bar_main))
        supportActionBar?.setHomeAsUpIndicator(android.R.drawable.ic_menu_preferences)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val policy=
            StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        cargarPopular()
        category_list.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL))
        category_list.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        val list_category:ArrayList<Category> = ArrayList()
        list_category.add(Category("Pizza","cat_1"))
        list_category.add(Category("Sopas","soup"))
        list_category.add(Category("Segundos","segundo"))
        list_category.add(Category("Postres","dessert"))

        val adapter=CategoryAdapter(list_category,applicationContext,listaplatos)
        category_list.adapter=adapter

        card_btn.setOnClickListener()
        {
            val intent:Intent =Intent(this,PedidoCarrito::class.java)
            startActivity(intent)
        }
        homeBtn.setOnClickListener(){
            val intent:Intent =Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        btnSearch.setOnClickListener(){
            val resulta=search()
            val llamaractividad= Intent(applicationContext, ListaPlatos::class.java)
            llamaractividad.putExtra("resultados",resulta)
            llamaractividad.putExtra("key","search")
            startActivity(llamaractividad)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal,menu)
        return true
    }




    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id:Int=item.itemId
        if (id==android.R.id.home) {
            layout_lateral.openDrawer(GravityCompat.START)
            return true
        }

        return super.onOptionsItemSelected(item)
    }



    fun search():ArrayList<plato_lista>{
        val results:ArrayList<plato_lista> = ArrayList()
        try{
            val keyword=txtsearch.text.toString().toLowerCase()
            for (plato in listaplatos){
                val coincidencia:String=plato.title.toLowerCase()

                if(coincidencia.contains(keyword)){
                    Log.e("error",coincidencia)
                    results.add(plato)
                }
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
        return results
    }
    fun cargarPopular(){
        food_list.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL))
        food_list.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        var llenarLista = ArrayList<plato_lista>()

        AsyncTask.execute {

            val queue = Volley.newRequestQueue(applicationContext)
            val url = getString(R.string.urlAPI) + "/productos"

            val stringRequest = JsonArrayRequest(url,
                { response ->

                    try {
                        for (i in 0 until response.length()) {
                            val id=
                                response.getJSONObject(i).getString("id").toInt()
                            val nombre =
                                response.getJSONObject(i).getString("name")
                            val price =
                                response.getJSONObject(i).getString("price")
                            val category=
                                response.getJSONObject(i).getJSONObject("category").getString("name")
                            val description =
                                response.getJSONObject(i).getString("description")
                            val image=
                                getString(R.string.urlStatic)+response.getJSONObject(i).getString("image")

                            llenarLista.add(plato_lista( id,nombre, image, description,category,price.toDouble()))
                        }

                        val adapter = PopularAdapter(llenarLista, applicationContext)
                        food_list.adapter = adapter
                    } catch (e: JSONException) {

                        Toast.makeText(
                            applicationContext,
                            "Error al obtener los datos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }, {

                    Toast.makeText(
                        applicationContext,
                        "Verifique que esta conectado a internet",
                        Toast.LENGTH_LONG
                    ).show()
                })
            queue.add(stringRequest)
        }
        llenarLista=listaplatos
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var intent:Intent
        fun intent(){
            intent=Intent(applicationContext,form::class.java)
            startActivity(intent)
        }
        fun intentdelivery(){
            intent=Intent(applicationContext,form_delivery::class.java)

            startActivity(intent)
        }
        when(item.itemId){
            R.id.nav_reservas-> intent()
            R.id.nav_delivery-> intentdelivery()
        }
        return true
    }

}