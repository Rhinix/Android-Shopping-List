package be.amellaa.shoppinglist.dao

import be.amellaa.shoppinglist.activities.shoppingListActivity.ShoppingListAdapter
import be.amellaa.shoppinglist.activities.shoppingListActivity.listFragment.ListFragment
import be.amellaa.shoppinglist.activities.shoppingListActivity.listFragment.MyListFragment
import be.amellaa.shoppinglist.models.ShoppingList

class DataFetcher : CommunicationInterface {

    private var listFragment: ListFragment

    constructor(listFragment: ListFragment) {
        this.listFragment = listFragment
    }

    fun fetchList() {
        if(listFragment is MyListFragment){
            ShoppingListDAO.instance.getMyList(this)
        }
        else {
            ShoppingListDAO.instance.getSharedList(this)
        }
    }

    fun createShoppingList(name: String){
        ShoppingListDAO.instance.createShoppingList(name, this)
    }

    fun deleteShoppingList(id: String){
        ShoppingListDAO.instance.deleteList(id, this)
    }

    override fun communicateShoppingLists(shoppingLists: ArrayList<ShoppingList>) {
        listFragment.activity!!.runOnUiThread {
            listFragment.setShoppingList(shoppingLists)
        }
    }

    override fun communicateACode(code: Int) {
        listFragment.activity!!.runOnUiThread {
            when(code){
                200 -> fetchList()
            }
        }
    }
}