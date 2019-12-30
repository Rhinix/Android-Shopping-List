package be.amellaa.shoppinglist.activities.shoppingListActivity.listFragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import be.amellaa.shoppinglist.Authenticator.AccountAuthenticator
import be.amellaa.shoppinglist.R
import be.amellaa.shoppinglist.activities.shoppingListActivity.ShoppingListAdapter
import be.amellaa.shoppinglist.activities.shoppingListActivity.dialogfragment.WithEntryDialogFragment
import be.amellaa.shoppinglist.dto.DataFetcher
import be.amellaa.shoppinglist.dto.ICommunicateData
import be.amellaa.shoppinglist.models.ShoppingList
import kotlin.collections.ArrayList

abstract class ListFragment : Fragment(), ICommunicateData<ArrayList<ShoppingList>> {

    lateinit var mRecyclerView: RecyclerView
    lateinit var swipeView: SwipeRefreshLayout
    lateinit var mDataFetcher: DataFetcher

    override fun communicateData(data: ArrayList<ShoppingList>) {
        activity!!.runOnUiThread { setShoppingList(data) }
    }

    override fun communicateCode(code: Int) {
        when (code) {
            201 -> getList()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.shoppinglist_fragment, container, false)
        mRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        swipeView = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
        swipeView.setOnRefreshListener { getList() }
        getList()
        return view
    }

    override fun onResume() {
        super.onResume()
        getList()
    }

    abstract fun getList()

    fun setShoppingList(newShoppingList: ArrayList<ShoppingList>) {
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