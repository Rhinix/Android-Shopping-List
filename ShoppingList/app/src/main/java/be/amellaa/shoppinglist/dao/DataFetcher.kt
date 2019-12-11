package be.amellaa.shoppinglist.dao

import be.amellaa.shoppinglist.activities.shoppingListActivity.ShoppingListAdapter
import be.amellaa.shoppinglist.activities.shoppingListActivity.listFragment.ListFragment
import be.amellaa.shoppinglist.activities.shoppingListActivity.listFragment.MyListFragment
import be.amellaa.shoppinglist.models.ShoppingItem
import be.amellaa.shoppinglist.models.ShoppingList
import be.amellaa.shoppinglist.models.User

class DataFetcher {

    private var communication: ICommunicateCode

    constructor(communication: ICommunicateCode) {
        this.communication = communication
    }

    @Suppress("UNCHECKED_CAST")
    fun fetchMyList() {
        ShoppingListDAO.instance.getMyList((communication as ICommunicateData<ArrayList<ShoppingList>>))
    }

    @Suppress("UNCHECKED_CAST")
    fun fetchSharedList() {
        ShoppingListDAO.instance.getSharedList(communication as ICommunicateData<ArrayList<ShoppingList>>)
    }

    @Suppress("UNCHECKED_CAST")
    fun fetchItems(id: String) {
        ShoppingListDAO.instance.getItemFromList(
            id,
            communication as ICommunicateData<ArrayList<ShoppingItem>>
        )
    }

    fun createShoppingList(name: String) {
        ShoppingListDAO.instance.createShoppingList(name, communication)
    }

    fun deleteShoppingList(id: String) {
        ShoppingListDAO.instance.deleteList(id, communication)
    }

    @Suppress("UNCHECKED_CAST")
    fun login(user: User) {
        ShoppingListDAO.instance.login(user, (communication as ICommunicateData<User>))
    }

    fun Signup(user: User) {
        ShoppingListDAO.instance.signUp(user, communication)
    }

}