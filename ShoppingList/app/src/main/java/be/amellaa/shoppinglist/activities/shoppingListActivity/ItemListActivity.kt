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
import be.amellaa.shoppinglist.models.ShoppingItem

class ItemListActivity : Activity()
{

    lateinit var mRecyclerView: RecyclerView
    lateinit var swipeView: SwipeRefreshLayout
    //var arrayList_details: ArrayList<ShoppingItem> = ArrayList();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listitem)
        mRecyclerView = findViewById<RecyclerView>(R.id.itemRecyclerView) as RecyclerView
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        swipeView = findViewById<SwipeRefreshLayout>(R.id.itemSwipeRefresh) as SwipeRefreshLayout
        swipeView.setOnRefreshListener { getMyList() }
        //swipeView.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener(function = { getMyList() }))
        getMyList()
    }

    private fun getMyList() {
        ShoppingListDAO.instance.getItemFromList(this.intent.getStringExtra("listId"), object : CommunicationInterface{
            override fun communicateShoppingItems(shoppingItems: ArrayList<ShoppingItem>) {
                runOnUiThread {
                    setShoppingList(shoppingItems)
                }
            }
        })
    }

    private fun setShoppingList(newItemList: ArrayList<ShoppingItem>) {
        val adapterShopping: ItemListAdapter =
            ItemListAdapter(newItemList)
        mRecyclerView.adapter = adapterShopping
        (mRecyclerView.adapter as ItemListAdapter).notifyDataSetChanged()
        stopRefreshing()
    }

    private fun stopRefreshing() {
        if (swipeView.isRefreshing) {
            swipeView.isRefreshing = false
        }
    }

}