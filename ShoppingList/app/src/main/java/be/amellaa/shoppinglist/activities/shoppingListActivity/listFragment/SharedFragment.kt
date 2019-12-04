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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mDataFetcher = DataFetcher(this)
        val view = super.onCreateView(inflater, container, savedInstanceState)
        mFloatingButton = view!!.findViewById<FloatingActionButton>(R.id.addListButton)
        mFloatingButton.hide()
        return view
    }

}