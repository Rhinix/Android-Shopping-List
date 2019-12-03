package be.amellaa.shoppinglist.activities.shoppingListActivity.listFragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import be.amellaa.shoppinglist.R
import be.amellaa.shoppinglist.dao.DataFetcher

class SharedFragment : ListFragment() {

    lateinit var mFloatingButton: FloatingActionButton
    var mDataFetcher = DataFetcher(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        mFloatingButton = view!!.findViewById<FloatingActionButton>(R.id.addListButton)
        mFloatingButton.hide()
        return view
    }

    override fun getList() {
        mDataFetcher.fetchSharedList()
    }


}