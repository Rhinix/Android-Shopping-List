package be.amellaa.shoppinglist.activities.shoppingListActivity.listFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import be.amellaa.shoppinglist.R
import be.amellaa.shoppinglist.activities.shoppingListActivity.AddShoppingListDialog
import be.amellaa.shoppinglist.activities.shoppingListActivity.ShoppingListAdapter
import be.amellaa.shoppinglist.dao.DataFetcher
import be.amellaa.shoppinglist.dao.ShoppingListDAO
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MyListFragment : ListFragment(), AddShoppingListDialog.DialogListener {


    lateinit var mFloatingButton: FloatingActionButton
    lateinit var mAddShoppingListDialog: AddShoppingListDialog

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        if (dialog is AddShoppingListDialog) {
            mDataFetcher.createShoppingList(dialog.getEditText())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mDataFetcher = DataFetcher(this)
        val view = super.onCreateView(inflater, container, savedInstanceState)
        mFloatingButton = view!!.findViewById<FloatingActionButton>(R.id.addListButton)
        mAddShoppingListDialog = AddShoppingListDialog()
        mAddShoppingListDialog.setListener(this)
        setSwipeToDismiss()
        mFloatingButton.setOnClickListener {
            mAddShoppingListDialog.show(fragmentManager, "AddShoppingListDialog")
        }
        return view
    }

    private fun setSwipeToDismiss() {
        val touch: ItemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val list = (viewHolder as ShoppingListAdapter.ListRowHolder).mShoppingList
                mDataFetcher.deleteShoppingList(list.id)
            }
        })
        touch.attachToRecyclerView(mRecyclerView)
    }

    override fun getList() {
        mDataFetcher.fetchMyList()
    }

}