package be.amellaa.shoppinglist.activities.shoppingListActivity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import be.amellaa.shoppinglist.R
import be.amellaa.shoppinglist.dao.CommunicationInterface
import be.amellaa.shoppinglist.dao.ShoppingListDAO
import be.amellaa.shoppinglist.models.ShoppingList
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MyListFragment() : Fragment(), AddShoppingListDialog.DialogListener {

    lateinit var mRecyclerView: RecyclerView
    lateinit var swipeView: SwipeRefreshLayout
    lateinit var mFloatingButton : FloatingActionButton
    lateinit var mAddShoppingListDialog: AddShoppingListDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view : View = inflater.inflate(R.layout.shoppinglist_fragment, container, false)
        mAddShoppingListDialog = AddShoppingListDialog()
        mAddShoppingListDialog.setListener(this)
        mRecyclerView = view.findViewById(R.id.recyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        swipeView = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh) as SwipeRefreshLayout
        swipeView.setOnRefreshListener { getMyList() }
        mFloatingButton = view.findViewById<FloatingActionButton>(R.id.addListButton)
        mFloatingButton.setOnClickListener {
            mAddShoppingListDialog.show(fragmentManager, "AddShoppingListDialog")
        }
        getMyList()
        return view
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        if(dialog is AddShoppingListDialog){
            ShoppingListDAO.instance.createShoppingList(dialog.getEditText())
            getMyList()
        }
    }

    private fun getMyList() {
        ShoppingListDAO.instance.getMyList(object: CommunicationInterface {
            override fun communicateShoppingLists(shoppingLists: ArrayList<ShoppingList>) {
                activity?.runOnUiThread{
                    setShoppingList(shoppingLists)
                }
            }
        })
    }

    private fun setShoppingList(newShoppingList: ArrayList<ShoppingList>) {
        val adapterShopping: ShoppingListAdapter =
            ShoppingListAdapter(
                newShoppingList
            )
        mRecyclerView.adapter = adapterShopping
        (mRecyclerView.adapter as ShoppingListAdapter).notifyDataSetChanged()
        stopRefreshing()
    }

    private fun stopRefreshing() {
        if (swipeView.isRefreshing) {
            swipeView.isRefreshing = false
        }
    }

}