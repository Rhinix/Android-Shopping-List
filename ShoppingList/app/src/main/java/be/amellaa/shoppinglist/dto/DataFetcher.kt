package be.amellaa.shoppinglist.dto

import be.amellaa.shoppinglist.models.ShoppingItem
import be.amellaa.shoppinglist.models.ShoppingList
import be.amellaa.shoppinglist.models.User

/**
 *  Class to talk between Activities and ShoppingListDTO
 */
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

    /**
     *  Fetch non-shared List
     */
    @Suppress("UNCHECKED_CAST")
    fun fetchMyList() {
        ShoppingListDTO.instance.getMyList((communication as ICommunicateData<ArrayList<ShoppingList>>))
    }

    /**
     *  Fetch shared List
     */
    @Suppress("UNCHECKED_CAST")
    fun fetchSharedList() {
        ShoppingListDTO.instance.getSharedList(communication as ICommunicateData<ArrayList<ShoppingList>>)
    }

    /**
     *  Create a list
     *  @param name Name of the list
     */
    fun createShoppingList(name: String) {
        ShoppingListDTO.instance.createShoppingList(name, communication)
    }

    /**
     *  Rename a list, identified by the id
     *  @param name Name of the list
     *  @param listId Id of the list
     */
    fun renameShoppingList(name: String, listId: String) {
        ShoppingListDTO.instance.patchList(name, listId, communication)
    }

    /**
     *  Add a share list, identified by his code
     *  @param shareCode Code of the list, to share
     */

    fun share(shareCode: String) {
        ShoppingListDTO.instance.share(shareCode, communication)
    }

    /**
     *  Delete a list, identified by the id
     *  @param listId Id of the list
     */
    fun deleteShoppingList(listId: String) {
        ShoppingListDTO.instance.deleteList(listId, communication)
    }

    /*
    *
    *  ITEMS
    *
    */

    /**
     *  Fetch items from a list, identified by the id
     *  @param listId Id of the list
     */
    @Suppress("UNCHECKED_CAST")
    fun fetchItems(listId: String) {
        ShoppingListDTO.instance.getItemFromList(
            listId,
            communication as ICommunicateData<ArrayList<ShoppingItem>>
        )
    }

    /**
     *  Create an item in a list, identified by the id
     *  @param name Name of the item
     *  @param qty Quantity of the item
     *  @param listId Id of the list
     */
    fun addShoppingItem(name: String, qty: String, listId: String) {
        ShoppingListDTO.instance.createItem(name, qty, listId, communication)
    }

    /**
     *  Check an item
     *  @param itemId Id of the item
     *  @param checked Boolean
     */
    fun checkItem(itemId: String, checked: Boolean) {
        ShoppingListDTO.instance.patchItem(null, null, checked, itemId, communication)
    }

    /**
     *  Update an item, identified by the id
     *  @param name Name of the item
     *  @param qty Quantity of the item
     *  @param itemId Id of the item
     */
    fun modifyShoppingItem(name: String, qty: String, itemId: String) {
        ShoppingListDTO.instance.patchItem(name, qty, false, itemId, communication)
    }

    /**
     *  Delete an item, identified by the id
     *  @param itemId Id of the item
     */
    fun deleteShoppingItem(itemId: String) {
        ShoppingListDTO.instance.deleteItem(itemId, communication)
    }

    /*
    *
    *   CONNEXION
    *
     */

    /**
     *  Log in an user
     *  @param user User to log in
     */
    @Suppress("UNCHECKED_CAST")
    fun login(user: User) {
        ShoppingListDTO.instance.login(user, communication as ICommunicateData<User>)
    }

    /**
     *  Sign up an user
     *  @param user User to sign up
     */
    fun signup(user: User) {
        ShoppingListDTO.instance.signUp(user, communication)
    }


}