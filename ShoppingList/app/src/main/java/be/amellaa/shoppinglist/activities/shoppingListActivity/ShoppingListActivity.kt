package be.amellaa.shoppinglist.activities.shoppingListActivity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import be.amellaa.shoppinglist.dao.ShoppingListDAO
import be.amellaa.shoppinglist.R
import be.amellaa.shoppinglist.dao.CommunicationInterface
import be.amellaa.shoppinglist.models.ShoppingList

class ShoppingListActivity : Activity() {

    lateinit var mRecyclerView: RecyclerView
    lateinit var swipeView: SwipeRefreshLayout
    //var arrayList_details: ArrayList<ShoppingList> = ArrayList();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shoppinglist)
        mRecyclerView = findViewById<RecyclerView>(R.id.recyclerView) as RecyclerView
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        swipeView = findViewById<SwipeRefreshLayout>(R.id.swipeRefresh) as SwipeRefreshLayout
        swipeView.setOnRefreshListener { getMyList() }
        //swipeView.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener(function = { getMyList() }))
        getMyList()
    }

    private fun getMyList() {
        ShoppingListDAO.instance.getMyList(object: CommunicationInterface{
            override fun communicateShoppingLists(shoppingLists: ArrayList<ShoppingList>) {
                runOnUiThread{
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
