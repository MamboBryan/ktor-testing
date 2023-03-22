package com.justmambo.bamboo

import kotlinx.coroutines.runBlocking

interface Composable {
     fun getUi()
}

data class Passenger(val name: String) : Composable {
    override fun getUi() {

    }
}
data class Glass(val quantity: Int) : Composable {
    override fun getUi() {
        TODO("Not yet implemented")
    }
}

data class Cement(val quantity: Int) : Composable{
    override fun getUi() {

    }

}

fun <T: Composable> Render(item: T){
    item.getUi()
}

fun main() = runBlocking {
    val list = mutableListOf<Composable>()
    val cList = mutableListOf<Cement>()
    list.add(Passenger("name"))
    list.add(Glass(2))
    list.add(Cement(2))
    println("FINISHED TRIP")
}
