package be.amellaa.shoppinglist.activities.shoppingListActivity.dialogfragment

import android.view.View
import androidx.fragment.app.DialogFragment

interface DialogListener {
    fun onDialogPositiveClick(dialog: DialogFragment)
    fun onDialogItemClick(text: String, itemId: String){}
}