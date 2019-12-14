package be.amellaa.shoppinglist.activities.shoppingListActivity.itemFragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import be.amellaa.shoppinglist.R
import be.amellaa.shoppinglist.activities.shoppingListActivity.dialogfragment.WithEntryDialogFragment


class MyItemsFragment : BaseItemFragment(){

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        if (dialog is WithEntryDialogFragment) {
            when(dialog.tag){
                "ModifyListDialog" -> {
                    newListName = dialog.getName()
                    mDataFetcher.renameShoppingList(newListName, listId)
                }
            }
        }
        super.onDialogPositiveClick(dialog)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.shopping_item_toolbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.list_modify -> {
                val mWithEntryDialog = WithEntryDialogFragment(R.string.modifyShoppingListTitle)
                mWithEntryDialog.setListener(this)
                mWithEntryDialog.show(fragmentManager, "ModifyListDialog")
            }
            R.id.list_share -> {
                Toast.makeText(activity, "Feature not yet implemented", Toast.LENGTH_SHORT).show()
            }
            R.id.list_delete -> {
                val mWithEntryDialog = WithEntryDialogFragment(R.string.modifyShoppingListTitle)
                mWithEntryDialog.setListener(this)
                mDataFetcher.deleteShoppingList(listId)
            }
        }
        return super.onOptionsItemSelected(item)
    }



}