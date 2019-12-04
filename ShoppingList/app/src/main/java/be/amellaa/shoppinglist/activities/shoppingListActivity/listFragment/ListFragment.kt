package be.amellaa.shoppinglist.activities.shoppingListActivity.listFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import be.amellaa.shoppinglist.R
import be.amellaa.shoppinglist.activities.ProcessResponseCode
import be.amellaa.shoppinglist.activities.shoppingListActivity.AddShoppingListDialog
import be.amellaa.shoppinglist.activities.shoppingListActivity.ShoppingListAdapter
import be.amellaa.shoppinglist.dao.CommunicationInterface
import be.amellaa.shoppinglist.dao.DataFetcher
import be.amellaa.shoppinglist.dao.ShoppingListDAO
import be.amellaa.shoppinglist.models.ShoppingList
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.collections.ArrayList

abstract class ListFragment() : Fragment(), CommunicationInterface, ProcessResponseCode {

    lateinit var mRecyclerView: RecyclerView
    lateinit var swipeView: SwipeRefreshLayout
    lateinit var mDataFetcher: DataFetcher


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

    override fun <T> communicateData(data: T) {

        activity!!.runOnUiThread {
            if (data is ArrayList<*>) {
                setShoppingList(data as ArrayList<ShoppingList>)
            } else {
                processCode(data as Int)
            }

        }
    }

    fun setShoppingList(newShoppingList: ArrayList<ShoppingList>) {
        val adapterShopping: ShoppingListAdapter =
            ShoppingListAdapter(
                newShoppingList
            )
        mRecyclerView.adapter = adapterShopping
        (mRecyclerView.adapter as ShoppingListAdapter).notifyDataSetChanged()
        stopRefreshing()
    }

    override fun processCode(code: Int) {
        when (code) {
            200 -> getList()
        }
    }

    private fun stopRefreshing() {
        if (swipeView.isRefreshing) {
            swipeView.isRefreshing = false
        }
    }

}