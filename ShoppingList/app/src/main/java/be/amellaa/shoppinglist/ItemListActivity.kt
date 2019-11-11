package be.amellaa.shoppinglist

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import be.amellaa.shoppinglist.DAO.ShoppingListDAO
import be.amellaa.shoppinglist.models.ShoppingItem
import be.amellaa.shoppinglist.models.ShoppingList
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class ItemListActivity : Activity()
{

    lateinit var mRecyclerView: RecyclerView
    //var arrayList_details: ArrayList<ShoppingItem> = ArrayList();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listitem)
        mRecyclerView = findViewById<RecyclerView>(R.id.itemRecyclerView) as RecyclerView
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        //swipeView = findViewById<SwipeRefreshLayout>(R.id.swipeRefresh) as SwipeRefreshLayout
        //swipeView.setOnRefreshListener { getMyList() }
        //swipeView.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener(function = { getMyList() }))
        getMyList()
    }

    private fun getMyList() {
        val itemListArray = ShoppingListDAO.instance.getItemFromList(this.intent.getStringExtra("listId"))
        setShoppingList(itemListArray)
    }

    private fun setShoppingList(newItemList: ArrayList<ShoppingItem>) {
        val adapterShopping: ItemListAdapter = ItemListAdapter(newItemList)
        mRecyclerView.adapter = adapterShopping
        (mRecyclerView.adapter as ItemListAdapter).notifyDataSetChanged()
        Log.d("OUI", (mRecyclerView.adapter as ItemListAdapter).values.count().toString())
        //stopRefreshing()
    }

    /*private fun stopRefreshing() {
        if (swipeView.isRefreshing) {
            swipeView.isRefreshing = false
        }
    }*/

}