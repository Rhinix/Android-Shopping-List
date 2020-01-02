package be.amellaa.shoppinglist.activities.shoppingListActivity.itemFragment

import android.app.AlertDialog
import android.content.ClipData
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.fragment.app.DialogFragment
import be.amellaa.shoppinglist.R
import be.amellaa.shoppinglist.activities.loginActivity.LoginActivity
import be.amellaa.shoppinglist.activities.shoppingListActivity.dialogfragment.WithEntryDialogFragment

/**
 *  Fragment for items in a list, the user owns
 */
class MyItemsFragment : BaseItemFragment() {

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        if (dialog is WithEntryDialogFragment) {
            when (dialog.tag) {
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
                showCopyDialog()
            }
            R.id.list_delete -> {
                val mWithEntryDialog = WithEntryDialogFragment(R.string.modifyShoppingListTitle)
                mWithEntryDialog.setListener(this)
                mDataFetcher.deleteShoppingList(listId)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showCopyDialog() {
        val builder = AlertDialog.Builder(this.context)
        builder.setTitle(R.string.ShareCode)
        builder.setMessage(activity!!.intent.getStringExtra("shareCode"))
        // builder.setPositiveButton(R.string.share_code_dialog_copy_button) { _, _ -> }

        val dialog = builder.create()
        dialog.show()
    }


}