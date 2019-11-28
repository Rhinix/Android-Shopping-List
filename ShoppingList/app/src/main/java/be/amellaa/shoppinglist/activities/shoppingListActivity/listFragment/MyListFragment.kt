package be.amellaa.shoppinglist.activities.shoppingListActivity.listFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import be.amellaa.shoppinglist.R
import be.amellaa.shoppinglist.activities.shoppingListActivity.AddShoppingListDialog
import be.amellaa.shoppinglist.dao.DataFetcher
import be.amellaa.shoppinglist.dao.ShoppingListDAO
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MyListFragment : ListFragment(), AddShoppingListDialog.DialogListener {

    lateinit var mFloatingButton: FloatingActionButton
    lateinit var mAddShoppingListDialog: AddShoppingListDialog
    var mDataFetcher = DataFetcher(this)

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        if (dialog is AddShoppingListDialog) {
            ShoppingListDAO.instance.createShoppingList(dialog.getEditText())
            getList()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        mFloatingButton = view!!.findViewById<FloatingActionButton>(R.id.addListButton)
        mAddShoppingListDialog = AddShoppingListDialog()
        mAddShoppingListDialog.setListener(this)
        mFloatingButton.setOnClickListener {
            mAddShoppingListDialog.show(fragmentManager, "AddShoppingListDialog")
        }
        return view;
    }

    override fun getList() {
        mDataFetcher.fetchMyList()
    }
}