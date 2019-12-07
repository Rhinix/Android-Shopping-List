package be.amellaa.shoppinglist.activities.shoppingListActivity

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import be.amellaa.shoppinglist.R
import be.amellaa.shoppinglist.dao.DataFetcher
import be.amellaa.shoppinglist.dao.ICommunicateData
import be.amellaa.shoppinglist.models.ShoppingItem

class ShoppingItemActivity : Activity(),
    ICommunicateData<ArrayList<ShoppingItem>> {


    lateinit var mRecyclerView: RecyclerView
    lateinit var swipeView: SwipeRefreshLayout
    lateinit var mDataFetcher: DataFetcher
    //var arrayList_details: ArrayList<ShoppingItem> = ArrayList();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shoppingitem)
        mDataFetcher = DataFetcher(this)
        mRecyclerView = findViewById<RecyclerView>(R.id.itemRecyclerView) as RecyclerView
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        swipeView = findViewById<SwipeRefreshLayout>(R.id.itemSwipeRefresh) as SwipeRefreshLayout
        swipeView.setOnRefreshListener { getMyList() }
        //swipeView.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener(function = { getMyList() }))
        getMyList()
    }

    private fun getMyList() {
        val id = this.intent.getStringExtra("listId")
        mDataFetcher.fetchItems(id)
    }

    override fun communicateData(data: ArrayList<ShoppingItem>) {
        runOnUiThread { setShoppingList(data) }
    }

    override fun communicateCode(code: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun setShoppingList(newItemList: ArrayList<ShoppingItem>) {
        val adapterShopping: ShoppingItemAdapter =
            ShoppingItemAdapter(newItemList)
        mRecyclerView.adapter = adapterShopping
        (mRecyclerView.adapter as ShoppingItemAdapter).notifyDataSetChanged()
        stopRefreshing()
    }

    private fun stopRefreshing() {
        if (swipeView.isRefreshing) {
            swipeView.isRefreshing = false
        }
    }

}