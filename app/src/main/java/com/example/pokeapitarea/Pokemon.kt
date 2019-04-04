package com.example.pokeapitarea

class Pokemon {
    var number: Int = 0
        get() {
            val urlPartes = url!!.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            return Integer.parseInt(urlPartes[urlPartes.size - 1])
        }
    val id:Int?=null
    val name:String?=null
    var experiencia:Int=0
    var altura:Int=0
    var peso:Int=0
    var url: String? = null
}
