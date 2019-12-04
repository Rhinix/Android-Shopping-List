package be.amellaa.shoppinglist.dao

import java.util.*

interface CommunicationInterface {

    //fun communicateACode(code: Int) {}
    //fun communicateShoppingLists(shoppingLists: ArrayList<ShoppingList>) {}
    //fun communicateShoppingItems(shoppingItems: ArrayList<ShoppingItem>) {}

    fun <T> communicateData(data: T)

}