package be.amellaa.shoppinglist.activities.shoppingListActivity.listFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import be.amellaa.shoppinglist.R
import be.amellaa.shoppinglist.activities.shoppingListActivity.dialogfragment.DialogListener
import be.amellaa.shoppinglist.activities.shoppingListActivity.dialogfragment.WithEntryDialogFragment
import be.amellaa.shoppinglist.dto.DataFetcher
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MyListFragment : ListFragment(), DialogListener {


    lateinit var mFloatingButton: FloatingActionButton

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        if (dialog is WithEntryDialogFragment) {
            mDataFetcher.createShoppingList(dialog.getName())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mDataFetcher = DataFetcher(this)
        val view = super.onCreateView(inflater, container, savedInstanceState)
        mFloatingButton = view!!.findViewById(R.id.addListButton)
        //setSwipeToDismiss()
        mFloatingButton.setOnClickListener {
            val mWithEntryDialogFragment =
                WithEntryDialogFragment(R.string.addShoppingListTitle)
            mWithEntryDialogFragment.setListener(this)
            mWithEntryDialogFragment.show(fragmentManager, "AddShoppingListDialog")
        }
        return view
    }


    override fun getList() {
        mDataFetcher.fetchMyList()
    }

}