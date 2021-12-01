package com.example.proyectofinal


import java.io.Serializable

class plato_lista:Serializable{
    var id:Int = 0
    var title:String
    var pic:String
    var description:String
    var category:String
    var fee:Double
    var numberInCart:Int
    get() =field
    constructor(id:Int,title:String,pic:String,description:String,category:String,fee:Double){
        this.id=id
        this.title=title
        this.pic=pic
        this.description=description
        this.category=category
        this.fee=fee
        this.numberInCart=0
    }
    constructor(title:String,pic:String,description:String,category:String,fee:Double,numberInCart:Int){
        this.title=title
        this.pic=pic
        this.description=description
        this.category=category
        this.fee=fee
        this.numberInCart=numberInCart
    }

}