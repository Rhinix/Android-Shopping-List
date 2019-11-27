package be.amellaa.shoppinglist.activities.shoppingListActivity


import be.amellaa.shoppinglist.dao.CommunicationInterface
import be.amellaa.shoppinglist.dao.ShoppingListDAO
import be.amellaa.shoppinglist.models.ShoppingList

class SharedFragment : ListFragment() {
    override fun getList() {
        ShoppingListDAO.instance.getSharedList(object : CommunicationInterface {
            override fun communicateShoppingLists(shoppingLists: ArrayList<ShoppingList>) {
                activity?.runOnUiThread {
                    setShoppingList(shoppingLists)
                }
            }
        })
    }


}