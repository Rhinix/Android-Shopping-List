package be.amellaa.shoppinglist.dao

import be.amellaa.shoppinglist.activities.shoppingListActivity.listFragment.ListFragment
import be.amellaa.shoppinglist.models.ShoppingList

class DataFetcher : CommunicationInterface {

    private var listFragment: ListFragment

    constructor(listFragment: ListFragment) {
        this.listFragment = listFragment
    }

    fun fetchMyList() {
        ShoppingListDAO.instance.getMyList(this)
    }

    fun fetchSharedList() {
        ShoppingListDAO.instance.getSharedList(this)
    }

    override fun communicateShoppingLists(shoppingLists: ArrayList<ShoppingList>) {
        listFragment.activity!!.runOnUiThread {
            listFragment.setShoppingList(shoppingLists)
        }
    }
}