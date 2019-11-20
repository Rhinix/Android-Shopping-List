package be.amellaa.shoppinglist.dao

import be.amellaa.shoppinglist.models.ShoppingItem
import be.amellaa.shoppinglist.models.ShoppingList

interface CommunicationInterface {

    fun communicateACode(code : Int) {}
    fun communicateShoppingLists(shoppingLists : ArrayList<ShoppingList>) {}
    fun communicateShoppingItems(shoppingItems : ArrayList<ShoppingItem>) {}

}