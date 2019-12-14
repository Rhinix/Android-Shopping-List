package be.amellaa.shoppinglist.activities.shoppingListActivity.itemFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import be.amellaa.shoppinglist.R
import be.amellaa.shoppinglist.activities.shoppingListActivity.ShoppingItemAdapter
import be.amellaa.shoppinglist.activities.shoppingListActivity.dialogfragment.DialogListener
import be.amellaa.shoppinglist.activities.shoppingListActivity.dialogfragment.WithEntryAndQuantityDialogFragment
import be.amellaa.shoppinglist.activities.shoppingListActivity.dialogfragment.WithListDialogFragment
import be.amellaa.shoppinglist.dto.DataFetcher
import be.amellaa.shoppinglist.dto.ICommunicateData
import be.amellaa.shoppinglist.models.ShoppingItem
import com.google.android.material.floatingactionbutton.FloatingActionButton

abstract class BaseItemFragment : Fragment(),
    ICommunicateData<ArrayList<ShoppingItem>>, DialogListener {

    lateinit var mRecyclerView: RecyclerView
    lateinit var swipeView: SwipeRefreshLayout
    lateinit var mDataFetcher: DataFetcher
    lateinit var mFloatingActionButton : FloatingActionButton
    lateinit var mToolbar: Toolbar
    lateinit var listId: String
    lateinit var listName: String
    var newListName: String = ""

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        if (dialog is WithEntryAndQuantityDialogFragment) {
            when(dialog.tag){
                "AddItemDialog" -> {
                    mDataFetcher.addShoppingItem(dialog.getName(), dialog.getQuantity(), listId)
                }
                "ModifyItemDialog" -> {
                    mDataFetcher.modifyShoppingItem(dialog.getName(), dialog.getQuantity(), dialog.itemId)
                }
            }
        }
    }

    override fun onDialogItemClick(text: String, itemId: String) {
        when(text){
            "Modify" -> {
                val mWithEntryQtyDialog = WithEntryAndQuantityDialogFragment(R.string.modifyShoppingItemTitle)
                mWithEntryQtyDialog.setListener(this)
                mWithEntryQtyDialog.itemId = itemId
                mWithEntryQtyDialog.show(fragmentManager, "ModifyItemDialog")
            }
            "Delete" -> {
                mDataFetcher.deleteShoppingItem(itemId)
            }
        }
    }

    override fun communicateData(data: ArrayList<ShoppingItem>) {
        activity!!.runOnUiThread() { setShoppingItems(data) }
    }

    override fun communicateCode(code: Int) {
        when(code){
            204 -> activity!!.finish()
            201 -> activity!!.runOnUiThread() { getList() }
            200 -> activity!!.runOnUiThread() {
                activity!!.intent.putExtra("listName", newListName)
                mToolbar.title = newListName
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.shoppingitem_fragment, container, false)

        mDataFetcher = DataFetcher(this)
        mRecyclerView = view.findViewById(R.id.itemRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        swipeView = view.findViewById<SwipeRefreshLayout>(R.id.itemSwipeRefresh)
        swipeView.setOnRefreshListener { getList() }
        listId = activity!!.intent.getStringExtra("listId")
        listName = activity!!.intent.getStringExtra("listName")
        mFloatingActionButton = view.findViewById<FloatingActionButton>(R.id.addItemButton)
        mFloatingActionButton.setOnClickListener {
            val mWithEntryQtyDialog =
            WithEntryAndQuantityDialogFragment(R.string.addShoppingItemTitle)
            mWithEntryQtyDialog.setListener(this)
            mWithEntryQtyDialog.show(fragmentManager, "AddItemDialog")
        }
        mToolbar = view.findViewById<Toolbar>(R.id.toolbar_shoppingitem)
        mToolbar.title = listName
        activity!!.setActionBar(mToolbar)
        setSwipeToDismiss()
        getList()
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
                val item = (viewHolder as ShoppingItemAdapter.ListRowHolder).mShoppingItem
                mDataFetcher.deleteShoppingItem(item.id)
            }
        })
        touch.attachToRecyclerView(mRecyclerView)
    }

    private fun getList() {
        mDataFetcher.fetchItems(listId)
    }

    private fun setShoppingItems(newItemList: ArrayList<ShoppingItem>) {
        val adapterShopping: ShoppingItemAdapter =
            ShoppingItemAdapter(
                newItemList
            )
        mRecyclerView.adapter = adapterShopping
        (mRecyclerView.adapter as ShoppingItemAdapter).notifyDataSetChanged()
        stopRefreshing()
    }

    private fun stopRefreshing() {
        if (swipeView.isRefreshing) {
            swipeView.isRefreshing = false
        }
    }

    fun onCheckBoxChecked(item: ShoppingItem,checked: Boolean) {
        mDataFetcher.checkItem(item.id, checked)
    }

    fun openListChoiceDialog(itemId: String): Boolean{
        var withListDialogFragment = WithListDialogFragment(R.string.dialogOptions, itemId)
        withListDialogFragment.setListener(this)

        withListDialogFragment.show(fragmentManager, "ItemOptions")
        return true;
    }

}