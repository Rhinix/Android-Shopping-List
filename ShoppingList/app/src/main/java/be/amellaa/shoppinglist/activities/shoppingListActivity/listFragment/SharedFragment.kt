package be.amellaa.shoppinglist.activities.shoppingListActivity.listFragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.amellaa.shoppinglist.activities.shoppingListActivity.AddShoppingListDialog
import be.amellaa.shoppinglist.dao.CommunicationInterface
import be.amellaa.shoppinglist.dao.ShoppingListDAO
import be.amellaa.shoppinglist.models.ShoppingList
import com.google.android.material.floatingactionbutton.FloatingActionButton

import be.amellaa.shoppinglist.R

class SharedFragment : ListFragment() {

    lateinit var mFloatingButton : FloatingActionButton
    lateinit var mAddShoppingListDialog: AddShoppingListDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        mFloatingButton = view!!.findViewById<FloatingActionButton>(R.id.addListButton)
        mFloatingButton.hide()
        return view
    }

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