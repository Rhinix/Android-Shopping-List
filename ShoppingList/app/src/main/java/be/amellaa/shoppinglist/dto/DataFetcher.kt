package be.amellaa.shoppinglist.dto

import be.amellaa.shoppinglist.models.ShoppingItem
import be.amellaa.shoppinglist.models.ShoppingList
import be.amellaa.shoppinglist.models.User

class DataFetcher {

    private var communication: ICommunicateCode

    constructor(communication: ICommunicateCode) {
        this.communication = communication
    }

    /*
    *
    *  LISTS
    *
    */

    @Suppress("UNCHECKED_CAST")
    fun fetchMyList() {
        ShoppingListDTO.instance.getMyList((communication as ICommunicateData<ArrayList<ShoppingList>>))
    }

    @Suppress("UNCHECKED_CAST")
    fun fetchSharedList() {
        ShoppingListDTO.instance.getSharedList(communication as ICommunicateData<ArrayList<ShoppingList>>)
    }

    fun createShoppingList(name: String) {
        ShoppingListDTO.instance.createShoppingList(name, communication)
    }

    fun renameShoppingList(name: String, listId: String){
        ShoppingListDTO.instance.patchList(name, listId, communication)
    }

    fun deleteShoppingList(listId: String) {
        ShoppingListDTO.instance.deleteList(listId, communication)
    }

    /*
    *
    *  ITEMS
    *
    */

    @Suppress("UNCHECKED_CAST")
    fun fetchItems(listId: String) {
        ShoppingListDTO.instance.getItemFromList(
            listId,
            communication as ICommunicateData<ArrayList<ShoppingItem>>
        )
    }

    fun addShoppingItem(name: String, qty: String, listId: String){
        ShoppingListDTO.instance.createItem(name, qty, listId, communication)
    }

    fun checkItem(itemId: String, checked: Boolean) {
        ShoppingListDTO.instance.patchItem(null, null, checked, itemId, communication)
    }

    fun modifyShoppingItem(name: String, qty: String, itemId: String){
        ShoppingListDTO.instance.patchItem(name, qty, false, itemId, communication)
    }

    fun deleteShoppingItem(itemId: String){
        ShoppingListDTO.instance.deleteItem(itemId, communication)
    }

    /*
    *
    *   CONNEXION
    *
     */

    fun login(user: User) {
        ShoppingListDTO.instance.login(user, communication)
    }

    fun Signup(user: User) {
        ShoppingListDTO.instance.signUp(user, communication)
    }



}