package be.amellaa.shoppinglist.dao

import be.amellaa.shoppinglist.activities.shoppingListActivity.ShoppingListAdapter
import be.amellaa.shoppinglist.activities.shoppingListActivity.listFragment.ListFragment
import be.amellaa.shoppinglist.activities.shoppingListActivity.listFragment.MyListFragment
import be.amellaa.shoppinglist.models.ShoppingList
import be.amellaa.shoppinglist.models.User

class DataFetcher {

    private var communication: CommunicationInterface

    constructor(communication: CommunicationInterface) {
        this.communication = communication
    }

    fun fetchMyList() {
        ShoppingListDAO.instance.getMyList(communication)
    }

    fun fetchSharedList() {
        ShoppingListDAO.instance.getSharedList(communication)
    }

    fun fetchItems(id: String) {
        ShoppingListDAO.instance.getItemFromList(id, communication)
    }

    fun createShoppingList(name: String) {
        ShoppingListDAO.instance.createShoppingList(name, communication)
    }

    fun deleteShoppingList(id: String) {
        ShoppingListDAO.instance.deleteList(id, communication)
    }

    fun login(user: User) {
        ShoppingListDAO.instance.login(user, communication)
    }

    fun Signup(user: User) {
        ShoppingListDAO.instance.signUp(user, communication)
    }

}