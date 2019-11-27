package be.amellaa.shoppinglist.activities.shoppingListActivity.listFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import be.amellaa.shoppinglist.R
import be.amellaa.shoppinglist.activities.shoppingListActivity.ShoppingListAdapter
import be.amellaa.shoppinglist.models.ShoppingList

abstract class ListFragment() : Fragment() {

    lateinit var mRecyclerView: RecyclerView
    lateinit var swipeView: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.shoppinglist_fragment, container, false)
        mRecyclerView = view.findViewById(R.id.recyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        swipeView = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh) as SwipeRefreshLayout
        swipeView.setOnRefreshListener { getList() }
        getList()
        return view
    }

    abstract fun getList()

    protected fun setShoppingList(newShoppingList: ArrayList<ShoppingList>) {
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