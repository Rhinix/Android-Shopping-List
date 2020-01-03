package be.amellaa.shoppinglist.activities.shoppingListActivity.listFragment


import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import be.amellaa.shoppinglist.R
import be.amellaa.shoppinglist.activities.shoppingListActivity.dialogfragment.DialogListener
import be.amellaa.shoppinglist.activities.shoppingListActivity.dialogfragment.WithEntryDialogFragment
import be.amellaa.shoppinglist.dto.DataFetcher

/**
 *  Fragment for shared list
 */
class SharedFragment : ListFragment(), DialogListener {
    lateinit var mFloatingButton: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mDataFetcher = DataFetcher(this)
        val view = super.onCreateView(inflater, container, savedInstanceState)
        mFloatingButton = view!!.findViewById<FloatingActionButton>(R.id.addListButton)
        mFloatingButton.setOnClickListener {
            showShareListDialog()
        }
        return view
    }

    private fun showShareListDialog() {
        val mWithEntryDialog = WithEntryDialogFragment(R.string.addSharedShoppingListTitle)
        mWithEntryDialog.setListener(this)
        mWithEntryDialog.show(fragmentManager, "ShareDialog")
    }

    override fun getList() {
        mDataFetcher.fetchSharedList()
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        if (dialog is WithEntryDialogFragment) {
            when (dialog.tag) {
                "ShareDialog" -> {
                    val shareCode = dialog.getName()
                    mDataFetcher.share(shareCode)
                }
            }
        }
    }

    override fun communicateCode(code: Int) {
        when (code) {
            200 -> getList()
        }
    }
}